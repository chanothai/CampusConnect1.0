package com.company.zicure.registerkey;

import android.location.Location;

import java.util.Date;

/**
 * Created by 4GRYZ52 on 10/26/2016.
 */
public class Position {

    public Position(String deviceId, Location location, double battery) {
        this.deviceId = deviceId;
        time = new Date(location.getTime());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        speed = location.getSpeed() * 1.943844; // speed in knots
        course = location.getBearing();
        this.battery = battery;
    }

    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private String deviceId;
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    private Date time;
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }

    private double latitude;
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    private double longitude;
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    private double altitude;
    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }

    private double speed;
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    private double course;
    public double getCourse() { return course; }
    public void setCourse(double course) { this.course = course; }

    private double battery;
    public double getBattery() { return battery; }
    public void setBattery(double battery) { this.battery = battery; }
}
