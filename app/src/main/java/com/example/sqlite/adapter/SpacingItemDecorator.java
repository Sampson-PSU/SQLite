package com.example.sqlite.adapter;

// Import all necessary libraries.
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

// Constructor to set the spacing value.
public class SpacingItemDecorator extends RecyclerView.ItemDecoration {
    private int spacing;

    public SpacingItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Set left, right, and bottom margins for all items.
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        // Add top margin only for the first item to avoid double spacing between products.
        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing;
        } else {
            outRect.top = 0;
        }
    }
}