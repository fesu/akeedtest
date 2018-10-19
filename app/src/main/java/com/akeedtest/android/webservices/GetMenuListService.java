package com.akeedtest.android.webservices;

import android.content.Context;
import android.util.Log;

import com.akeedtest.android.TheApplication;
import com.akeedtest.android.models.MenuListResponseModel;
import com.akeedtest.android.utils.WSUrls;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetMenuListService {

    private final Context context;

    RequestQueue mRequestQueue;

    private static String TAG = "GetMenuListService";

    String responseStatus = "";

    public GetMenuListService(Context context) {
        this.context = context;

        TAG = this.context.getClass().getSimpleName();
    }

    public void makeRequest() {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        String url = WSUrls.KEY_WS_GET_MENU_LIST;

        final Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        /*try {
            jsonObject = new JSONObject();
            jsonObject.put("userId", UserSettings.getUserIdFromPref(context));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.d(TAG, obj.toString());
                MenuListResponseModel menuListResponseModel = gson.fromJson(obj.toString(), MenuListResponseModel.class);

                doResponseActions(menuListResponseModel);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                error.printStackTrace();
                /*Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                /*headers.put("userId", String.valueOf(CommonUtilities.getUserIDFromProfilePref(context)));
                headers.put("accept", KeyValueStore.KEY_APP_VERSION_HEADER);*/

                //headers.put("Accept", "application/json;charset=UTF-8");
                //headers.put("Content-Type","application/json;charset=UTF-8");

                return headers;
            }

        };

        // Add the request to the RequestQueue.
        //mRequestQueue.add(jsonObjReq);
        TheApplication.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public interface OnResponseListener{
        void onResponse(MenuListResponseModel menuListResponseModel);
    }

    OnResponseListener mOnResponseListener;
    public void setOnResponseListener(OnResponseListener onResponseListener){
        mOnResponseListener = onResponseListener;
    }


    private void doResponseActions(MenuListResponseModel menuListResponseModel) {
        if(mOnResponseListener != null){
            mOnResponseListener.onResponse(menuListResponseModel);
        }
    }

}
