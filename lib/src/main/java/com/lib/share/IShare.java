package com.lib.share;

import android.app.Activity;

import com.lib.share.data.ShareData;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public interface IShare {

    void share(Activity activity, ShareData shareData, boolean isChat);

}
