package com.allysoftsolutions.water_clients1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allysoftsolutions.water_clients1.admin_agent_side.product_model.MyProductListAdapter;
import com.allysoftsolutions.water_clients1.client_model.client_product_model.client_product_listadepter;
import com.allysoftsolutions.water_clients1.client_model.client_product_model.client_product_model;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class client_view_product extends AppCompatActivity implements View.OnClickListener {
    View coordinatorLayout;


    private static final String TAG = "Mohit";
    List<client_product_model> productList;
    //for  fab buttton
    private TextView tvhide, tvagenthide, tvproducthide, tvlogouthide;
    Button btnorder;
    TextView tvbottletotal;

    //the listview
    ListView listclient;
    String bottlet;
    String url = "https://waterbottle12-e6aa9.firebaseio.com/";
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private DatabaseReference mDatabaseReference;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_client_view_product);
        coordinatorLayout = findViewById(R.id.layout1);
        tvhide = findViewById(R.id.tvhide);
        tvagenthide = findViewById(R.id.tvagenthide);
        tvproducthide = findViewById(R.id.tvhideproduct);
        tvlogouthide = findViewById(R.id.tvhidelogout);
        productList = new ArrayList<>();

        tvbottletotal = findViewById(R.id.tv_total);

        listclient = findViewById(R.id.clientdatalist);

        Firebase.setAndroidContext(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Product_data");

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        client_product_model upload1 = postSnapshot.getValue(client_product_model.class);
                        productList.add(upload1);
                        client_product_model model = new client_product_model();
                        //arrayList1.add(postSnapshot.getKey());
                    }
                    String[] uploads = new String[productList.size()];

                    for (int i = 0; i < uploads.length; i++) {
                        uploads[i] = productList.get(i).getProduct_name();
                        Log.e(TAG, "onDataChange: " + uploads[i].toString());

                    }
                    //displaying it to list
                  //  final MyProductListAdapter adapter = new MyProductListAdapter();

                    client_product_listadepter adepter1 = new client_product_listadepter(getApplicationContext(), R.layout.client_view_product_listview, productList);
                    listclient.setAdapter(adepter1);

                    //select product for delete
                } catch (Exception e) {
                    Log.e(TAG, "onDataChange: " + e);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }


        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:

                Log.d("Raj", "Fab 1");
                showCustomDialog();
                animateFAB();
                break;

            case R.id.fab2:
                //Toast.makeText(this, "log1", Toast.LENGTH_SHORT).show();
                Log.d("a", "Fab 2");
                // showAgentDialog();
                Intent i1 = new Intent(this, customer_order.class);
                startActivity(i1);
                break;

            case R.id.fab3:
                animateFAB();
               /* Log.d("a", "Fab 3");
                Intent i = new Intent(this, client_view_product.class);
                startActivity(i);
                finish();*/
                break;

            case R.id.fab4:
                //Logout From cURRENT User
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                if (fAuth != null) {
                    Intent iq = new Intent(getApplicationContext(), client_login.class);
                    startActivity(iq);
                    finish();
                } else {
                    Toast.makeText(this, "Cant Logout", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }



    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(client_view_product.this);
        View view = getLayoutInflater().inflate(R.layout.dialong_customer_profile, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.Theme_Design_BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.show();


        //get all edittext in dialog_customer_add
      /*  final EditText edtnm = view.findViewById(R.id.edtnm);
        final EditText edtmob = view.findViewById(R.id.edtmob);
        final EditText edtadd = view.findViewById(R.id.tvaddress);
        final EditText edtadd2 = view.findViewById(R.id.edtaddresstwo);
        final EditText edtbarcode = view.findViewById(R.id.edtbarcode);
        final ImageView imgview=view.findViewById(R.id.imgview);*/


        //client profile
        view.findViewById(R.id.btnallclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set all client display activity
             /*   Intent i = new Intent(getApplicationContext(), Admin_view_all_client.class);
                startActivity(i);*/
            }
        });

        view.findViewById(R.id.btnscannbarcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add barcode scann code herer
                //Toast.makeText(Admin_view_all_client.this, "barcode scanner", Toast.LENGTH_SHORT).show();
            }
        });


        view.findViewById(R.id.btnaddclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void animateFAB() {
        if (isFabOpen)
        {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);

            tvhide.startAnimation(fab_close);
            tvagenthide.startAnimation(fab_close);
            tvproducthide.startAnimation(fab_close);
            tvlogouthide.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);

            isFabOpen = false;
            Log.d("Raj", "close");
        }
        else
            {

                fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);

            tvhide.startAnimation(fab_open);
            tvagenthide.startAnimation(fab_open);
            tvproducthide.startAnimation(fab_open);
            tvlogouthide.startAnimation(fab_open);

            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
        }
    }

    private void animateFAB2() {

        fab.startAnimation(rotate_forward);
        fab1.startAnimation(fab_open);
        fab2.startAnimation(fab_open);
        fab3.startAnimation(fab_open);
        fab4.startAnimation(fab_open);

        tvhide.startAnimation(fab_open);
        tvagenthide.startAnimation(fab_open);
        tvproducthide.startAnimation(fab_open);
        tvlogouthide.startAnimation(fab_open);

        fab1.setClickable(true);
        fab2.setClickable(true);
        fab3.setClickable(true);
        fab4.setClickable(true);
        isFabOpen = true;
    }

    public void addbackbutton(View view) {
        super.onBackPressed();
    }
}
