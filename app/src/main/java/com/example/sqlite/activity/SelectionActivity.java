package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlite.R;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerProduct;
    private String PRODUCT_OPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button btnAllProducts = findViewById(R.id.btn_all_products);
        Button btnProductByCategory = findViewById(R.id.btn_category_products);
        Button btnSendEmail = findViewById(R.id.btn_send_email);

        spinnerProduct =findViewById(R.id.spinner_movie_category);

        btnAllProducts.setOnClickListener(this);
        btnProductByCategory.setOnClickListener(this);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set click listener for the email button.
                // Create an implicit intent to send an email.
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:scrooksjr@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Selected Products");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Here are the selected products");

                // Start the email intent
                startActivity(emailIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_all_products) {
            PRODUCT_OPTION = "all";
        } else if (id == R.id.btn_category_products) {
            PRODUCT_OPTION = spinnerProduct.getSelectedItem().toString();
        }

        if (PRODUCT_OPTION.equals("---"))
            Toast.makeText(this, "Select a valid option", Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(SelectionActivity.this, MainActivity.class);
            String KEY_OPTION_SELECTED = "option_selected";
            intent.putExtra(KEY_OPTION_SELECTED, PRODUCT_OPTION);
            startActivity(intent);
        }
    }
}