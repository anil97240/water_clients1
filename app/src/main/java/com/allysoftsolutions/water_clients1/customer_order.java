package com.allysoftsolutions.water_clients1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.allysoftsolutions.water_clients1.admin_agent_side.SlidingImage_Adapter;
import com.allysoftsolutions.water_clients1.admin_agent_side.sliding_image;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class customer_order extends AppCompatActivity {

    String userName;
    //firebase
    List<sliding_image> slid_img;
    private DatabaseReference mDatabaseReference;

    static String as;
    static TextView totalPrice;
    ArrayList arrayList1 = new ArrayList<String>();
    String url = "https://waterbottle12-e6aa9.firebaseio.com/";
    SlidingImage_Adapter slidadepter;
    //bottle details
    private static TextView tvdetails, tvprice;
    EditText edtbottle;
    TextView tv23;
    TextView tvtotalbottle;
    static TextView tvqty;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    View coordinatorLayout;
    int random;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //will hide the title
        getSupportActionBar().hide();
        // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //enable full screen

        setContentView(R.layout.activity_customer_order);
        coordinatorLayout = findViewById(R.id.layout1);
        random = ThreadLocalRandom.current().nextInt(100, 100000000);

        tvdetails = findViewById(R.id.tvbottledetails);
        tvprice = findViewById(R.id.tvprice);
        tvqty = findViewById(R.id.tvqty);
        totalPrice = findViewById(R.id.tv_totalcost);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userName = extras.getString("cname");
            // and get whatever type user account id is
        }

        FirebaseApp.initializeApp(this);
        // final SlidingImage_Adapter adapter = new SlidingImage_Adapter();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Product_data");
        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    slid_img = new ArrayList<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        sliding_image list = postSnapshot.getValue(sliding_image.class);
                        slid_img.add(list);
                        arrayList1.add(postSnapshot.getKey());
                    }
                    String[] uploads = new String[slid_img.size()];
                    for (int i = 0; i < uploads.length; i++) {
                        uploads[i] = slid_img.get(i).getImage();
                    }

                    for (int i = 0; i < uploads.length; i++) {
                        String s = (uploads[i]);
                        mPager = (ViewPager) findViewById(R.id.pager);
                        slidadepter=new SlidingImage_Adapter(customer_order.this, slid_img);
                        mPager.setAdapter(slidadepter);
                        tvdetails.setText(slid_img.get(0).getProduct_name());
                        tvprice.setText("" + slid_img.get(0).getProduct_Price());

                        // Toast.makeText(customer_order.this, ""+tvdetails.getText().toString(), Toast.LENGTH_SHORT).show();

                        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
                        indicator.setViewPager(mPager);

                        final float density = getResources().getDisplayMetrics().density;


                        //Set circle indicator radius
                        indicator.setRadius(8 * density);

                        NUM_PAGES = uploads.length;
                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                mPager.setCurrentItem(currentPage++, true);
                            }
                        };

                        // Pager listener over indicator
                        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                            @Override
                            public void onPageSelected(int position) {
                                currentPage = position;
                                tvdetails.setText(slid_img.get(position).getProduct_name());
                                tvprice.setText("" + slid_img.get(position).getProduct_Price());

                                // Toast.makeText(customer_order.this, ""+tvdetails.getText().toString(), Toast.LENGTH_SHORT).show();

                            }
                            @Override
                            public void onPageScrolled(final int pos, float arg1, int arg2) {
                            }
                            @Override
                            public void onPageScrollStateChanged(int pos) {

                            }
                        });

                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void btnok(View view) {
        for (int i = 0; i < slid_img.size(); i++) {
            try {
                if (slid_img.get(i).getQry().equals("0")) {

                    //Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    //add product data
                    Firebase.setAndroidContext(this);
                    Firebase ref;
                    ref = new Firebase(url);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //date of order
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    Map<String, String> users = new HashMap<>();
                    users.put("Botttle_qty", slid_img.get(i).getQry());
                    users.put("Customer_name", userName);
                    users.put("Product_id", arrayList1.get(i).toString());
                    users.put("Bottle_price", slid_img.get(i).getProduct_Price());
                    users.put("Product_name", slid_img.get(i).getProduct_name());
                    users.put("Order_id", userName +""+ random);
                    ref.child("Orer_details").push().setValue(users);
                 /* Snackbar s1 = Snackbar.make(coordinatorLayout, "Order Successfully.", Snackbar.LENGTH_SHORT);
                    s1.show();*/
                }
            } catch (Exception e) {
                //Toast.makeText(this,""+e, Toast.LENGTH_SHORT).show();
            }
        }
        Firebase.setAndroidContext(this);
        Firebase ref;
        ref = new Firebase(url);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        int s = Integer.parseInt(tvqty.getText().toString());
        if (s == 0) {
          Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "No Bottles In Cart", Snackbar.LENGTH_SHORT);
            snackbar1.show();
        } else {
            //date of order
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Map<String, String> users = new HashMap<>();
            users.put("Botttle_qty", tvqty.getText().toString());
            users.put("Customer_id", user.getPhoneNumber());
            users.put("Customer_name", userName);
            users.put("total_cost", totalPrice.getText().toString());
            users.put("Order_date", date);
            users.put("Order_id", userName + "" + random);
            users.put("Status","1");
            ref.child("Customer_order").push().setValue(users);

          /*tvqty.setText("0");
            totalPrice.setText("0");
            sliding_image slid=new sliding_image();
            slid.setQry("0");
            for(int i=0;i<slid_img.size();i++) {
                slid_img.get(i).setQry("");
            }*/

            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Toast.makeText(this, "Order Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void gobacktodashboard(View view) {
        super.onBackPressed();
    }

    public static void adddata() {
        as = SlidingImage_Adapter.data.toString();
        int tdata;
        tdata = Integer.parseInt(as);
        if (as.equals("0")) {
        } else {
            int a = Integer.parseInt(as);
            if (a == 0) {
                int b = Integer.parseInt(tvqty.getText().toString());
                b = b + 0;
                tvqty.setText("" + b);

            } else {
                int price = Integer.parseInt(tvprice.getText().toString());
                int tprice = Integer.parseInt(totalPrice.getText().toString());
                int b = Integer.parseInt(tvqty.getText().toString());
                b = b + 1;
                tprice = tprice + price;
                tvqty.setText("" + b);
                totalPrice.setText("" + tprice);

            }
        }
    }

    public static void min() {
        as = SlidingImage_Adapter.data.toString();
        int data = Integer.parseInt(tvqty.getText().toString());

        if (data == 0) {

        } else {
            int price = Integer.parseInt(tvprice.getText().toString());
            int tprice = Integer.parseInt(totalPrice.getText().toString());
            int b = Integer.parseInt(tvqty.getText().toString());
            b = b - 1;
            tvqty.setText("" + b);
            tprice = tprice - price;
            totalPrice.setText("" + tprice);
        }
    }
}

