package com.cowman.models;

import org.joda.time.DateTime;

import java.util.Date;

public class Event {
    private String _header;
    private DateTime _date;
    private String _description;
    private String _startLocation;
    private String _endLocation;

    public Event(String header, DateTime date, String description, String startLocation, String endLocation) {
        this._header = header;
        this._date = date;
        this._description = description;
        this._startLocation = startLocation;
        this._endLocation = endLocation;
    }

    public String getHeader() {
        return _header;
    }

    public void setHeader(String header) {
        this._header = header;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public DateTime getDate() {
        return _date;
    }

    public void setDate(DateTime date) {
        this._date = date;
    }

    public String getStartLocation() {
        return _startLocation;
    }

    public void setStartLocation(String startLocation) {
        this._startLocation = startLocation;
    }

    public String getEndLocation() {
        return _endLocation;
    }

    public void setEndLocation(String endLocation) {
        this._endLocation = endLocation;
    }
}
