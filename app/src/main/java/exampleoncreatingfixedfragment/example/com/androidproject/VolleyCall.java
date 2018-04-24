package exampleoncreatingfixedfragment.example.com.androidproject;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 450 G1 on 09/05/2017.
 */

public class VolleyCall {
    private String mUrl;
    private Context mContext;
    String ip;
    private ArrayList<Params> mList;
    String respons;
    String baseUrl;

    public VolleyCall(Context context, String url, ArrayList<Params> list) {
        mUrl = url;
        mContext = context;
        ip = context.getString(R.string.ip);
        mList = new ArrayList<>();
        mList = list;
        baseUrl =  ip;
    }

    public String getVolleyCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl + mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respons = response;
                new CustomToast(mContext).raiseToast(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                for (int i = 0; i < mList.size(); i++) {
                    map.put(mList.get(i).getName(), mList.get(i).getValue());
                }
                return map;
            }
        };
        SingleTon.getInstance(mContext).addRequest(stringRequest);
        return respons;
    }

    public String getRespons() {
        return respons;
    }

    public ArrayList cutResponse(String response, Object object) {
        try {
            JSONObject root = new JSONObject(response);
            int state = root.getInt("success");
            if (state == 1) {
                new CustomToast(mContext).raiseToast("success = 1");
                JSONArray array = root.getJSONArray("message");

                JSONObject jsonObjectI = null;
                for (int i = 0; i < array.length(); i++) {
                    jsonObjectI = array.getJSONObject(i);

                }
            } else {
                System.out.println("success = zero");
            }
        } catch (JSONException ex) {
        }
        return null;
    }
}
