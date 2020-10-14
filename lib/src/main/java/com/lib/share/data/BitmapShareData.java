package com.lib.share.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lib.basex.utils.LBitmapUtils;
import com.lib.basex.utils.LPathUtils;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class BitmapShareData extends ShareData {

    /**
     * 微信 分享使用
     */
    private Bitmap bitmap;

    /**
     * QQ 分享使用
     */
    public String bitmapPath;

    public BitmapShareData(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapPath = LPathUtils.getExternalCache() + "/share.png";
        LBitmapUtils.save(bitmap, this.bitmapPath);
    }

    public BitmapShareData(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }

    public Bitmap getShareBitmap() {
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeFile(bitmapPath);
        }
        return bitmap;
    }


    @Override
    public boolean isAvailable(boolean isQQ) {
        return isQQ ? !TextUtils.isEmpty(bitmapPath) : getShareBitmap() != null;
    }

    public byte[] getBitmapData() {
        return bmpToByteArray(getShareBitmap(), false);
    }

    @Override
    public void recycle() {
        super.recycle();
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
