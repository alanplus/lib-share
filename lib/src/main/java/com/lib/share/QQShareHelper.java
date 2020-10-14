package com.lib.share;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.lib.basex.utils.LToastManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * @author Alan
 * 时 间：2020/10/14
 * 简 述：<功能简述>
 */
public class QQShareHelper implements IUiListener {

    private Context context;

    public QQShareHelper(Context context) {
        this.context = context;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, this);
    }

    @Override
    public void onComplete(Object o) {
        LToastManager.getInstance().showToast(context, "分享成功");
    }

    @Override
    public void onError(UiError uiError) {
        LToastManager.getInstance().showToast(context, "分享失败:" + uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        LToastManager.getInstance().showToast(context, "分享取消");
    }
}
