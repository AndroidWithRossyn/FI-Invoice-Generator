package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class MyCustomersList {
    private String customerAddress;
    private String customerId;
    private String customerName;
    private String customerPhone;

    public MyCustomersList(String str, String str2, String str3, String str4) {
        this.customerId = str;
        this.customerName = str2;
        this.customerAddress = str3;
        this.customerPhone = str4;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String str) {
        this.customerId = str;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String str) {
        this.customerName = str;
    }

    public String getCustomerAddress() {
        return this.customerAddress;
    }

    public void setCustomerAddress(String str) {
        this.customerAddress = str;
    }

    public String getCustomerPhone() {
        return this.customerPhone;
    }

    public void setCustomerPhone(String str) {
        this.customerPhone = str;
    }
}
