package com.example.bugracket.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bugracket.bugRacket.BugRacketActivity;
import com.example.bugracket.R;

import java.util.ArrayList;
import java.util.Random;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private static final DeviceManager deviceManager = new DeviceManager();
    private static final String TAG = "DeviceAdapter";
    private final ArrayList<String> deviceList;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceName;
        public ImageView deviceImage;
        public LinearLayout deviceLayout;

        public ViewHolder(View view) {
            super(view);

            deviceName = view.findViewById(R.id.device_name);
            deviceImage = view.findViewById(R.id.device_image);
            deviceLayout = view.findViewById(R.id.device_layout);

        }
    }

    public DeviceAdapter(ArrayList<String> deviceList, Context context) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);
        return new DeviceAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        deviceManager.getDeviceInfo(deviceList.get(position), new Activity(), new DeviceManager.deviceInfoCallBack() {
//            @Override
//            public void onSuccess(Device deviceInfo) {
//
//                holder.deviceName.setText(deviceInfo.getDeviceName());
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//                Log.e(TAG, e.toString());
//
//            }
//        });


        Random random = new Random();
        holder.deviceName.setText(deviceList.get(position));

        setDeviceImage(deviceList.get(position), holder);

        holder.deviceLayout.setOnClickListener(view -> setDeviceOnClicker(deviceList.get(position)));

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    private void setDeviceImage(String deviceNumber, @NonNull ViewHolder holder) {

        holder.deviceImage.setImageDrawable(null);

        if (deviceNumber.startsWith("BR")) {

            holder.deviceImage.setImageResource(R.drawable.sample_bug_racket);

        } else {

            holder.deviceImage.setImageResource(R.drawable.sample_device);

        }

        holder.deviceImage.invalidate();

    }

    private void setDeviceOnClicker(String deviceNumber) {

        Intent intent = null;

        if (deviceNumber.startsWith("BR")) {

            intent = new Intent(context, BugRacketActivity.class);

        } else {



        }

        if (intent == null) return;

        context.startActivity(intent);

    }

}
