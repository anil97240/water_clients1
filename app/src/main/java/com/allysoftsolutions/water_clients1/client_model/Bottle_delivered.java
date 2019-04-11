package com.allysoftsolutions.water_clients1.client_model;

public class Bottle_delivered {
    private String Amount_collected;
    private String Bottle_type;
    private String Padding_amount;
    private String QR_code;
    private String Total_amount;
    private String Delivry_date;

    public Bottle_delivered() {
    }

    public Bottle_delivered(String amount_collected, String bottle_type, String padding_amount, String QR_code, String total_amount, String delivry_date) {
        Amount_collected = amount_collected;
        Bottle_type = bottle_type;
        Padding_amount = padding_amount;
        this.QR_code = QR_code;
        Total_amount = total_amount;
        Delivry_date = delivry_date;
    }

    public String getAmount_collected() {
        return Amount_collected;
    }

    public void setAmount_collected(String amount_collected) {
        Amount_collected = amount_collected;
    }

    public String getBottle_type() {
        return Bottle_type;
    }

    public void setBottle_type(String bottle_type) {
        Bottle_type = bottle_type;
    }

    public String getPadding_amount() {
        return Padding_amount;
    }

    public void setPadding_amount(String padding_amount) {
        Padding_amount = padding_amount;
    }

    public String getQR_code() {
        return QR_code;
    }

    public void setQR_code(String QR_code) {
        this.QR_code = QR_code;
    }

    public String getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(String total_amount) {
        Total_amount = total_amount;
    }

    public String getDelivry_date() {
        return Delivry_date;
    }

    public void setDelivry_date(String delivry_date) {
        Delivry_date = delivry_date;
    }
}
