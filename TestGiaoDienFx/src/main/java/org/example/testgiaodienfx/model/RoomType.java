package org.example.testgiaodienfx.model;

public class RoomType {
    private int room_type_id;
    private String room_type_name;
    private String description;
    private int status;

    public RoomType(){

    }

    public RoomType(String room_type_name, String description, int status) {
        this.room_type_id = room_type_id;
        this.room_type_name = room_type_name;
        this.description = description;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getRoom_type_name() {
        return room_type_name;
    }

    public void setRoom_type_name(String room_type_name) {
        this.room_type_name = room_type_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
