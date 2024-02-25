package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class MyItemsList {
    private byte imageByte;
    private String myItemId;
    private String myItemInStock;
    private String myItemName;
    private String myItemPrice;

    public MyItemsList(String str, byte b, String str2, String str3, String str4) {
        this.myItemId = str;
        this.imageByte = b;
        this.myItemName = str2;
        this.myItemPrice = str3;
        this.myItemInStock = str4;
    }

    public String getMyItemId() {
        return this.myItemId;
    }

    public void setMyItemId(String str) {
        this.myItemId = str;
    }

    public byte getImageByte() {
        return this.imageByte;
    }

    public void setImageByte(byte b) {
        this.imageByte = b;
    }

    public String getMyItemName() {
        return this.myItemName;
    }

    public void setMyItemName(String str) {
        this.myItemName = str;
    }

    public String getMyItemPrice() {
        return this.myItemPrice;
    }

    public void setMyItemPrice(String str) {
        this.myItemPrice = str;
    }

    public String getMyItemInStock() {
        return this.myItemInStock;
    }

    public void setMyItemInStock(String str) {
        this.myItemInStock = str;
    }
}
