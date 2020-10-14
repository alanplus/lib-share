package com.lib.share.data;

import android.text.TextUtils;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class TextShareData extends ShareData {

    public String text;

    @Override
    public boolean isAvailable(boolean isQQ) {
        return !TextUtils.isEmpty(text);
    }
}
