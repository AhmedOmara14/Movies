package com.illa.task.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class HelperClass {
    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
