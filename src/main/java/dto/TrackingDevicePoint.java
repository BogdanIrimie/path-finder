package dto;

public class TrackingDevicePoint implements Point {

    private double xAxis;
    private double yAxis;

    public TrackingDevicePoint() {
    }

    public TrackingDevicePoint(double xAxis, double yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
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
}
