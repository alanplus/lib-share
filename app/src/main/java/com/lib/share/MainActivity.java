package com.lib.share;

import android.view.View;

import com.lib.basex.activity.LActivity;
import com.lib.basex.activity.LViewModel;
import com.lib.share.data.TextShareData;
import com.lib.share.databinding.ActivityMainBinding;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class MainActivity extends LActivity<LViewModel, ActivityMainBinding> {
    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        d.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LShareDialog shareDialog = new LShareDialog(getActivity());
                TextShareData textShareData = new TextShareData();
                textShareData.text = "abc";
                shareDialog.setShareData(textShareData);
                shareDialog.show();
            }
        });
    }
}
