package com.allysoftsolutions.water_clients1;

public class cliet_data {
    String Agent_email,Amount_collected,Amount_total,Collected_Date,Padding_amount,QR_code;

    public cliet_data() {
    }

    public cliet_data(String agent_email, String amount_collected, String amount_total, String collected_Date, String padding_amount, String QR_code) {
        Agent_email = agent_email;
        Amount_collected = amount_collected;
        Amount_total = amount_total;
        Collected_Date = collected_Date;
        Padding_amount = padding_amount;
        this.QR_code = QR_code;
    }

    public String getAgent_email() {
        return Agent_email;
    }

    public void setAgent_email(String agent_email) {
        Agent_email = agent_email;
    }

    public String getAmount_collected() {
        return Amount_collected;
    }

    public void setAmount_collected(String amount_collected) {
        Amount_collected = amount_collected;
    }
    public String getAmount_total() {
        return Amount_total;
    }

    public void setAmount_total(String amount_total) {
        Amount_total = amount_total;
    }

    public String getCollected_Date() {
        return Collected_Date;
    }

    public void setCollected_Date(String collected_Date) {
        Collected_Date = collected_Date;
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

    public void setQR_codel(String QR_code) {
        this.QR_code = QR_code;
    }


}
