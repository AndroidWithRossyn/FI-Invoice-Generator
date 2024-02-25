package com.invoice.generator.model;

@SuppressWarnings("ALL")
public class SeePrintedItemListConstructor {
    String itemCount2;
    String itemName2;
    String itemPrice2;

    public SeePrintedItemListConstructor(String str, String str2, String str3) {
        this.itemName2 = str;
        this.itemCount2 = str2;
        this.itemPrice2 = str3;
    }

    public String getItemName2() {
        return this.itemName2;
    }

    public void setItemName2(String str) {
        this.itemName2 = str;
    }

    public String getItemCount2() {
        return this.itemCount2;
    }

    public void setItemCount2(String str) {
        this.itemCount2 = str;
    }

    public String getItemPrice2() {
        return this.itemPrice2;
    }

    public void setItemPrice2(String str) {
        this.itemPrice2 = str;
    }
}
