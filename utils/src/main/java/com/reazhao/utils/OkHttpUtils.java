package com.reazhao.utils;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <pre>
 * @author ReaZhao
 * @date 2017/3/6
 * @E-mail 377742053qq.com
 * @desc 网络请求
 * </pre>
 */
public class OkHttpUtils {
    private static OkHttpClient client = new OkHttpClient();


    /**
     * get同步请求
     *
     * @param url
     */
    public static String doGet(String url) {
        String result = null;
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        try {
            result = call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get 异步请求
     *
     * @param url
     * @param handler
     */
    public static void doGetAsyn(final String url, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.obj = doGet(url);
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * post同步请求
     *
     * @param url
     * @param params
     */
    public static String doPost(String url, final Map<String, String> params) {
        String result = null;
        FormBody.Builder data = new FormBody.Builder();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                data.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(data.build())
                .build();
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json; charset=utf-8"), param);
//        Request request = new Request.Builder().url(url).post(body).build();
        try {
            result = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post同步请求
     *
     * @param url
     * @param param
     */
    public static String doPost(String url, String param) {
        String result = null;
        FormBody.Builder params = new FormBody.Builder();
        if (!StringUtils.isEmpty(param)) {
            params.add("data", param);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json; charset=utf-8"), param);
//        Request request = new Request.Builder().url(url).post(body).build();
        try {
            result = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post异步请求
     *
     * @param url
     * @param param
     * @param handler
     */
    public static void doPostAsyn(final String url, final String param,
                                  final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.obj = doPost(url, param);
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * post异步请求
     *
     * @param url
     * @param params
     * @param handler
     */
    public static void doPostAsyn(final String url, final Map<String, String> params,
                                  final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.obj = doPost(url, params);
                handler.sendMessage(msg);
            }
        }.start();
    }

    public static void uploadImg(final String url, final String data, final List<File> files, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                upload(url, data, files, handler);
            }
        }.start();
    }

    private static void upload(String url, String data, List<File> files, Handler handler) {
        String result = "FAIL";
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File f : files) {
            if (f != null) {
                builder.addFormDataPart("img", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
            }
        }

        //添加其它信息
//        builder.addFormDataPart("time",takePicTime);
//        builder.addFormDataPart("mapX", SharedInfoUtils.getLongitude());
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());

        builder.addFormDataPart("data", data);
        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();
        final Message msg = handler.obtainMessage();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                msg.obj = "FAIL";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                msg.obj = response.body().string();
//                System.out.println("上传照片成功：response = " + response.body().string());
            }

        });
        handler.sendMessage(msg);
    }
}
