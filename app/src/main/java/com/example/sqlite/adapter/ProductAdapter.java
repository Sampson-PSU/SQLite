package com.example.sqlite.adapter;

// Import all necessary libraries and custom classes.

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Product;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

// Adapter for displaying product items in a RecyclerView.
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products; // List of products to display.
    private List<Product> selections = new ArrayList<>(); // List of selections to display.

    /**
     * Constructs a ProductAdapter with the given list of products.
     * @param products List of Products to display.
     */
    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the product item layout and create a ViewHolder.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Bind product data to the ViewHolder.
        Product product = products.get(position);

        holder.nameTextView.setText(product.getName());
        holder.descriptionTextView.setText(product.getDescription());
        holder.sellerTextView.setText(product.getSeller());
        holder.priceTextView.setText(NumberFormat.getCurrencyInstance().format(product.getPrice()));
        holder.pictureImageView.setImageURI(Uri.fromFile(new File(product.getPicture())));

        // Handle item click to update selected position
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selections.contains(product)) {
                    selections.remove(product);
                    holder.itemView.setBackgroundColor(Color.GREEN);
                } else {
                    selections.add(product);
                    holder.itemView.setBackgroundColor(Color.GRAY);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public List<Product> getSelections() {
        return selections;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, descriptionTextView, sellerTextView, priceTextView;
        public ImageView pictureImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize UI elements within the ViewHolder.
            nameTextView = itemView.findViewById(R.id.name_textview);
            descriptionTextView = itemView.findViewById(R.id.description_textview);
            sellerTextView = itemView.findViewById(R.id.seller_textview);
            priceTextView = (itemView).findViewById(R.id.price_textview);
            pictureImageView = itemView.findViewById(R.id.product_picture);
        }
    }
}