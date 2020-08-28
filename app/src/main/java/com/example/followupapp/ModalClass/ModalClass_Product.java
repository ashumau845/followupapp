package com.example.followupapp.ModalClass;

public class ModalClass_Product {
    String productName;
    String quantity;
    String rate;
    double total;


    public ModalClass_Product(String productName, String quantity, String rate, double total) {
        this.productName = productName;
        this.quantity = quantity;
        this.rate = rate;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
