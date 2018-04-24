package exampleoncreatingfixedfragment.example.com.androidproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 450 G1 on 09/05/2017.
 */

public class SingleTon {

    private static SingleTon mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private SingleTon(Context context) {
        mContext = context;
        getRequestQueue(context);
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }
    // one object
    public static synchronized SingleTon getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingleTon(context);
        }
        return mInstance;
    }

    public <T> void addRequest(Request<T> request) {
        mRequestQueue.add(request);
    }
}
