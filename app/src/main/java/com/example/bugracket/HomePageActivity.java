package com.example.bugracket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bugracket.device.DeviceAdapter;
import com.example.bugracket.device.DeviceManager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePageActivity extends AppCompatActivity {
    private static final ProfileManager profileManager = new ProfileManager();
    private static final DeviceManager deviceManager = new DeviceManager();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
    private static final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final String TAG = "HomePageActivity";
    private static final int PERMISSION_CODE_BLUETOOTH_CONNECT = 1;
    private static final int PERMISSION_CODE_BACKGROUND_LOCATION = 2;
    private static final int PERMISSION_CODE_FINE_LOCATION = 3;
    private static final int REQUEST_ENABLE_BT = 4;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> getImage;
    private RecyclerView homePageRecyclerView;
    private DrawerLayout drawerLayout;
    private TextView welcomeName;
    private TextView testInfo;
    private TextView logOut;
    private ImageView avatar;
    private ImageView addDeviceButton;
    private static final ArrayList<String> devices = new ArrayList<>();
    private final DeviceAdapter deviceAdapter = new DeviceAdapter(devices, this);
    private BroadcastReceiver receiver;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializeUI();

        checkAndRequestPermissions();

        mockDeviceData();

        setOnClickers();

    }

    private void initializeUI() {

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);

        User.getInstance().setName(sharedPreferences.getString("name", "-"));

        avatar = findViewById(R.id.home_page_avatar);
        welcomeName = findViewById(R.id.home_page_user_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        addDeviceButton = findViewById(R.id.home_page_add_button_image);

        homePageRecyclerView = findViewById(R.id.home_page_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        homePageRecyclerView.setLayoutManager(gridLayoutManager);

        logOut = findViewById(R.id.drawer_log_out_text);

        testInfo = findViewById(R.id.home_page_test_info);

    }

    @SuppressLint("SetTextI18n")
    private void fetchUserData() {

        welcomeName.setText("Welcome, " + User.getInstance().getName() + "!");

        deviceManager.getDeviceList(User.getInstance().getName(), this, new DeviceManager.deviceListCallBack() {
            @Override
            public void onSuccess(List<String> deviceList) {

                devices.clear();
                devices.addAll(deviceList);

                homePageRecyclerView.setAdapter(deviceAdapter);

            }

            @Override
            public void onFailure(Exception e) {

                Log.e(TAG, e.toString());

            }
        });

    }

    private void setOnClickers() {

        avatar.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        addDeviceButton.setOnClickListener(view -> {

            Intent intent = new Intent(this, DeviceAddingAvtivity.class);
            startActivity(intent);

        });

        logOut.setOnClickListener(view -> {

            SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });

    }

    @SuppressLint("SetTextI18n")
    private void mockDeviceData() {

        welcomeName.setText("Welcome, " + User.getInstance().getName() + "!");

        devices.clear();
        devices.add("BR001");
        devices.add("BR002");

        homePageRecyclerView.setAdapter(deviceAdapter);

    }

    private void setBluetooth() {

        if (bluetoothAdapter == null) {

            Log.e(TAG, "This device does not support bluetooth");

        } else {

            if (!bluetoothAdapter.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {

                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                }

            } else {

                updatePairedDevices();

            }

        }


    }


    private void updatePairedDevices() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) return;
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {

            StringBuilder pd = new StringBuilder();

            for (BluetoothDevice device : pairedDevices) {

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                pd.append(deviceName).append("\n").append(deviceHardwareAddress).append("\n");

            }

            testInfo.setText(pd);

        }
    }

    private void checkAndRequestPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.e(TAG, "ACCESS_FINE_LOCATION not allowed");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_FINE_LOCATION);

//        }
//        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            Log.e(TAG, "ACCESS_BACKGROUND_LOCATION not allowed");
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_CODE_BACKGROUND_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            Log.e(TAG, "BLUETOOTH_CONNECT not allowed");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_CODE_BLUETOOTH_CONNECT);

        } else {

            setBluetooth();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE_FINE_LOCATION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_CODE_BLUETOOTH_CONNECT);

                }

            }

//        }
//        else if (requestCode == PERMISSION_CODE_BACKGROUND_LOCATION) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_CODE_BLUETOOTH_CONNECT);
//
//                }
//
//            }

        } else if (requestCode == PERMISSION_CODE_BLUETOOTH_CONNECT) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                setBluetooth();

            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {

            if (resultCode == RESULT_OK) {

                // Bluetooth has been enabled, proceed with device discovery
                updatePairedDevices();

            } else {

                // User did not enable Bluetooth or an error occurred
                Log.e(TAG, "Bluetooth not enabled");

            }
        }
    }





}