package com.chenjiayao.zhihudaily.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chenjiayao.zhihudaily.Animate;
import com.chenjiayao.zhihudaily.R;
import com.chenjiayao.zhihudaily.constant;
import com.chenjiayao.zhihudaily.uitls.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;

/**
 * Created by chen on 2015/11/15.
 * <p/>
 * 根据url去获取json数组,
 * 解析json数组,获取图片的url地址
 * 下载图片,保存图片,完毕跳转MainActivity
 */
public class SplashActivity extends BaseActivity {


    @Bind(R.id.start_image)
    ImageView startImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initImage();
    }


    private void initImage() {

        File dir = getFilesDir();
        final File imageFile = new File(dir, "start.jpg");
        if (imageFile.exists()) {
            startImage.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        } else {
            startImage.setImageResource(R.mipmap.ic_launcher);
        }

        startImage.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setListener(new Animate() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (HttpUtils.isNetworkConnected(SplashActivity.this)) {

                            AsyncHttpClient client = new AsyncHttpClient();
                            //创建异步请求
                            client.get(constant.START_URL + constant.IMAGE, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                    String responseUrl = new String(responseBody);
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseUrl);
                                        String imageUrl = jsonObject.getString("img");
                                        saveImage(imageFile, imageUrl);
                                        startActivity();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        startActivity();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    startActivity();
                                }
                            });
                        } else {
                            startActivity();
                        }
                    }
                })
                .setDuration(3000)
                .start();
    }


    /**
     * 进入主界面
     */
    private void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 根据url去下载图片并且保存,
     *
     * @param imageUrl 图片地址
     */
    private void saveImage(final File file, String imageUrl) {
        //android-async-http中下载二进制/图片数据使用BinaryHttpResponseHandler
        HttpUtils.getImage(imageUrl, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                if (file.exists()) {
                    file.delete();
                }

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(binaryData);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {

            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }
}
