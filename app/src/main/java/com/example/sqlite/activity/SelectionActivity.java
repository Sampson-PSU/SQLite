package com.example.sqlite.activity;

// Import all necessary libraries and custom classes.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sqlite.R;
import com.example.sqlite.adapter.ProductAdapter;
import com.example.sqlite.adapter.SpacingItemDecorator;
import com.example.sqlite.database.ProductDatabaseHelper;
import com.example.sqlite.model.Product;

import java.util.List;

// Displays a list of product options and handles user interaction.
public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private String PRODUCT_OPTION; // Stores the selected product option.
    private RecyclerView recyclerView; // Displays a list of product options.
    private LinearLayoutManager layoutManager; // Manages the layout of RecyclerView items.
    public ProductDatabaseHelper databaseHelper; // Provides database operations.
    private ProductAdapter productAdapter; // Handles product data binding.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // Initialize the UI elements.
        recyclerView = findViewById(R.id.selection_recycler_view);
        Button btnAllProducts = findViewById(R.id.btn_all_products);

        // Set click listener for the "All Products" button.
        btnAllProducts.setOnClickListener(this);

        // Create an Instance of the ProductDatabaseHelper to manipulate the database.
        databaseHelper = new ProductDatabaseHelper(this);

        // Retrieve the list of products from the database.
        List<Product> products;

        products = databaseHelper.getAllProducts();

        // Configure the adapter and assign a product to the RecyclerView.
        productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.addItemDecoration(new SpacingItemDecorator(0));

        // LayoutManager for RecyclerView to draw product objects.
        // Responsible for measuring and positioning each item view within RecyclerView.
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_all_products) {
            PRODUCT_OPTION = "all";
        }
        if (PRODUCT_OPTION.equals(("all"))) {
            Intent intent = new Intent(SelectionActivity.this, ProductActivity.class);
            String KEY_OPTION_SELECTED = "option_selected";
            intent.putExtra(KEY_OPTION_SELECTED, PRODUCT_OPTION);
            startActivity(intent);
        }
    }
}