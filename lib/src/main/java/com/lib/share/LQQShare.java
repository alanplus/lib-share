package com.lib.share;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.lib.basex.utils.LToastManager;
import com.lib.basex.utils.Logger;
import com.lib.share.data.BitmapShareData;
import com.lib.share.data.ShareData;
import com.lib.share.data.TextShareData;
import com.lib.share.data.WebShareData;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public class LQQShare implements IShare {

    @Override
    public void share(Activity context, ShareData shareData, boolean isChat) {
        Tencent tencent = Tencent.createInstance(shareData.getId(context, true), context.getApplicationContext());
        if (!isQQClientAvailable(context, tencent, shareData, isChat)) {
            return;
        }

        if (shareData instanceof TextShareData) {
            shareText(context, (TextShareData) shareData, tencent, isChat);
        } else if (shareData instanceof WebShareData) {
            shareWebPage(context, (WebShareData) shareData, tencent, isChat);
        } else if (shareData instanceof BitmapShareData) {
            shareBitmap(context, (BitmapShareData) shareData, tencent, isChat);
        }
    }

    private void shareBitmap(Activity context, BitmapShareData shareData, Tencent tencent, boolean isChat) {

        final Bundle bundle = new Bundle();
        if (isChat) {
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareData.bitmapPath);
            bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "APP name");
            bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
            shareTo(context, tencent, bundle, true);
        } else {
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
            ArrayList<String> imglist = new ArrayList<String>();
            imglist.add(shareData.bitmapPath);
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imglist);
            tencent.publishToQzone(context, bundle, null);
        }
    }

    private void shareWebPage(Activity context, WebShareData shareData, Tencent tencent, boolean isChat) {
        final Bundle bundle = new Bundle();
        ArrayList<String> imgList = new ArrayList<>();
        String imgUrl = TextUtils.isEmpty(shareData.imgUrl) ? "http://source.weicistudy.com/image/gaozhong/icon/weici_student.png" : shareData.imgUrl;
        imgList.add(imgUrl);//图片地址);
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgList);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareData.title);// 必填
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareData.description);// 选填
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareData.url);// 必填

        if (isChat) {
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        } else {
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        }

        shareTo(context, tencent, bundle, isChat);
    }

    private void shareText(Activity context, TextShareData shareData, Tencent tencent, boolean isChat) {
        final Bundle bundle = new Bundle();
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareData.text);// 必填
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "");// 选填
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "");// 必填
        shareTo(context, tencent, bundle, isChat);
    }

    public void shareTo(Activity context, Tencent tencent, Bundle bundle, boolean isChat) {
        IUiListener iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LToastManager.getInstance().showToast(context, "分享成功");
            }

            @Override
            public void onError(UiError uiError) {
                Logger.i("分享失败：" + uiError.errorMessage);
                LToastManager.getInstance().showToast(context, "分享失败");
            }

            @Override
            public void onCancel() {
                LToastManager.getInstance().showToast(context, "分享取消");
            }
        };

        if (isChat) {
            tencent.shareToQQ(context, bundle, iUiListener);
        } else {
            tencent.shareToQzone(context, bundle, iUiListener);
        }
    }


    private boolean isQQClientAvailable(Context context, Tencent tencent, ShareData shareData, boolean isChat) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> infoList = packageManager.getInstalledPackages(0);
        boolean isAvailable = false;
        for (int i = 0; i < infoList.size(); i++) {
            String pn = infoList.get(i).packageName;
            if (pn.equals("com.tencent.mobileqq") || pn.equals("com.tencent.minihd.qq")) {
                isAvailable = true;
                break;
            }
        }
        if (null == tencent) {
            LToastManager.getInstance().showToast(context, "无效的Id");
            return false;
        }

        if (!isAvailable) {
            LToastManager.getInstance().showToast(context, "还没有安装QQ，请先安装QQ客户端");
            return false;
        }

        if (null == shareData || !shareData.isAvailable(true)) {
            LToastManager.getInstance().showToast(context, "无效的数据，分享失败");
            return false;
        }
        return true;
    }

}
