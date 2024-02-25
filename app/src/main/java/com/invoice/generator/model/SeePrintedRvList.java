package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class SeePrintedRvList {
    String itemCount;
    String itemName;
    String itemPrice;

    public SeePrintedRvList(String str, String str2, String str3) {
        this.itemName = str;
        this.itemCount = str2;
        this.itemPrice = str3;
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
}
