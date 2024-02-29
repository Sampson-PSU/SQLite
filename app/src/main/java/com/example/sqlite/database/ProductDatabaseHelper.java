package com.example.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SELLER = "seller";
    private static final String KEY_PRICE = "price";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Query to create the database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_CATEGORY + " TEXT," +
                KEY_NAME + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_SELLER + " TEXT," +
                KEY_PRICE + " REAL" + ")";
        db.execSQL(QUERY_CREATE_PRODUCT_TABLE);
    }

    public List<Product> getProductByCategory (String category) {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_CATEGORY + " = ?";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{category});

        if(cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1), // CATEGORY
                        cursor.getString(2), // NAME
                        cursor.getString(3), // DESCRIPTION
                        cursor.getString(4), // SELLER
                        cursor.getFloat(5)   // PRICE
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return productList;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1), // CATEGORY
                        cursor.getString(2), // NAME
                        cursor.getString(3), // DESCRIPTION
                        cursor.getString(4), // SELLER
                        cursor.getFloat(5)   // PRICE
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return productList;
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count > 0) {
                isEmpty = false;
            }
            cursor.close();
        }
        return isEmpty;
    }

    public void populateProductsDatabase() {
        // Calling a method to get writable database.
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, "Cat 1");
        values.put(KEY_NAME, "Name 1");
        values.put(KEY_DESCRIPTION, "Description 1");
        values.put(KEY_SELLER, "Seller 1");
        values.put(KEY_PRICE, "$1.00");
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_CATEGORY, "Cat 2");
        values.put(KEY_NAME, "Name 2");
        values.put(KEY_DESCRIPTION, "Description 2");
        values.put(KEY_SELLER, "Seller 2");
        values.put(KEY_PRICE, "$2.00");
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_CATEGORY, "Cat 3");
        values.put(KEY_NAME, "Name 3");
        values.put(KEY_DESCRIPTION, "Description 3");
        values.put(KEY_SELLER, "Seller 3");
        values.put(KEY_PRICE, "$3.00");
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_CATEGORY, "Cat 4");
        values.put(KEY_NAME, "Name 4");
        values.put(KEY_DESCRIPTION, "Description 4");
        values.put(KEY_SELLER, "Seller 4");
        values.put(KEY_PRICE, "$4.00");
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(KEY_CATEGORY, "Cat 5");
        values.put(KEY_NAME, "Name 5");
        values.put(KEY_DESCRIPTION, "Description 5");
        values.put(KEY_SELLER, "Seller 5");
        values.put(KEY_PRICE, "$5.00");
        database.insert(TABLE_PRODUCTS, null, values);

        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}