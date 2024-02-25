package com.invoice.generator.model;

public class ItemList {
    private final String itemCount;
    private String itemName;
    private String itemPrice;
    private final double itemTotal;

    public ItemList(String str, String str2, String str3, double d) {
        this.itemName = str;
        this.itemCount = str2;
        this.itemPrice = str3;
        this.itemTotal = d;
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

    public String getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice(String str) {
        this.itemPrice = str;
    }

    public double getItemTotal() {
        return this.itemTotal;
    }

}
