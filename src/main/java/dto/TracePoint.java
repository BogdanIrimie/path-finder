package dto;

public class TracePoint {
    private double approximatedTime;
    private double xAxis;
    private double yAxis;

    public TracePoint() {
    }

    public TracePoint(double approximatedTime, double xAxis, double yAxis) {
        this.approximatedTime = approximatedTime;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public double getApproximatedTime() {
        return approximatedTime;
    }

    public void setApproximatedTime(double approximatedTime) {
        this.approximatedTime = approximatedTime;
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

    @Override
    public String toString() {
        return "TracePoint{" +
                "approximatedTime=" + approximatedTime +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                '}';
    }
}
