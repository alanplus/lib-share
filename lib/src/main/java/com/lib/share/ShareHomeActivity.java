package com.lib.share;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.lib.basex.activity.LHomeActivity;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public abstract class ShareHomeActivity extends LHomeActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new QQShareHelper(this).onActivityResult(requestCode, resultCode, data);
    }

}
