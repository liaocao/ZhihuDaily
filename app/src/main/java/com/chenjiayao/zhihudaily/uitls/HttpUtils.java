package com.chenjiayao.zhihudaily.uitls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.chenjiayao.zhihudaily.constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by chen on 2015/11/15.
 */
public class HttpUtils {

    public static AsyncHttpClient client = new AsyncHttpClient();

    //去看看知乎的APi就知道了.
    public static void get(String url, ResponseHandlerInterface responseHandler) {
        client.get(constant.START_URL + url, responseHandler);

    }


    public static void getImage(String url, ResponseHandlerInterface responseHander) {
        client.get(url, responseHander);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager systemService = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = systemService.getActiveNetworkInfo();
        if(null != info){
            return info.isAvailable();
        }
        return false;
    }
}
