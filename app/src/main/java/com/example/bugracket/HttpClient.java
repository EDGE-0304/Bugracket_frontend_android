package com.example.bugracket;

import okhttp3.OkHttpClient;

public class HttpClient {
    private static OkHttpClient singletonClient;

    private HttpClient() {}

    //ChatGPT usage: Yes
    public static synchronized OkHttpClient getInstance() {

        if (singletonClient == null) singletonClient = new OkHttpClient();

        return singletonClient;

    }

}