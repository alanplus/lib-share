package com.lib.share;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.lib.basex.activity.LActivity;
import com.lib.basex.activity.LViewModel;
import com.lib.basex.utils.LToastManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public abstract class ShareActivity<T extends LViewModel, D extends ViewDataBinding> extends LActivity<T, D> {


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new QQShareHelper(this).onActivityResult(requestCode, resultCode, data);
    }


}
