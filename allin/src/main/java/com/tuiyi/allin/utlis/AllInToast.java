package com.tuiyi.allin.utlis;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 吐司
 *
 * @author liuhuijie
 * @date 12/21/20
 */
public class AllInToast {
    private static Toast sToast;

    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String msg, int duration) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setDuration(duration);
            toast.setText(String.valueOf(msg));
            toast.show();
        } else {
            Log.i("TToast", "toast msg: " + String.valueOf(msg));
        }
    }

    private static Toast getToast(Context context) {
        if (context == null) {
            return sToast;
        }
        sToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        return sToast;
    }

    public static void reset() {
        sToast = null;
    }
}
