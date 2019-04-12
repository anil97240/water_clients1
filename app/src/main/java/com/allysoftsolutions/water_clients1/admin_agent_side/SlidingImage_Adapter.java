package com.allysoftsolutions.water_clients1.admin_agent_side;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allysoftsolutions.water_clients1.R;
import com.allysoftsolutions.water_clients1.customer_order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SlidingImage_Adapter extends PagerAdapter {
    public static ImageView imageView;
    public static Object data;

        int total;



    // private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    List<sliding_image> totalList;

    public SlidingImage_Adapter(Context context,List<sliding_image> totalList) {
        this.context = context;
        this.totalList=totalList;
        inflater = LayoutInflater.from(context);
    }




    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return totalList.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {
        final View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        imageView = (ImageView) imageLayout.findViewById(R.id.image);
       Button add=imageLayout.findViewById(R.id.btnadd);
        Button min=imageLayout.findViewById(R.id.btnmin);
        final TextView edtbottle=imageLayout.findViewById(R.id.edtbottle);

        final sliding_image sliding_image= totalList.get(position);
        Picasso.with(context)
                .load(sliding_image.getImage())
                .into(imageView);

        if(sliding_image.getQry()==null)
        {
            edtbottle.setText("0");

        }
        else {
            edtbottle.setText(sliding_image.getQry());
        }
        if(sliding_image.getQry()=="")
        {
            edtbottle.setText("0");

        }

        data=edtbottle.getText().toString();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a= Integer.parseInt(edtbottle.getText().toString());
                if(edtbottle.getText()=="") {
                        a=0;
                    }
                    else
                    {
                    int b = 1;
                    a = a + b;
                    edtbottle.setText("" + a);
                    sliding_image.setQry(edtbottle.getText().toString());
                    data = edtbottle.getText().toString();
                    customer_order.adddata();
                }
            }
        });
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a= Integer.parseInt(edtbottle.getText().toString());
                if(a==0)
                {
                    Toast.makeText(v.getContext(), "No Bottle In Cart", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    int b = 1;
                    a = a-b;
                    customer_order.min();
                }
                edtbottle.setText(""+a);
                data=edtbottle.getText().toString();
                sliding_image.setQry(edtbottle.getText().toString());

            }
        });

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {

        return null;
    }
}

