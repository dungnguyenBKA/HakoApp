package com.Dungnm.model;

public class ItemLine {
    private String itemText;
    private String itemImage;

    public ItemLine(String itemText, String itemImage) {
        this.itemText = itemText;
        this.itemImage = itemImage;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
