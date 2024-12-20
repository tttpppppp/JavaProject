package org.example.testgiaodienfx.model;

public class Rooms {
    private int room_id;
    private int room_type_id;
    private float price_per_night;
    private float price_per_hour;
    private int status;
    private String room_type_name;
    private String description;

    public Rooms(){

    }
    public Rooms(int status, float price_per_hour, float price_per_night, int room_type_id, int room_id) {
        this.status = status;
        this.price_per_hour = price_per_hour;
        this.price_per_night = price_per_night;
        this.room_type_id = room_type_id;
        this.room_id = room_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public float getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(float price_per_night) {
        this.price_per_night = price_per_night;
    }

    public float getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(float price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoom_type_name() {
        return room_type_name;
    }

    public void setRoom_type_name(String room_type_name) {
        this.room_type_name = room_type_name;
    }
    public int getFloor() {
        return Integer.parseInt(String.valueOf(room_id).substring(0, 1)); // Extract first digit
    }
}
