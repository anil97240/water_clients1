package com.allysoftsolutions.water_clients1.client_model;



public class Client{

    private String image;
    private String Customer_name, Mobile_number, Address, Local_address, Customer_qrcode;

    public Client() {

    }

    public Client(String image, String customer_name, String mobile_number, String address) {
        this.image = image;
        Customer_name = customer_name;
        Mobile_number = mobile_number;
        Address = address;
    }

    public Client(String image, String customer_name) {
        this.image = image;
        Customer_name = customer_name;
    }

    public Client(String image, String customer_name, String mobile_number, String address, String local_address, String customer_qrcode) {
        this.image = image;
        Customer_name = customer_name;
        Mobile_number = mobile_number;
        Address = address;
        Local_address = local_address;
        Customer_qrcode = customer_qrcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCustomer_name() {
        return Customer_name;
    }

    public void setCustomer_name(String customer_name) {
        Customer_name = customer_name;
    }

    public String getMobile_number() {
        return Mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        Mobile_number = mobile_number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocal_address() {
        return Local_address;
    }

    public void setLocal_address(String local_address) {
        Local_address = local_address;
    }

    public String getCustomer_qrcode() {
        return Customer_qrcode;
    }

    public void setCustomer_qrcode(String customer_qrcode) {
        Customer_qrcode = customer_qrcode;
    }
}
