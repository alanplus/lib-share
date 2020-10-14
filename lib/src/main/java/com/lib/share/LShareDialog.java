package com.lib.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.lib.basex.dialog.bottom.BottomSheet;
import com.lib.basex.utils.LBitmapUtils;
import com.lib.share.data.BitmapShareData;
import com.lib.share.data.ShareData;
import com.lib.share.databinding.LShareDialogBinding;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public class LShareDialog extends BottomSheet<LShareDialogBinding> {

    private Activity activity;

    private ShareData shareData;

    private boolean isWXChat = true;
    private boolean isWxTimeLine = true;
    private boolean isQQ = true;
    private boolean isQQZone = true;

    public LShareDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d.qqLayout.setVisibility(isQQ ? View.VISIBLE : View.GONE);
        d.qqZoneLayout.setVisibility(isQQZone ? View.VISIBLE : View.GONE);
        d.wxLayout.setVisibility(isWXChat ? View.VISIBLE : View.GONE);
        d.timeLayout.setVisibility(isWxTimeLine ? View.VISIBLE : View.GONE);

        d.qqTv.setOnClickListener(shareData.onClickListener == null ? (View.OnClickListener) view -> {
            ShareFactory.getQQShare().share(activity, shareData, true);
            dismiss();
        } : shareData.onClickListener);
        d.qqZoneTv.setOnClickListener(shareData.onClickListener == null ? (View.OnClickListener) view -> {
            ShareFactory.getQQShare().share(activity, shareData, false);
            dismiss();
        } : shareData.onClickListener);
        d.wxTv.setOnClickListener(shareData.onClickListener == null ? (View.OnClickListener) view -> {
            ShareFactory.getWeiXinShare().share(activity, shareData, true);
            dismiss();
        } : shareData.onClickListener);
        d.timeTv.setOnClickListener(shareData.onClickListener == null ? (View.OnClickListener) view -> {
            ShareFactory.getWeiXinShare().share(activity, shareData, false);
            dismiss();
        } : shareData.onClickListener);

        d.btnCancel.setOnClickListener(view -> dismiss());

    }

    public void setShareData(@NonNull ShareData shareData) {
        this.shareData = shareData;
    }

    public void setViewCacheShareData(@NonNull View view) {
        Bitmap bitmap = LBitmapUtils.getViewScreenBitmap(view);
        BitmapShareData bitmapShareData = new BitmapShareData(bitmap);
        setShareData(bitmapShareData);
    }

    @Override
    public void show() {
        super.show();
    }

    public void show(boolean isWXChat, boolean isWxTimeLine, boolean isQQ, boolean isQQZone) {
        this.isQQ = isQQ;
        this.isQQZone = isQQZone;
        this.isWXChat = isWXChat;
        this.isWxTimeLine = isWxTimeLine;
        show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        destroy();
    }

    @Override
    public int getContentRes() {
        return R.layout.l_share_dialog;
    }

    public void destroy() {
        if (null != activity) {
            activity = null;
        }
        if (shareData != null) {
            shareData.recycle();
        }
    }
}
