package test.hotline.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tanmay on 22/07/17.
 */

public class Utilities {
    public static void toast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
