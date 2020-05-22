package com.example.myapplication;


public class LocationClass {
    int _id;
    Double _latitude;
    Double _longitude;
    String _time;

    public LocationClass(){   }
    public LocationClass(int _id, Double _latitude, Double _longitude, String _time){
        this._id = _id;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._time = _time;
    }

    public LocationClass(Double _latitude, Double _longitude, String _time){
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._time = _time;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public Double getLatitude(){
        return this._latitude;
    }

    public void setLatitude(Double latitude){
        this._latitude = latitude;
    }

    public Double getLongitude(){
        return this._longitude;
    }

    public void setLongitude(Double longitude){
        this._longitude = longitude;
    }
    public String getTime(){
        return this._time;
    }

    public void setTime(String time){
        this._time = time;
    }
}
