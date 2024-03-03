package com.example.sqlite.activity;

// Import all necessary libraries and custom classes.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.model.Product;
import com.example.sqlite.adapter.ProductAdapter;
import com.example.sqlite.adapter.SpacingItemDecorator;

import com.example.sqlite.database.ProductDatabaseHelper;

import java.text.NumberFormat;
import java.util.List;

/**
 * Displays a list of products using a RecyclerView.
 * Handles user interaction and database operations.
 */
public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView; // Displays the list of products.
    private LinearLayoutManager layoutManager;// Manages layout of RecyclerView items.
    public ProductDatabaseHelper databaseHelper; // Provides database operations.
    private ProductAdapter productAdapter; // Handles product data binding.
    private Button btnBackSelection; // Button to navigate back.
    private Button openEmailButton; // Button to open email.
    private String option_selected; // Stores the selected option.
    List<Product> products; // List of products retrieved from database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Initialize the UI elements.
        recyclerView = findViewById(R.id.recycler_view);

        btnBackSelection = findViewById(R.id.btn_all_products);
        openEmailButton = findViewById(R.id.open_email_button);

        // Set click listener.
        btnBackSelection.setOnClickListener(this);
        openEmailButton.setOnClickListener(this);

        // Create an Instance of the ProductDatabaseHelper to manipulate the database.
        databaseHelper = new ProductDatabaseHelper(this);

        //Create an intent to recover the String passed as a parameter in the previous activity.
        Intent intent = getIntent();
        option_selected = intent.getStringExtra("option_selected");

        // Check if the database is empty.
        // If empty, populate and get All products.
        if (databaseHelper.isDatabaseEmpty()) {
            databaseHelper.populateProductsDatabase();
        }

        // Option is equal to "all" then get all products.
        if (option_selected.equals("all")) {
            products = databaseHelper.getAllProducts();
        } else {
            return;
        }

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
        int id = v.getId();
        if (id == R.id.btn_all_products) {
            goBackToPreviousActivity();
        } else if (id == R.id.open_email_button) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:sweng888mobileapps@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Selected Products");

            StringBuilder databaseOutput = new StringBuilder("Here are the selected products: " + "\n\n");
            List<Product> products = databaseHelper.getAllProducts();
            for(Product item : products) {
                databaseOutput.append(item.getName()).append("\n");
                databaseOutput.append(item.getDescription()).append("\n");
                databaseOutput.append(item.getSeller()).append("\n");
                databaseOutput.append(NumberFormat.getCurrencyInstance().format(item.getPrice())).append("\n\n");
            }
            emailIntent.putExtra(Intent.EXTRA_TEXT, databaseOutput.toString());

            startActivityIfNeeded(emailIntent, 123);
        }
    }

    // Handles the result of an activity (email Sending).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getBaseContext(), "Email Successfully Sent!", Toast.LENGTH_SHORT).show();
                // Clear the list of products at the specified position.
                products.clear();

                // Notify the adapter that the data has changed.
                productAdapter.notifyItemRangeRemoved(0,products.size());
            }
        }
    }

    // Navigate back to the previous activity (SelectionActivity).
    private void goBackToPreviousActivity() {
        Intent intent = new Intent(ProductActivity.this, SelectionActivity.class);
        startActivity(intent);
    }
}