package com.example.bugracket.device;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bugracket.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceManager {
    private static final String TAG = "DeviceManager";
    private static final Gson gson = new Gson();
    private static final Type stringArrayListType = new TypeToken<ArrayList<String>>(){}.getType();
    private static final Type deviceType = new TypeToken<Device>(){}.getType();

    public interface deviceListCallBack {
        void onSuccess(List<String> deviceList);
        void onFailure(Exception e);
    }

    public interface deviceInfoCallBack {
        void onSuccess(Device deviceInfo);
        void onFailure(Exception e);
    }

    public void getDeviceList(String name, final Activity activity, final deviceListCallBack callback) {

        String url = "" + name;
        OkHttpClient httpClient = HttpClient.getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.e(TAG, "FAILURE GET BUG RECORD: " + e);

                activity.runOnUiThread(() -> callback.onFailure(e));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (!response.isSuccessful()) {

                    Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                    activity.runOnUiThread(() -> callback.onFailure(new IOException("Unexpected response " + response)));

                } else {

                    Log.d(TAG, "GET BUG RECORD SUCCEED");

                    assert response.body() != null;
                    final String responseData = response.body().string();

                    List<String> deviceList = gson.fromJson(responseData, stringArrayListType);

                    activity.runOnUiThread(() -> callback.onSuccess(deviceList));

                }
            }
        });
    }

    public void getDeviceInfo(String deviceNumber, final Activity activity, final deviceInfoCallBack callback) {

        String url = "" + deviceNumber;
        OkHttpClient httpClient = HttpClient.getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.e(TAG, "FAILURE GET BUG RECORD: " + e);

                activity.runOnUiThread(() -> callback.onFailure(e));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (!response.isSuccessful()) {

                    Log.d(TAG, "Unexpected server response, the code is: " + response.code());

                    activity.runOnUiThread(() -> callback.onFailure(new IOException("Unexpected response " + response)));

                } else {

                    Log.d(TAG, "GET BUG RECORD SUCCEED");

                    assert response.body() != null;
                    final String responseData = response.body().string();

                    Device device = gson.fromJson(responseData, deviceType);

                    activity.runOnUiThread(() -> callback.onSuccess(device));

                }
            }
        });
    }

}
