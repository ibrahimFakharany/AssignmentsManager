package exampleoncreatingfixedfragment.example.com.androidproject;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 450 G1 on 09/05/2017.
 */

public class CustomToast {
    String message;
    Context mContext;
    int du = 5000 ;
    public CustomToast(Context context) {
        mContext = context;
    }
    public void raiseToast(String message) {
        android.widget.Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
    }

}
