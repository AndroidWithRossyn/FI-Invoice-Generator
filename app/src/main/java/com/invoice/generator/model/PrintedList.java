package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class PrintedList {
    private String advance;
    private String customerAddress;
    private String customerName;
    private String customerPhone;
    private String deliFee;
    private String discount;
    private String idToSee;
    private String itemCount;
    private String itemName;
    private String itemPrice;
    private String itemTotal;
    private String orderDate;
    private String orderId;
    private String tax;

    public PrintedList(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.idToSee = str;
        this.orderId = str2;
        this.orderDate = str3;
        this.customerName = str4;
        this.customerAddress = str5;
        this.customerPhone = str6;
        this.deliFee = str7;
        this.advance = str8;
        this.tax = str9;
        this.discount = str10;
        this.itemName = str11;
        this.itemCount = str12;
        this.itemPrice = str13;
        this.itemTotal = str14;
    }

    public String getIdToSee() {
        return this.idToSee;
    }

    public void setIdToSee(String str) {
        this.idToSee = str;
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

    public String getItemTotal() {
        return this.itemTotal;
    }

    public void setItemTotal(String str) {
        this.itemTotal = str;
    }
}
