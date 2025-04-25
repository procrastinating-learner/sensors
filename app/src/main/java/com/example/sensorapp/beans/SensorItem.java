package com.example.sensorapp.beans;


public class SensorItem {
    public final String id;
    public final String name;
    public final String type;
    public final String vendor;
    public final String version;


    public SensorItem(String id, String name, String type, String vendor, String version) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.vendor = vendor;
        this.version = version;
    }

    @Override
    public String toString() {
        return name;
    }
}
