package org.example.testgiaodienfx.model;

public class Customers {
    private int customer_id;
    private String name;
    private String phone_number;
    private String email;
    private String address;
    private String id_card;

    public Customers() {

    }
    public Customers(String name, String phone_number, String email, String address, String id_card) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.id_card = id_card;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
}
