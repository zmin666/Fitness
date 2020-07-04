package com.example.fitness.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fitness.R;
import com.example.fitness.SportBean;

import java.util.regex.Pattern;

/**
 * @author: ZhangMin
 * @date: 2020/7/4 16:17
 * @version: 1.0
 * @desc:
 */
public class AddDialog extends BaseDialog {
    private EditText editText;
    private EditText editText2;
    private RadioGroup RadioGroup1;
    private RadioButton rb1;
    private RadioButton rb2;
    private TextView tvSure;
    private TextView tvCancle;

    public interface OnListener {
        void click(SportBean sportBean);
    }

    OnListener onListener;

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public AddDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_add_project;
    }

    @Override
    protected void initView() {
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        RadioGroup1 = (RadioGroup) findViewById(R.id.RadioGroup1);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        tvSure = (TextView) findViewById(R.id.tv_sure);
        tvCancle = (TextView) findViewById(R.id.tv_cancle);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = editText2.getText().toString().trim();
                String s2 = editText.getText().toString().trim();
                if (TextUtils.isEmpty(s2)) {
                    Toast.makeText(context, "请输入运动名称", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(context, "请输入时间", Toast.LENGTH_SHORT).show();
                } else if (!isInteger(s1)) {
                    Toast.makeText(context, "时间请输入整数", Toast.LENGTH_SHORT).show();
                } else {
                    String str = RadioGroup1.getCheckedRadioButtonId() == R.id.rb1 ? "秒" : "个";
                    SportBean s = new SportBean(Integer.parseInt(s1), s2, str);
                    if (onListener != null) {
                        onListener.click(s);
                    }
                    dismiss();
                }
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

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
