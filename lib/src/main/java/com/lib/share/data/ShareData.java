package com.lib.share.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

import java.io.ByteArrayOutputStream;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public abstract class ShareData {

    protected String id;
    public View.OnClickListener onClickListener;

    public abstract boolean isAvailable(boolean isQQ);

    public byte[] bmpToByteArray(Bitmap bitmap, final boolean needRecycle) {

        Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        bitmap = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            Logger.error(e);
        }
        return result;
    }

    public void recycle() {

    }

    public String getId(Context context, boolean isQQ) {
        if (isQQ) {
            int qqId = LUtils.getMetaDataInt(context, "l_qq_id");
            return String.valueOf(qqId);
        } else {
            return LUtils.getMetaData(context, "l_wx_id");
        }

    }

    public void setId(String id) {
        this.id = id;
    }

}
