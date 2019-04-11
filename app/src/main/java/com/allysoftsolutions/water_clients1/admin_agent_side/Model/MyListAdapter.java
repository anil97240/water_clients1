package com.allysoftsolutions.water_clients1.admin_agent_side.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


public class MyListAdapter extends ArrayAdapter<agent> {

    List<agent> agentList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    public MyListAdapter(Context context, int resource, List<agent> agentList) {
        super(context, resource, agentList);
        this.context = context;
        this.resource = resource;
        this.agentList = agentList;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

/*
        TextView tvnm = view.findViewById(R.id.tvagentnm);
        TextView tvtotal = view.findViewById(R.id.tvbottletotle);
        TextView tvcoll = view.findViewById(R.id.tvcollection);



        agent agent = agentList.get(position);


        tvnm.setText(agent.aname);
        tvtotal.setText(agent.bottle);
        tvcoll.setText(agent.collected);

*/
        return view;
    }

}