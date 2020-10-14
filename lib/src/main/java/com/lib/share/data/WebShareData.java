package com.lib.share.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.lib.basex.utils.Logger;

import java.io.ByteArrayOutputStream;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class WebShareData extends ShareData {

    public String title;
    public String description;
    public String url;
    public int bitmapRes;
    public Bitmap bitmap;

    //QQ 使用
    public String imgUrl;

    public byte[] getBitmapData(@NonNull Context context) {
        if (bitmapRes == 0 && bitmap == null) {
            bitmap = getBitmap(context);
        }
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapRes);
        }
        bitmap = imageZoom(bitmap, 30);
        return bmpToByteArray(bitmap, false);
    }

    @Override
    public boolean isAvailable(boolean isQQ) {
        return !TextUtils.isEmpty(url);
    }

    @Override
    public void recycle() {
        super.recycle();
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * @param maxSize //图片允许最大空间   单位：KB
     */
    public static Bitmap imageZoom(Bitmap bitMap, double maxSize) {
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
    }



    public static synchronized Bitmap getBitmap(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
            Bitmap APKicon;
            if (d instanceof BitmapDrawable) {
                APKicon = ((BitmapDrawable) d).getBitmap();
            } else {
                Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                d.draw(canvas);
                APKicon = bitmap;
            }
            applicationInfo = null;
            return APKicon;
        } catch (Exception e) {
            Logger.error(e);
        }

        return null;
    }
}
