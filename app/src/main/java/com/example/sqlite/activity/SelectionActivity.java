package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sqlite.R;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private final String KEY_OPTION_SELECTED = "option_selected";
    private String PRODUCT_OPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button btnAllProducts = findViewById(R.id.btn_all_products);

        btnAllProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_all_products) {
            PRODUCT_OPTION = "all";

        }
        if (PRODUCT_OPTION.equals(("all"))) {
            Intent intent = new Intent(SelectionActivity.this, MainActivity.class);
            intent.putExtra(KEY_OPTION_SELECTED, PRODUCT_OPTION);
            startActivity(intent);
        }
    }
}