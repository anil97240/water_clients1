package com.allysoftsolutions.water_clients1.client_model;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.allysoftsolutions.water_clients1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class clientlistAdepter extends ArrayAdapter<Client> {

    List<Client> clientList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    public clientlistAdepter(Context context, int resource, List<Client> clientList) {
        super(context, resource, clientList);
        this.context = context;
        this.resource = resource;
        this.clientList = clientList;
    }


    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        TextView tvnm = view.findViewById(R.id.tvcnm);
        TextView tvmob = view.findViewById(R.id.tvmobno);
        TextView tvbar = view.findViewById(R.id.tvbarcode);
        TextView tvadd1 = view.findViewById(R.id.tvaddress);

        ImageView img=view.findViewById(R.id.imgclient);
        //getting the hero of the specified position
        Client client = clientList.get(position);
        //adding values to the list item
        tvnm.setText(client.getCustomer_name());
        tvmob.setText(client.getMobile_number());
        tvbar.setText(client.getCustomer_qrcode());
        tvadd1.setText(client.getAddress());

        Picasso.with(getContext())
                .load(client.getImage())
                .into(img);



        return view;
    }


}
