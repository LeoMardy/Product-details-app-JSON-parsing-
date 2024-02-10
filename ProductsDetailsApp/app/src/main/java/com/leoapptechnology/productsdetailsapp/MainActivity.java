package com.leoapptechnology.productsdetailsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.leoapptechnology.productsdetailsapp.databinding.ActivityMainBinding;
import com.leoapptechnology.productsdetailsapp.databinding.RvLayoutBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String,String>> myArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://dummyjson.com/products";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            binding.progressBar.setVisibility(View.GONE);
                Log.d("serverRes",response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("products");

                    for (int x=0; x<jsonArray.length(); x++){
                        JSONObject object = jsonArray.getJSONObject(x);
                        String productName = object.getString("title");
                        String productPrice = object.getString("price");
                        String id = object.getString("id");
                        String description = object.getString("description");
                        String discountPercentage = object.getString("discountPercentage");
                        String rating = object.getString("rating");
                        String stock = object.getString("stock");
                        String brand = object.getString("brand");
                        String category = object.getString("category");
                        String thumbnail = object.getString("thumbnail");
                        String images = object.getString("images");

                        hashMap = new HashMap<>();
                        hashMap.put("productName_key",productName);
                        hashMap.put("productPrice_key",productPrice);
                        hashMap.put("id_key",id);
                        hashMap.put("description_key",description);
                        hashMap.put("discountPercentage_key",discountPercentage);
                        hashMap.put("rating_key",rating);
                        hashMap.put("stock_key",stock);
                        hashMap.put("brand_key",brand);
                        hashMap.put("category_key",category);
                        hashMap.put("thumbnail_key",thumbnail);
                        hashMap.put("images_key",images);
                        myArrayList.add(hashMap);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                MyAdapter myAdapter = new MyAdapter();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(myAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private class MyViewHolder extends RecyclerView.ViewHolder{

            RvLayoutBinding binding;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = RvLayoutBinding.bind(itemView);
            }
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            HashMap<String,String> hashMap = myArrayList.get(position);

            String productName = hashMap.get("productName_key");
            String productPrice = hashMap.get("productPrice_key");
            String id = hashMap.get("id_key");
            String description = hashMap.get("description_key");
            String discountPercentage = hashMap.get("discountPercentage_key");
            String rating = hashMap.get("rating_key");
            String stock = hashMap.get("stock_key");
            String brand = hashMap.get("brand_key");
            String category = hashMap.get("category_key");
            String thumbnail = hashMap.get("thumbnail_key");
            String images = hashMap.get("images_key");


            Picasso.get()
                    .load(thumbnail)
                    .into(holder.binding.productImage);

            holder.binding.productName.setText(productName);
            holder.binding.productPrice.setText("Price: "+productPrice);
            holder.binding.productDitails.setText(description);


            holder.binding.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, Product_Activity.class);
                    intent.putExtra("imageIntent",thumbnail);
                    intent.putExtra("titleIntent",productName);
                    intent.putExtra("descIntent",description);
                    intent.putExtra("ratingIntent",rating);
                    intent.putExtra("priceIntent",productPrice);
                    intent.putExtra("brandIntent",brand);

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return myArrayList.size();
        }
    }
}