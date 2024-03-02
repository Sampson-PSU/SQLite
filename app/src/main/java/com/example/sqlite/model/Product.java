package com.example.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private String name;
    private String description;
    private String seller;
    private float price;
    private String picture;

    public Product(String name, String description, String seller, float price, String picture) {

        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.picture = picture;
    }

    protected Product(Parcel in) {

        name = in.readString();
        description = in.readString();
        seller = in.readString();
        price = in.readFloat();
        picture = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Describe Content.
    @Override
    public int describeContents() {
        return 0;
    }

    // Getter and Setters.

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(seller);
        dest.writeFloat(price);
        dest.writeString(picture);
    }
}