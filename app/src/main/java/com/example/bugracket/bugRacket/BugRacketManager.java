package com.example.bugracket.bugRacket;

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

public class BugRacketManager {
    private static final String TAG = "BugRacketManager";
    private static final Gson gson = new Gson();

    public interface bugRecordCallBack {
        void onSuccess(List<String> bugRecord);
        void onFailure(Exception e);
    }

    public void getBugRecord(String name, final Activity activity, final bugRecordCallBack callback) {

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

                    Type bugRecordType = new TypeToken<ArrayList<String>>(){}.getType();
                    List<String> bugRecord = gson.fromJson(responseData, bugRecordType);

                    activity.runOnUiThread(() -> callback.onSuccess(bugRecord));

                }
            }
        });
    }

}
