package com.cowman.models;

public class Site {
    private int _id;
    private String _title;
    private double _latitude;
    private double _longitude;
    private String _imageUrl;

    public Site(int id, String title, double latitude, double longitude) {
        _id = id;
        _title = title;
        _latitude = latitude;
        _longitude = longitude;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public double getLatitude() {
        return _latitude;
    }

    public void setLatitude(double latitude) {
        this._latitude = latitude;
    }

    public double getLongitude() {
        return _longitude;
    }

    public void setLongitude(double longitude) {
        this._longitude = longitude;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getImageUrl() {
        return _imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this._imageUrl = imageUrl;
    }
}
