package com.krishnan.internship.smart_monitoring;

import org.springframework.stereotype.Component;

@Component
public class DashboardParameters {

    float temperature ;
    float pressure ;
    float vibration ;
    float humidity ;

    public void setTemperature(float temperature) {
        this.temperature = temperature ;
    }

    public void setPressure(float pressure){
        this.pressure = pressure ;
    }

    public void setVibration(float vibration){
        this.vibration = vibration ;
    }

    public void setHumidity(float humidity){
        this.humidity = humidity ;
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

    public float getVibration() {
        return vibration ;
    }
}
