package com.allysoftsolutions.water_clients1.admin_agent_side.product_model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allysoftsolutions.water_clients1.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class   MyProductListAdapter extends ArrayAdapter<Product> {

    //the list values in the List of type hero
    List<Product> productList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;


    //constructor initializing the values
    public MyProductListAdapter(Context context, int resource, List<Product> productList) {
        super(context, resource, productList);
        this.context = context;
        this.resource = resource;
        this.productList = productList;
    }

    //this will return the ListView tem as a View


    @Override
    public View getView(final int position, @Nullable View lconvertView, @NonNull ViewGroup parent) {
        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //getting the view
        View view = layoutInflater.inflate(resource, null, false);
        //getting the view elements of the list from the view
        TextView tvpname = view.findViewById(R.id.tvproname);
        TextView tvpprice = view.findViewById(R.id.pprice);
        TextView tvprodetails = view.findViewById(R.id.prodetails);
        ImageView img1=view.findViewById(R.id.imgproduct);


        //getting the hero of the specified position

        Product product = productList.get(position);

        //adding values to the list item
        tvpname.setText(product.getProduct_name());
        tvpprice.setText(product.getProduct_Price());
        tvprodetails.setText(product.getProduct_detail());

            Picasso.with(getContext())
                    .load(product.getImage())
                    .into(img1);

        //finally returning the view
        return view;
    }
}
