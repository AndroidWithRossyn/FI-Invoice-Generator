package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class SeePrintedList {
    String advance;
    String customerAddress;
    String customerName;
    String customerPhone;
    String deliFee;
    String discount;
    String itemCount;
    String itemName;
    String itemPrice;
    String orderDate;
    String orderId;
    String tax;

    public SeePrintedList(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        this.orderId = str;
        this.orderDate = str2;
        this.customerName = str3;
        this.customerAddress = str4;
        this.customerPhone = str5;
        this.itemName = str6;
        this.itemCount = str7;
        this.itemPrice = str8;
        this.deliFee = str9;
        this.advance = str10;
        this.tax = str11;
        this.discount = str12;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public String getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(String str) {
        this.orderDate = str;
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

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String str) {
        this.itemName = str;
    }

    public String getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(String str) {
        this.itemCount = str;
    }

    public String getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice(String str) {
        this.itemPrice = str;
    }

    public String getDeliFee() {
        return this.deliFee;
    }

    public void setDeliFee(String str) {
        this.deliFee = str;
    }

    public String getAdvance() {
        return this.advance;
    }

    public void setAdvance(String str) {
        this.advance = str;
    }

    public String getTax() {
        return this.tax;
    }

    public void setTax(String str) {
        this.tax = str;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String str) {
        this.discount = str;
    }
}
