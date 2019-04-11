package com.allysoftsolutions.water_clients1.client_model.client_product_model;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allysoftsolutions.water_clients1.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class client_product_listadepter extends ArrayAdapter<client_product_model> {
    List<client_product_model> productList;
    int totalbottle;

    //activity context
    Context context;
     client_product_model product;
    //the layout resource file for the list items
    int resource;

    public client_product_listadepter(Context context, int resource, List<client_product_model> productList) {
        super(context, resource, productList);
        this.context = context;
        this.resource = resource;
        this.productList = productList;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);


        //getting the view
        View view = layoutInflater.inflate(resource, null, false);




        TextView tvnm = view.findViewById(R.id.tvproname);
        final TextView tvprice = view.findViewById(R.id.pprice);
        TextView tvdetails = view.findViewById(R.id.prodetails);

        ImageView imgproduct = view.findViewById(R.id.imgproduct);
        //getting the hero of the specified position

        product= productList.get(position);

        tvnm.setText(product.getProduct_name());
        tvprice.setText(product.getProduct_price());
        tvdetails.setText(product.getProduct_detail());
        Picasso.with(getContext())
                .load(product.getImage())
                .into(imgproduct);



        return view;
    }


    }

