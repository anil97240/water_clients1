package com.allysoftsolutions.water_clients1.admin_agent_side.product_model;

public class Product {
    private int  status;
    private String Product_name, Product_Price, Product_detail,image;

    public Product(String image,int status, String product_name, String product_Price, String product_detail) {
        image = image;
        this.status = status;
        Product_name = product_name;
        Product_Price = product_Price;
        Product_detail = product_detail;
    }

    public Product() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
