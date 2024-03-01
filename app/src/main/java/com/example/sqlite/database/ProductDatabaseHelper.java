package com.example.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.sqlite.model.Product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    public static final String KEY_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SELLER = "seller";
    public static final String  COLUMN_PRICE = "price";
    public static final String COLUMN_PICTURE = "picture";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createProductsTable());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            /** Update the database to version 2.
             *  You may need to create new columns or add new tables to the database.
             */
        }
    }

    // Query to create the database.
    private String createProductsTable(){
        String QUERY_CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_SELLER + " TEXT," +
                COLUMN_PRICE + " TEXT," +
                COLUMN_PICTURE + " REAL" +
                ")";
        return QUERY_CREATE_PRODUCT_TABLE;
    }
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_PRODUCTS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Product product = new Product(

                        cursor.getString(1), // NAME
                        cursor.getString(2), // DESCRIPTION
                        cursor.getString(3), // SELLER
                        cursor.getFloat(4),  // PRICE
                        cursor.getInt(5)     // PICTURE
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

    // Convert from bitmap to byte array.
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void populateProductsDatabase(){

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values = new ContentValues();
        values.put(COLUMN_NAME, "Double bacon Cheeseburger");
        values.put(COLUMN_DESCRIPTION, "Cheeseburger with bacon, mayo, lettuce and tomatoes.");
        values.put(COLUMN_SELLER, "Denny's");
        values.put(COLUMN_PRICE, 9.80);
        values.put(COLUMN_PICTURE, 1);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NAME, "Name 2");
        values.put(COLUMN_DESCRIPTION, "Desc 2");
        values.put(COLUMN_SELLER, "Seller 2");
        values.put(COLUMN_PRICE, "$1.00");
        values.put(COLUMN_PICTURE, 2);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NAME, "Name 3");
        values.put(COLUMN_DESCRIPTION, "Desc 3");
        values.put(COLUMN_SELLER, "Seller 3");
        values.put(COLUMN_PRICE, "");
        values.put(COLUMN_PICTURE, 3);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NAME, "Name 4");
        values.put(COLUMN_DESCRIPTION, "Desc 4");
        values.put(COLUMN_SELLER, "Seller 4");
        values.put(COLUMN_PRICE, "");
        values.put(COLUMN_PICTURE, 4);
        database.insert(TABLE_PRODUCTS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NAME, "Name 5");
        values.put(COLUMN_DESCRIPTION, "Desc 5");
        values.put(COLUMN_SELLER, "Seller 5");
        values.put(COLUMN_PRICE, "");
        values.put(COLUMN_PICTURE, 5);
        database.insert(TABLE_PRODUCTS, null, values);

        database.close();
    }
}