package com.example.fitness.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.example.fitness.R;

/**
 * @author: ZhangMin
 * @date: 2017/7/26 11:06
 * @desc: 编写布局的时候注意最外层的layout_width layout_height失效
 * 控制布局的大小可以通过xml第二层 layout_width layout_height 来设置
 */
public abstract class BaseDialog extends Dialog {

    /**
     * 中间显示的dialog
     */
    public static final int GRAVITY_CENTER = 100;
    /**
     * 从底部弹出的dialog
     */
    public static final int GRAVITY_BOTTOM = 101;
    /**
     * 全屏
     */
    public static final int GRAVITY_FULL = 102;


    public Context context;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.public_msgdialog);
    }


    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        //style一定要指定. 没有标题和透明. 否则dialog会被压缩
        super(context, themeResId);
        this.context = context;
        View contentView = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        setContentView(contentView);
    }

    /**
     * 设置布局
     */
    protected void initView() {

    }

    /**
     * @return dialog的布局
     */
    protected abstract int getLayoutId();


    /**
     * @return GRAVITY_CENTER 中间弹出  GRAVITY_BOTTOM 底部弹出
     */
    protected abstract int getGravity();

    /**
     * 是否可以点击外部消失
     * @return true点击外部弹窗可以消失  false 点击外部弹窗不能消失
     */
    protected abstract boolean isCanceledOnTouchOutside();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得dialog的window窗口
        Window window = getWindow();
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        switch (getGravity()) {
            case GRAVITY_CENTER:
                window.setGravity(Gravity.CENTER);
                window.getDecorView().setPadding(0, 0, 0, 0);
                //设置窗口宽度为充满全屏
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                //设置窗口高度为包裹内容
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                //将设置好的属性set回去
                window.setAttributes(lp);
                //设置dialog弹出时的动画效果，
                window.setWindowAnimations(R.style.public_pop_add_ainm);
                break;
            case GRAVITY_BOTTOM:
                //设置dialog在屏幕底部
                window.setGravity(Gravity.BOTTOM);
                window.getDecorView().setPadding(0, 0, 0, 0);
                //设置窗口宽度为充满全屏
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //设置窗口高度为包裹内容
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                //将设置好的属性set回去
                window.setAttributes(lp);
                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
                window.setWindowAnimations(R.style.public_dialog_add_ainm);
                break;
            case GRAVITY_FULL:
                window.getDecorView().setPadding(0, 0, 0, 0);
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(lp);
                window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
                window.setWindowAnimations(R.style.public_pop_add_ainm);
                break;
            default:
                break;
        }
        setWindowForAnimation(window);
        setCanceledOnTouchOutside(isCanceledOnTouchOutside());

        initView();
    }

    /**
     * 可以在这里配置 dialog 弹出消失动画
     * @param window
     */
    public void setWindowForAnimation(Window window) {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.context = null;
    }
}

