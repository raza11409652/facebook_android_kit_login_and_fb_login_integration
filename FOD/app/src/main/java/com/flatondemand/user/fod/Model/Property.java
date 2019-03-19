package com.flatondemand.user.fod.Model;

public class Property {
    private String property , addedOn , vacant , total,uid , fulladdress , address , coverImage , price;
    public  Property(){

    }
    public Property(String property, String addedOn, String vacant, String total, String uid, String fulladdress, String address, String coverImage, String price) {
        this.property = property;
        this.addedOn = addedOn;
        this.vacant = vacant;
        this.total = total;
        this.uid = uid;
        this.fulladdress = fulladdress;
        this.address = address;
        this.coverImage = coverImage;
        this.price = price;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getVacant() {
        return vacant;
    }

    public void setVacant(String vacant) {
        this.vacant = vacant;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
