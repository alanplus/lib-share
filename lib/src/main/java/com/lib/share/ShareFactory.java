package com.lib.share;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public class ShareFactory {

    public static IShare getQQShare() {
        return new LQQShare();
    }

    public static IShare getWeiXinShare() {
        return new LWeiXinShare();
    }
}
