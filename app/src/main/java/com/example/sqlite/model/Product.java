package com.example.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private String category;
    private String name;
    private String description;
    private String seller;
    private float price;

    public Product(String category, String name, String description, String seller, float price) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
    }

    protected Product(Parcel in) {
        category = in.readString();
        name = in.readString();
        description = in.readString();
        seller = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[0];
        }
    };

    // Describe Content.
    @Override
    public int describeContents() {
        return 0;
    }

    // Getter and Setters.
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(seller);
        dest.writeFloat(price);
    }
}