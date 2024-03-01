package com.example.sqlite.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.R;
import com.example.sqlite.model.Product;
import com.example.sqlite.adapter.ProductAdapter;
import com.example.sqlite.adapter.SpacingItemDecorator;

import com.example.sqlite.database.ProductDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public ProductDatabaseHelper databaseHelper;
    private ProductAdapter productAdapter;
    private Button btnBackSelection;
    private Button sendEmailButton;
    private FloatingActionButton floatingActionBtn;
    private String option_selected;
    private ImageView productPicture;
    private String productInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the UI elements.
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionBtn = findViewById(R.id.floating_action_btn);
        btnBackSelection = findViewById(R.id.btn_back_selection);
        sendEmailButton = findViewById(R.id.send_email_button);

        floatingActionBtn.setOnClickListener(this);
        btnBackSelection.setOnClickListener(this);
        sendEmailButton.setOnClickListener(this);

        // Create an Instance of the ProductDatabaseHelper to manipulate the database.
        databaseHelper = new ProductDatabaseHelper(this);

        List<Product> products;

        //Create an intent to recover the String passed as a parameter in the previous activity.
        Intent intent = getIntent();
        option_selected = (String) intent.getStringExtra("option_selected");

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
        if (id == R.id.floating_action_btn) {
        } else if (id == R.id.btn_back_selection) {
            goBackToPreviousActivity();
        } else if (id == R.id.send_email_button) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:scrooksjr@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Selected Products");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Here are the selected products:\n" + option_selected);

            //Intent emailIntent = new Intent(Intent.ACTION_SEND);
            //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/data/data/com.example.sqlite/databases/products_db")));
            //startActivity(Intent.createChooser(emailIntent, "Send email"));

            // Start the email intent
            startActivity(emailIntent);
        }
    }

    private void goBackToPreviousActivity() {
        Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
        startActivity(intent);
    }

    /*private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Project");
        builder.setMessage("Do you want to add a new project?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "The action was cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
}