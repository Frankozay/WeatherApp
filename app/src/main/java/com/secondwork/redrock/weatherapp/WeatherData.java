package com.secondwork.redrock.weatherapp;

public class WeatherData {
    private String date;
    private String high;
    private String low;
    private String fengli;
    private String fengxiang;
    private String type;

    public WeatherData(String date,String high,String low,String fengli,String fengxiang,String type){
        this.date=date;
        this.high=high;
        this.low=low;
        this.fengli=fengli;
        this.fengxiang=fengxiang;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getLow() {
        return low;
    }


    public String getHigh() {
        return high;
    }

    public String getDate() {
        return date;
    }
}
