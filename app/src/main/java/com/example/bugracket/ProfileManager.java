package com.example.bugracket;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileManager {
    private static final String TAG = "ProfileManager";
    private static final Gson gson = new Gson();

    public interface verificationCallback {
        void onSuccess(boolean pass);
        void onFailure(Exception e);
    }

    public interface emptyCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public void verifyPassword(String name, String password, final Activity activity, final verificationCallback callback){

        String url = "https://bugracket.nn.r.appspot.com/users/login";
        OkHttpClient httpClient = HttpClient.getInstance();

        userLogInData userLogInData = new userLogInData(name, password);
        String jsonUserLogInData = gson.toJson(userLogInData);

        Log.d(TAG, "LOG IN: " + jsonUserLogInData);

        RequestBody body = RequestBody.create(jsonUserLogInData, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.e(TAG, "FAILURE VERIFY USER: " + e);

                activity.runOnUiThread(() -> callback.onFailure(e));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (!response.isSuccessful()) {

                    Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                    activity.runOnUiThread(() -> callback.onFailure(new IOException("Unexpected response " + response)));

                } else {

                    Log.d(TAG, "VERIFY USER SUCCEED");

                    try {

                        assert response.body() != null;

                        ApiResponse apiResponse = gson.fromJson(response.body().charStream(), ApiResponse.class);
                        boolean pass = apiResponse.isSuccess();

                        activity.runOnUiThread(() -> callback.onSuccess(pass));

                    } catch (Exception e) {

                        Log.e(TAG, "Error parsing response: " + e);

                        activity.runOnUiThread(() -> callback.onFailure(e));

                    }
                }
            }
        });
    }

    public void createNewUser(String name, String password, String email, final Activity activity, final emptyCallback callback){

        String url = "https://bugracket.nn.r.appspot.com/users/sign-up";
        OkHttpClient httpClient = HttpClient.getInstance();

        userSignInData userSignInData = new userSignInData(name, password, email);
        String jsonUserSignInData = gson.toJson(userSignInData);

        Log.d(TAG, "SIGN IN: " + jsonUserSignInData);

        RequestBody body = RequestBody.create(jsonUserSignInData, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.e(TAG, "FAILURE CREATE USER: " + e);

                activity.runOnUiThread(() -> callback.onFailure(e));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (!response.isSuccessful()) {

                    Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                    activity.runOnUiThread(() -> callback.onFailure(new IOException("Unexpected response " + response)));

                } else {

                    Log.d(TAG, "CREATE USER SUCCEED");

                    activity.runOnUiThread(callback::onSuccess);

                }
            }
        });
    }

    public void operateFriend(boolean add, String name, final Activity activity, final emptyCallback callback) {

        String url;
        if (add) url = "";
        else     url = "";
        OkHttpClient httpClient = HttpClient.getInstance();

        FriendRequestData friendRequestData = new FriendRequestData(User.getInstance().getName(), name);

        String jsonFriendRequestData = gson.toJson(friendRequestData);

        if (add) Log.d(TAG, "ADD FRIEND: " + jsonFriendRequestData);
        else     Log.d(TAG, "DEL FRIEND: " + jsonFriendRequestData);

        RequestBody body = RequestBody.create(jsonFriendRequestData, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                if (add) Log.e(TAG, "FAILURE ADD FRIEND: " + e);
                else     Log.e(TAG, "FAILURE DEL FRIEND: " + e);

                activity.runOnUiThread(() -> callback.onFailure(e));

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (!response.isSuccessful()) {

                    Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                    activity.runOnUiThread(() -> callback.onFailure(new IOException("Unexpected response " + response)));

                } else {

                    if (add) Log.d(TAG, "ADD FRIEND SUCCEED");
                    else     Log.d(TAG, "DEL FRIEND SUCCEED");

                    activity.runOnUiThread(callback::onSuccess);

                }
            }
        });
    }

    public void uploadUserAvatar(File avatarFile, final Activity activity, final emptyCallback callback) {

        String url = "";
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Increase connect timeout
                .writeTimeout(10, TimeUnit.SECONDS) // Increase write timeout
                .readTimeout(10, TimeUnit.SECONDS) // Increase read timeout
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        builder.addFormDataPart("image", avatarFile.getName(), fileBody);
        builder.addFormDataPart("name", User.getInstance().getName());

        RequestBody requestBody = builder.build();

        Log.d(TAG, requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> {

                    Log.e(TAG, "FAILURE UPLOAD AVATAR: " + e);

                    activity.runOnUiThread(() -> callback.onFailure(e));

                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                activity.runOnUiThread(() -> {

                    if (!response.isSuccessful()) {

                        Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                        callback.onFailure(new IOException("Unexpected response " + response));

                    } else {

                        Log.d(TAG, "UPLOAD AVATAR SUCCEED");

                        activity.runOnUiThread(callback::onSuccess);

                    }
                });
            }
        });
    }



}
