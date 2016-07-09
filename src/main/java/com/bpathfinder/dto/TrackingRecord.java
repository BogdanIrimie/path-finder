package com.bpathfinder.dto;

public class TrackingRecord {
    private double time;
    private double xAxis;
    private double yAxis;
    private int rssi;

    public TrackingRecord()  {
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "TrackingRecord{" +
                "time=" + time +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", rssi=" + rssi +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        TrackingRecord trackingRecord = (TrackingRecord)obj;
        return this.xAxis == trackingRecord.xAxis && this.yAxis == trackingRecord.yAxis;
    }

    @Override
    public int hashCode() {
        return (xAxis + yAxis + "").hashCode();
    }



}
