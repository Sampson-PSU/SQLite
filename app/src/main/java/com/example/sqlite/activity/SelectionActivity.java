package com.example.sqlite.activity;

// Import all necessary libraries and custom classes.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.adapter.ProductAdapter;
import com.example.sqlite.adapter.SpacingItemDecorator;
import com.example.sqlite.database.ProductDatabaseHelper;
import com.example.sqlite.model.Product;

import java.util.ArrayList;
import java.util.List;

// Displays a list of product options and handles user interaction.
public class SelectionActivity extends AppCompatActivity {

    private String PRODUCT_OPTION; // Stores the selected product option.
    private RecyclerView recyclerView; // Displays a list of product options.
    private LinearLayoutManager layoutManager; // Manages the layout of RecyclerView items.
    public ProductDatabaseHelper databaseHelper; // Provides database operations.
    private ProductAdapter productAdapter; // Handles product data binding.
    private Button btnSelectedProducts; // Handles product data binding.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // Initialize the UI elements.
        recyclerView = findViewById(R.id.selection_recycler_view);
        Button btnAllProducts = findViewById(R.id.btn_all_products);
        btnSelectedProducts = findViewById(R.id.btn_selected_products);

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

        btnSelectedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_selected_products) {
                    // Get selected products from the adapter.
                    List<Product> selections = productAdapter.getSelections();
                    // If there are selected products, create intent to launch the ProductActivity.
                    if (!selections.isEmpty()) {
                        Intent selectionIntent = new Intent(SelectionActivity.this, ProductActivity.class);
                        String KEY_OPTION_SELECTIONS = "option_selections";
                        selectionIntent.putExtra(KEY_OPTION_SELECTIONS, new ArrayList<>(selections));
                        startActivity(selectionIntent);
                    }
                }

                btnAllProducts.setOnClickListener(new View.OnClickListener() {
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
                });
            }
        });
    }
}
