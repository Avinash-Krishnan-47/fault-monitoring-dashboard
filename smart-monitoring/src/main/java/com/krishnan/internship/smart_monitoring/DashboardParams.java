package com.krishnan.internship.smart_monitoring;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DashboardParams {
    private Timestamp timestamp ;
    private float temperature ;
    private float pressure ;
    private float vibration ;
    private float humidity ;
    private String statusMonitor ;

    public float getVibration() {
        return vibration ;
    }

    public float getTemperature() {
        return temperature ;
    }

    public float getPressure() {
        return pressure ;
    }

    public float getHumidity() {
        return humidity ;
    }

    public String getStatusMonitor() {
        return statusMonitor ;
    }

    public Timestamp getTimestamp() {
        return timestamp ;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature ;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity ;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure ;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp ;
    }

    public void setVibration(float vibration) {
        this.vibration = vibration ;
    }

    public void setStatusMonitor(String statusMonitor) {
        this.statusMonitor = statusMonitor ;
    }
}
