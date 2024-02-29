package com.example.sqlite.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sqlite.R;

import java.util.List;

import com.example.sqlite.adapter.ProductAdapter;
import com.example.sqlite.adapter.SpacingItemDecorator;
import com.example.sqlite.database.ProductDatabaseHelper;
import com.example.sqlite.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the UI elements.
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton floatingActionBtn = findViewById(R.id.floating_action_btn);
        Button btnBackSelection = findViewById(R.id.btn_back_selection);

        floatingActionBtn.setOnClickListener(this);
        btnBackSelection.setOnClickListener(this);

        // Create an instance of the ProductDatabaseHelper to manipulate the database.
        List<Product> products;
        try (ProductDatabaseHelper databaseHelper = new ProductDatabaseHelper(this)) {

            // Intent to recover the String passed as parameter in the previous activity.
            Intent intent = getIntent();
            String option_selected = intent.getStringExtra("option_selected");

            // Check if database is empty.
            // If empty, populate database and get All the products.
            if (databaseHelper.isDatabaseEmpty()) {
                databaseHelper.populateProductsDatabase();
            }

            // Check which option the user selected, and call the method appropriate.
            // Select a specific category, pass as parameter in the getProductByCategory(...)
            // method defined in the DatabaseHelper.
            if (option_selected != null) {
                if (option_selected.equals("all")) {
                    products = databaseHelper.getAllProducts();
                } else {
                    products = databaseHelper.getProductByCategory(option_selected.toLowerCase());
                }

                // Populated list of products.
                // Configure the adapter and assign a product adapter to the RecyclerView.
                ProductAdapter productAdapter = new ProductAdapter(products);
                recyclerView.setAdapter(productAdapter);
                recyclerView.addItemDecoration(new SpacingItemDecorator(0));

                // The RecyclerView needs a LayoutManager to draw the objects.
                // This will be responsible for measuring and positioning each item view within the RecyclerView.
                LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.floating_action_btn) {
            showDialog();
        } else if (id == R.id.btn_back_selection) {
            goBackToPreviousActivity();
        }
    }

    private void goBackToPreviousActivity() {
            Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
            startActivity(intent);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Product");
        builder.setMessage("Would you like to add a new product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(MainActivity.this, "The action was cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}