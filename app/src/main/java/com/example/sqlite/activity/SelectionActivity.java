package com.example.sqlite.activity;

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

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private String PRODUCT_OPTION;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public ProductDatabaseHelper databaseHelper;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // Instantiate the UI elements.
        recyclerView = findViewById(R.id.selection_recycler_view);

        Button btnAllProducts = findViewById(R.id.btn_all_products);
        btnAllProducts.setOnClickListener(this);

        // Create an Instance of the ProductDatabaseHelper to manipulate the database.
        databaseHelper = new ProductDatabaseHelper(this);

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