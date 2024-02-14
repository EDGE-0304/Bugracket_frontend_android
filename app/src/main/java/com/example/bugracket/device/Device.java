package com.example.bugracket.device;

public class Device {
    private String deviceName;
    private String owner;
    private String MACAddress;
    private String deviceType;
//    private int battery;
//    private int status;

//    public enum Status {
//        ON(0),
//        OFF(1),
//        IDLE(2);
//        private final int value;
//        Status(int value) {
//            this.value = value;
//        }
//        public int getValue() {
//            return value;
//        }
//        public static Status fromInt(int i) {
//            switch (i) {
//                case 0: return ON;
//                case 1: return OFF;
//                case 2: return IDLE;
//                default: return null;
//            }
//        }
//    }

    public Device(String deviceName, String owner, String MACAddress, String deviceType) {

        this.deviceName = deviceName;
        this.owner = owner;
        this.MACAddress = MACAddress;
        this.deviceType = deviceType;

    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

//    public int getBattery() {
//        return battery;
//    }
//
//    public void setBattery(int battery) {
//        this.battery = battery;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMacAddress() {
        return MACAddress;
    }

    public void setMacAddress(String macAddress) {
        this.MACAddress = macAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
