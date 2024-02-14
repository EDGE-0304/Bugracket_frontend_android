package com.example.bugracket.bugRacket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.bugracket.R;
import com.example.bugracket.device.Device;
import com.example.bugracket.device.DeviceAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BugRacketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_racket);
    }

//    private void fetchUserData() {
//
//        profileManager.getBugRecord(User.getInstance().getName(), this, new ProfileManager.bugRecordCallBack() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onSuccess(List<String> bugRecord) {
//
//                LocalDateTime now = LocalDateTime.now();
//                LocalDateTime time;
//
//                int countToday = 0;
//                int countThisWeek = 0;
//                int countThisMonth = 0;
//                int countThisYear = 0;
//
//                int currentDayOfMonth = now.getDayOfMonth();
//                int currentWeek = now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
//                int currentMonth = now.getMonthValue();
//                int currentYear = now.getYear();
//
//                for (String date : bugRecord) {
//
//                    time = LocalDateTime.parse(date, formatter);
//
//                    if (time.getYear() == currentYear && time.getMonthValue() == currentMonth && time.getDayOfMonth() == currentDayOfMonth) {
//                        countToday++;
//                    }
//
//                    if (time.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) == currentWeek && time.getYear() == currentYear) {
//                        countThisWeek++;
//                    }
//
//                    if (time.getMonthValue() == currentMonth && time.getYear() == currentYear) {
//                        countThisMonth++;
//                    }
//
//                    if (time.getYear() == currentYear) {
//                        countThisYear++;
//                    }
//                }
//
//                welcomeName.setText("Welcome, " + User.getInstance().getName() + "!");
//                bugsKilledToday.setText("You killed " + countToday + " bugs today!");
//
////                BUG_RECORD_DEVICES.clear();
////                BUG_RECORD_DEVICES.add(new Device("This week", String.valueOf(countThisWeek), countThisWeek));
////                BUG_RECORD_DEVICES.add(new Device("This month", String.valueOf(countThisMonth), countThisMonth));
////                BUG_RECORD_DEVICES.add(new Device("This year", String.valueOf(countThisYear), countThisYear));
////                BUG_RECORD_DEVICES.add(new Device("All time", String.valueOf(bugRecord.size()), bugRecord.size()));
//
//                DeviceAdapter deviceAdapter = new DeviceAdapter(BUG_RECORD_DEVICES);
//                homePageRecyclerView.setAdapter(deviceAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//                Log.d(TAG, String.valueOf(e));
//
//            }
//        });
//    }

    private void mockBugData(List<String> bugRecord) {

        bugRecord.add("2024-07-06-09:54:44");
        bugRecord.add("2023-04-30-18:39:44");
        bugRecord.add("2023-03-26-03:29:44");
        bugRecord.add("2023-01-20-07:17:44");
        bugRecord.add("2023-12-11-23:11:44");
        bugRecord.add("2023-08-01-14:24:44");
        bugRecord.add("2023-12-02-15:33:44");
        bugRecord.add("2023-04-29-07:17:44");
        bugRecord.add("2023-09-21-09:51:44");
        bugRecord.add("2023-05-25-18:58:44");
        bugRecord.add("2023-09-26-11:27:44");
        bugRecord.add("2023-06-25-06:50:44");

    }

//    @SuppressLint("SetTextI18n")
//    private void mockFetchUserData() {
//
//        List<String> bugRecord = new ArrayList<>();
//        mockBugData(bugRecord);
//
//        initializeUI();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime time;
//
//        int countToday = 0;
//        int countThisWeek = 0;
//        int countThisMonth = 0;
//        int countThisYear = 0;
//
//        int currentDayOfMonth = now.getDayOfMonth();
//        int currentWeek = now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
//        int currentMonth = now.getMonthValue();
//        int currentYear = now.getYear();
//
//        for (String date : bugRecord) {
//
//            time = LocalDateTime.parse(date, formatter);
//
//            if (time.getYear() == currentYear && time.getMonthValue() == currentMonth && time.getDayOfMonth() == currentDayOfMonth) {
//                countToday++;
//            }
//
//            if (time.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) == currentWeek && time.getYear() == currentYear) {
//                countThisWeek++;
//            }
//
//            if (time.getMonthValue() == currentMonth && time.getYear() == currentYear) {
//                countThisMonth++;
//            }
//
//            if (time.getYear() == currentYear) {
//                countThisYear++;
//            }
//
//        }
//
//        welcomeName.setText("Welcome, " + User.getInstance().getName() + "!");
//        bugsKilledToday.setText("You killed " + countToday + " bugs today!");
//
//        BUG_RECORD_DEVICES.clear();
//        BUG_RECORD_DEVICES.add(new Device("This week", String.valueOf(countThisWeek), countThisWeek));
//        BUG_RECORD_DEVICES.add(new Device("This month", String.valueOf(countThisMonth), countThisMonth));
//        BUG_RECORD_DEVICES.add(new Device("This year", String.valueOf(countThisYear), countThisYear));
//        BUG_RECORD_DEVICES.add(new Device("All time", String.valueOf(bugRecord.size()), bugRecord.size()));
//
//        DeviceAdapter deviceAdapter = new DeviceAdapter(BUG_RECORD_DEVICES);
//        homePageRecyclerView.setAdapter(deviceAdapter);
//
//    }

}