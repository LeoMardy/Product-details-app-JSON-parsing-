package com.leoapptechnology.productsdetailsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.leoapptechnology.productsdetailsapp.databinding.ActivityProductBinding;
import com.squareup.picasso.Picasso;

public class Product_Activity extends AppCompatActivity {

    ActivityProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        String headerImage = intent.getStringExtra("imageIntent");
        String headerTitle = intent.getStringExtra("titleIntent");
        String headerDescription = intent.getStringExtra("descIntent");
        String productRating = intent.getStringExtra("ratingIntent");
        String productPrice = intent.getStringExtra("priceIntent");
        String brand = intent.getStringExtra("brandIntent");

        Picasso.get().load(headerImage).into(binding.imageView7);
        binding.titleText.setText(headerTitle);
        binding.descText.setText(headerDescription);
        binding.brandText.setText("Brand: "+brand);
        binding.price.setText("Price: "+productPrice+" $");
        binding.ratingText.setText("Rating : "+productRating+"/5");

        binding.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(Product_Activity.this)
                        .setTitle("Comfirm Order?")
                        .setMessage(headerTitle+"\n"+headerDescription+"\n"+"Price: "+productPrice)
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(Product_Activity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Product_Activity.this, "Order Canceled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

    }
}