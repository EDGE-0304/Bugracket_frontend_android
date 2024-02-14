package com.example.bugracket;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static User instance = null;
    private static final String TAG = "User";
    private static final ProfileManager profileManager = new ProfileManager();
    private String name;
    private String email;
    private ImageData avatar;
    private String token;
    private ArrayList<String> deviceList;

    public static synchronized User getInstance() {

        if (instance == null) {

            instance = new User();

        }

        return instance;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ImageData getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageData avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<String> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<String> deviceList) {
        this.deviceList.clear();
        this.deviceList.addAll(deviceList);
    }

}

class ImageData {
    private String contentType;
    private String image;// This holds the Base64 encoded image data
    public String getImage()
    {
        return image;
    }
    public String getContentType() {
        return contentType;
    }

}

class userLogInData {
    private String name;
    private String password;

    public userLogInData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

class userSignInData {
    private String name;
    private String password;
    private String email;

    public userSignInData(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

class ApiResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}

class FriendRequestData {
    private String requester;
    private String receiver;

    public FriendRequestData(String requesterName, String receiverName) {
        this.requester = requester;
        this.receiver = receiver;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


}