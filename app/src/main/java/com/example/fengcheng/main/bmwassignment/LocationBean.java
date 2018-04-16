package com.example.fengcheng.main.bmwassignment;

/**
 * @Package com.example.fengcheng.main.bmwassignment
 * @FileName LocationBean
 * @Date 4/16/18, 2:06 AM
 * @Author Created by fengchengding
 * @Description BmwAssignment
 */

public class LocationBean {
    private String id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String arrivalTime;

    public LocationBean(String id, String name, String latitude, String longitude, String address, String arrivalTime) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.arrivalTime = arrivalTime;
    }

    /**
     * @param lastLatitude  current lat
     * @param lastLongitude current longt
     * calculate distance from current location to given location
     */

    public Double countDistance(Double lastLatitude, Double lastLongitude){
        Double distance = Math.pow((lastLongitude - Double.valueOf(longitude)), 2) + Math.pow((lastLatitude - Double.valueOf(latitude)), 2);
        return Math.sqrt(distance);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


}
