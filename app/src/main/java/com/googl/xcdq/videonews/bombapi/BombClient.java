package com.googl.xcdq.videonews.bombapi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xcdq on 2017/3/16.
 */

public class BombClient {
    private OkHttpClient okHttpClient;
    private static BombClient bombClient;

    private BombClient() {
        okHttpClient = new OkHttpClient();
    }

    public static synchronized BombClient getInstance() {
        if (bombClient == null) {
            bombClient = new BombClient();
        }
        return bombClient;
    }

    //注册请求
    public Call register(String username, String password) {
        //构建一个请求的请求体（根据服务器要求）
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        RequestBody requestBody = RequestBody.create(null, json);

        Request request = new Request.Builder()
                .url("https://api.bmob.cn/1/users")
                //用于让bomb服务器，区分是哪一个应用
                .addHeader("X-Bmob-Application-Id", "623aaef127882aed89b9faa348451da3")
                //用于授权
                .addHeader("X-Bmob-REST-API-Key", "c00104962a9b67916e8cbcb9157255de")
                //请求和响应统一使用json格式
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }

    //登入请求
    public Call singin(String username, String password) {
        Request request = new Request.Builder()
                .get()
                .url("https://api.bmob.cn/1/login" + "?"
                        + "username=" + username + "&"
                        + "password=" + password)
                //用于让bomb服务器，区分是哪一个应用
                .addHeader("X-Bmob-Application-Id", "623aaef127882aed89b9faa348451da3")
                //用于授权
                .addHeader("X-Bmob-REST-API-Key", "c00104962a9b67916e8cbcb9157255de")
                //请求和响应统一使用json格式
                .addHeader("Content-Type", "application/json")
                .build();
        return okHttpClient.newCall(request);
    }
}
