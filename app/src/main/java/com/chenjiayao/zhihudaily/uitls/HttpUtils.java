package com.chenjiayao.zhihudaily.uitls;

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
        Log.i("TAG", constant.START_URL + url);
        client.get(constant.START_URL + url, responseHandler);

    }


    public static void getImage(String url, ResponseHandlerInterface responseHander) {
        client.get(url, responseHander);
    }
}
