package com.allysoftsolutions.water_clients1.admin_agent_side;

public class sliding_image {
    private String Product_name, Product_Price, Product_detail,Qry, image;

    public sliding_image() {
    }

    public sliding_image(String product_name, String product_Price, String product_detail, String qry, String image) {
        Product_name = product_name;
        Product_Price = product_Price;
        Product_detail = product_detail;
        Qry = qry;
        this.image = image;
    }

    public sliding_image(String image) {
        this.image = image;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_detail() {
        return Product_detail;
    }

    public void setProduct_detail(String product_detail) {
        Product_detail = product_detail;
    }

    public String getQry() {
        return Qry;
    }

    public void setQry(String qry) {
        Qry = qry;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}