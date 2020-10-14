package com.lib.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;

import androidx.annotation.NonNull;

import com.lib.basex.utils.LToastManager;
import com.lib.share.data.BitmapShareData;
import com.lib.share.data.ShareData;
import com.lib.share.data.TextShareData;
import com.lib.share.data.WebShareData;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public class LWeiXinShare implements IShare {


    @Override
    public void share(Activity context, ShareData shareData, boolean isChat) {
        String id = shareData.getId(context, false);
        final IWXAPI api = WXAPIFactory.createWXAPI(context, shareData.getId(context, false), true);
        if (!check(context, api, shareData)) {
            return;
        }
//        api.registerApp(id);
        if (shareData instanceof TextShareData) {
            shareText(context, api, (TextShareData) shareData, isChat);
        } else if (shareData instanceof WebShareData) {
            shareWebPage(context, api, (WebShareData) shareData, isChat);
        } else if (shareData instanceof BitmapShareData) {
            shareBitmap(context, api, (BitmapShareData) shareData, isChat);
        }
    }


    /**
     * 文本分享
     *
     * @param context
     * @param shareData
     * @param isChat
     */
    private void shareText(Activity context, IWXAPI api, TextShareData shareData, boolean isChat) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = shareData.text;
        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = shareData.text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    /**
     * 网页分享
     *
     * @param context
     * @param shareData
     * @param isChat
     */
    private void shareWebPage(Activity context, IWXAPI api, WebShareData shareData, boolean isChat) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = shareData.url;

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webObject);
        msg.title = shareData.title;
        msg.description = shareData.description;
        msg.thumbData = shareData.getBitmapData(context);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("web");
        req.message = msg;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    /**
     * 网页分享
     *
     * @param context
     * @param shareData
     * @param isChat
     */
    private void shareBitmap(Activity context, IWXAPI api, BitmapShareData shareData, boolean isChat) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(shareData.getShareBitmap());
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        msg.thumbData = shareData.getBitmapData();

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }


    private boolean check(Context context, IWXAPI api, ShareData shareData) {
        if (shareData == null || !shareData.isAvailable(false)) {
            LToastManager.getInstance().showToast(context, "无效的数据，分享失败");
            return false;
        }

        if (!api.isWXAppInstalled()) {
            LToastManager.getInstance().showToast(context, "还没有安装微信，请先安装微信客户端");
            return false;
        }

        if (api.getWXAppSupportAPI() < Build.TIMELINE_SUPPORTED_SDK_INT) {
            LToastManager.getInstance().showToast(context, "微信版本过低，请先升级微信客户端");
            return false;
        }

        return true;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }




}
