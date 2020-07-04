package com.example.fitness.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitness.R;
import com.example.fitness.SportBean;

/**
 * @author: ZhangMin
 * @date: 2020/7/4 16:17
 * @version: 1.0
 * @desc:
 */
public class TipsDialog extends BaseDialog {

    public TipsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_tips;
    }


    @Override
    protected void initView() {
        TextView  tvSure = (TextView) findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected int getGravity() {
        return BaseDialog.GRAVITY_CENTER;
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return true;
    }
}
