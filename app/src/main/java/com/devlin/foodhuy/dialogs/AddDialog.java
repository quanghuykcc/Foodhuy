package com.devlin.foodhuy.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.devlin.foodhuy.R;

/**
 * Created by Administrator on 7/29/2016.
 */
public class AddDialog extends Dialog {
    public AddDialog(Context context) {
        super(context);
        initialize();
    }

    public AddDialog(Context context, int themeResId) {
        super(context, themeResId);
        initialize();
    }

    private void initialize() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add);

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        getWindow().getAttributes().windowAnimations = R.style.DialogMoveUp;
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        setCancelable(true);
    }
}
