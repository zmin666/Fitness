package com.example.fitness;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.DialogCompat;

/**
 * @author: ZhangMin
 * @date: 2020/7/4 10:55
 * @version: 1.0
 * @desc:
 */
public class ProjectDialog extends Dialog {
    public ProjectDialog(@NonNull Context context) {
        super(context);
    }

    public ProjectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProjectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
