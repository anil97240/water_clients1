package com.allysoftsolutions.water_clients1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allysoftsolutions.water_clients1.client_model.Client;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class client_dashboard extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Mohit";
    // inside Activit
    EditText edtnm, edtmob, edtadd, edtadd2, edtbarcode;
    ImageView imgview;
    int authint = 0;
    ImageView imgcus;
    private static final int PICK_IMAGE_REQUEST = 234;
    String filedownloadpath = "";
    private Uri filePath;
    Button expandableButton3, expandableButton2, expandableButton1;
    ExpandableRelativeLayout expandableLayout1, expandableLayout2;
    ArrayList arrayList = new ArrayList<String>();
    ArrayList arrayList1 = new ArrayList<String>();
    ArrayList arrayList2 = new ArrayList<String>();
    ArrayList all = new ArrayList<String>();
    String cid;
    String cimage = "";
    String sdata;
    int a1, b1 = 0;
    int month1, m1total, month2, m2total, month3, m3total;
    int collected, c, ctotal;
    String sss1;
    TextView tvmonth1, tvmonth2, tvmonth3;
    private StorageReference storageReference;
    //the listview
    private DatabaseReference mDatabaseReference;
    String customername;
    List<cliet_data> client_list;
    ListView listView2;
    //database reference to get uploads data
    View coordinatorLayout;
    Bitmap bitmap;
    List<cliet_data> bottle_deliveredList;
    List<cliet_data> bottle_deliveredList2;
    Client client;
    String ss1, datem;
    String qrcodedata;
    int lastindexd;
    String mono;
    FirebaseUser user;
    String provider;
    private FirebaseAuth mAuth;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private TextView tvhide, tvagenthide, tvproducthide, tvlogouthide;
    cliet_data upload;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_client_dashboard);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        coordinatorLayout = findViewById(R.id.layout1);

        expandableButton3 = findViewById(R.id.totalbtn);
        expandableButton2 = findViewById(R.id.expandableButton2);
        expandableButton1 = findViewById(R.id.expandableButton1);
        imgcus=findViewById(R.id.imgviewcus);
        tvmonth1 = findViewById(R.id.tvmon1);
        tvmonth2 = findViewById(R.id.tvmon2);
        tvmonth3 = findViewById(R.id.tvmon3);

        tvhide = findViewById(R.id.tvhide);
        tvagenthide = findViewById(R.id.tvagenthide);
        tvproducthide = findViewById(R.id.tvhideproduct);
        tvlogouthide = findViewById(R.id.tvhidelogout);

        listView2 = (ListView) findViewById(R.id.listpadding3);

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
        mAuth = FirebaseAuth.getInstance();
        try {
            if (mAuth != null) {
                user = mAuth.getCurrentUser();
                provider = user.getProviders().get(0);
                Log.e(TAG, "Mono: " + user.getPhoneNumber());
                mono = user.getPhoneNumber();
            } else {
                Log.e(TAG, "onCreate: Auth is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        //Get an Currenrt User Details
        FirebaseDatabase.getInstance().getReference("Customer_data/" + user.getPhoneNumber())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //client data and

                        try {
                            client = dataSnapshot.getValue(Client.class);
                            cid = "+91" + client.getMobile_number();
                            cimage = client.getImage();
                            qrcodedata = client.getCustomer_qrcode();
                            sdata = qrcodedata;

                            customername = client.getCustomer_name();

                            Picasso.with(getApplicationContext())
                                    .load(client.getImage())
                                    .into(imgcus);



                            mDatabaseReference = FirebaseDatabase.getInstance().getReference("collected_amount");
                            //
                            final Date d = new Date();
                            CharSequence s = DateFormat.format("dd-MM-yyyy ", d.getTime());
                            final CharSequence s1 = DateFormat.format("MM", d.getTime());
                            bottle_deliveredList = new ArrayList<>();
                            bottle_deliveredList2 = new ArrayList<>();
                            //getting the database reference
                            //retrieving upload data from firebase database
                            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        upload = postSnapshot.getValue(cliet_data.class);
                                        bottle_deliveredList.add(upload);
                                    }
                                    if (qrcodedata == null) {
                                    } else {
                                        for (int i = 0; i < bottle_deliveredList.size(); i++) {
                                            // arrayList2.add();
                                            if (bottle_deliveredList.get(i).getQR_code().contains(qrcodedata)) {
                                                //   bottle_deliveredList2.add(bottle_deliveredList.get(i));
                                                bottle_deliveredList2.add(bottle_deliveredList.get(i));
                                                //  Toast.makeText(client_dashboard.this, "qrcode" + sdata, Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        lastindexd = bottle_deliveredList2.size() - 1;
                                    }

                                    int sum = 0, b = 0;
                                    int Pendingsum = 0, p;
                                    String[] uploads = new String[bottle_deliveredList2.size()];
                                    for (int i = 0; i < uploads.length; i++) {

                                        String acollect = null;
                                        String pending = null;
                                        String ddate = null;
                                        String totl = null;
                                        String qr;

                                        //   if (bottle_deliveredList.get(i).getQR_code().contains(qrcodedata)) {
                                        acollect = uploads[i] = bottle_deliveredList2.get(i).getAmount_collected();
                                        pending = uploads[i] = bottle_deliveredList2.get(i).getPadding_amount();
                                        ddate = uploads[i] = bottle_deliveredList2.get(i).getCollected_Date();
                                        totl = uploads[i] = bottle_deliveredList2.get(i).getAmount_total();
                                        //qr = uploads[i] = bottle_deliveredList.get(i).getQR_code();

                                        /*
                                         * for the month data display
                                         * get month date
                                         * display data in listview
                                         */
                                        // if (qr.equals(cid)) {

                                        //fist month date
                                        Date c1 = new Date();
                                        SimpleDateFormat f = new SimpleDateFormat("MMMM-yyyy");
                                        SimpleDateFormat f1 = new SimpleDateFormat("MM-yyyy");
                                        datem = f.format(c1);
                                        String datemd = f1.format(c1);
                                        //last month date
                                        SimpleDateFormat format = new SimpleDateFormat("MMMM-yyyy");
                                        SimpleDateFormat format1 = new SimpleDateFormat("MM-yyyy");
                                        Calendar cal1 = Calendar.getInstance();
                                        cal1.add(Calendar.MONTH, -1);
                                        ss1 = format.format(cal1.getTime());
                                        String lastmonth = format1.format(cal1.getTime());
                                        //third month date
                                        SimpleDateFormat f2 = new SimpleDateFormat("MMMM-yyyy");
                                        SimpleDateFormat sf2 = new SimpleDateFormat("MM-yyyy");
                                        Calendar cal2 = Calendar.getInstance();
                                        cal2.add(Calendar.MONTH, -2);
                                        sss1 = f2.format(cal2.getTime());
                                        String ss2 = f2.format(cal2.getTime());
                                        //date convert
                                        SimpleDateFormat ff = new SimpleDateFormat("dd-MM-yyyy");
                                        SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
                                        Date date = null;
                                        Date dd1 = null;
                                        try {
                                            date = ff.parse(ddate);
                                            dd1 = day.parse(ddate);
                                        } catch (ParseException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        //for month
                                        ff = new SimpleDateFormat("MM-yyyy");
                                        String a = ff.format(date);

                                        ///for day
                                        day = new SimpleDateFormat("dd");
                                        int daydate = Integer.parseInt(day.format(dd1));
                                        //last 7 day data get
                                        for (int i1 = -7; i1 < 1; i1++) {
                                            Calendar calendar1 = GregorianCalendar.getInstance();
                                            calendar1.setTime(new Date());
                                            calendar1.add(Calendar.DAY_OF_YEAR, i1);
                                            Date lastdate = calendar1.getTime();
                                            SimpleDateFormat ff2 = new SimpleDateFormat("dd");
                                            int last1 = Integer.parseInt(ff2.format(lastdate));
                                            Date cdate = new Date();
                                            SimpleDateFormat f11 = new SimpleDateFormat("dd-MM-yyyy");
                                            String dday = f11.format(cdate);
                                            if (daydate == last1) {
                                                a1 = Integer.parseInt(acollect);
                                                b1 = b1 + a1;
                                                arrayList.add("  " + ddate + "                     " + acollect);
                                            }
                                        }
                                        //collected amount

                                        // all total
                                        all.add(ddate + "                            Rs." + totl);
                                        //COUNT TOTAL OF THE THIS MONTH
                                        if (a.equals(datemd)) {
                                            month1 = Integer.parseInt(String.valueOf(pending));
                                            m1total = m1total + month1;
                                        }
                                        //last month
                                        if (a.equals(lastmonth)) {
                                            month2 = Integer.parseInt(String.valueOf(pending));
                                            m2total = m2total + month2;

                                        }
                                        //secod last month
                                        if (a.equals(ss2)) {
                                            month3 = Integer.parseInt(String.valueOf(pending));
                                            m3total = m3total + month3;

                                        }
                                        int monthtotalrs = m1total + m2total + m3total;
                                        int abc = bottle_deliveredList2.size() - 1;


                                        try {
                                            //For An total Number fo Totalamount
                                            b = Integer.parseInt(String.valueOf(totl));
                                            sum = b + sum;
                                            Log.e(TAG, "Total is: " + sum);


                                            //For An total Number fo Pendingamount
                                            p = Integer.parseInt(String.valueOf(pending));
                                            Pendingsum = p + Pendingsum;

                                            Log.e(TAG, "pendin is: " + Pendingsum);

                                            collected = Integer.parseInt(String.valueOf(acollect));
                                            ctotal = c + collected;

                                            Calendar cal = GregorianCalendar.getInstance();
                                            cal.setTime(new Date());
                                            cal.add(Calendar.DAY_OF_YEAR, -7);
                                            Date date1 = cal.getTime();

                                            SimpleDateFormat formatter5 = new SimpleDateFormat("dd/MM");
                                            String formats1 = formatter5.format(date1);

                                            Date cdate1 = new Date();

                                            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM");
                                            String formats = formatter1.format(cdate1);
                                            try {
                                                expandableButton1.setText(formats1 + " - " + formats + "                Rs." + b1);
                                            } catch (Exception e) {
                                                expandableButton1.setText(formats1 + " - " + formats + "                Rs." + 0);
                                            }

                                        } catch (Exception e) {
                                            Log.e(TAG, "data not found: " + e.getMessage());


                                        }
                                    }
                                    int abc = bottle_deliveredList2.size() - 1;
                                    //  Toast.makeText(client_dashboard.this, "index"+abc, Toast.LENGTH_SHORT).show();

                                    try {
                                        expandableButton2.setText("Pending Amount         Rs." + bottle_deliveredList2.get(lastindexd).getPadding_amount());

                                    } catch (Exception e) {
                                        expandableButton2.setText("Pending Amount         Rs." + 0);

                                    }

                                    try {
                                        int pdata = Integer.parseInt(bottle_deliveredList2.get(lastindexd).getPadding_amount());
                                        int a = b1 + pdata;

                                        expandableButton3.setText("Total Amount           Rs." + a);
                                        //total data display in listview
                                    } catch (Exception e) {
                                        expandableButton3.setText("Total Amount           Rs.0");

                                    }
                                    arrayList1.add("   " + datem + "                 " + m1total);
                                    arrayList1.add("   " + ss1 + "                  " + m2total);
                                    arrayList1.add("   " + sss1 + "               " + m3total);

                                    if (arrayList1.isEmpty()) {
                                        tvmonth1.setText("       " + datem + "                         No Data");
                                        tvmonth2.setText("       " + ss1 + "                     No Data");
                                        tvmonth3.setText("       " + sss1 + "                     No Data");
                                    } else {
                                        try {
                                            tvmonth1.setText("       " + datem + "                        " + bottle_deliveredList2.get(abc).getPadding_amount());
                                            tvmonth2.setText("       " + ss1 + "                     " + m2total);
                                            tvmonth3.setText("       " + sss1 + "                     " + m3total);

                                        } catch (Exception e) {

                                        }
                                    }

                                    //displaying it to list
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                                    if (arrayList == null) {

                                    } else {
                                        listView2.setAdapter(adapter);
                                    }
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList1);
                                    // listView.setAdapter(adapter1);
                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e(TAG, "onCancelled: " + databaseError.getMessage());
                                }
                            });


                            // Toast.makeText(client_dashboard.this, "qrcode:"+qrcodedata, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            authint = 2;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        if (authint == 2) {

        } else {

        }
        imgcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

    }
    public void expandableButton1(View view) {

        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle();// toggle expand and collapse

    }

    public void expandableButton2(View view) {
       /* expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse*/
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.fab:
                animateFAB();
                break;

            case R.id.fab1:
                if (mono.equals(cid)) {
                    showCustomDialog();
                } else {
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Please Contact  Admin.", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
                break;

            case R.id.fab2:
                if (mono.equals(cid)) {
                    Intent i1 = new Intent(this, customer_order.class);
                    i1.putExtra("cname", customername);
                    startActivity(i1);
                } else {
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Please Contact Admin.", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
                break;

            case R.id.fab3:

                Intent i = new Intent(this, client_view_product.class);
                startActivity(i);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(client_dashboard.this);
        View view = getLayoutInflater().inflate(R.layout.dialong_customer_profile, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.Theme_Design_BottomSheetDialog); // Style here
        dialog.setContentView(view);
        //get all edittext in dialog_customer_add
        edtnm = view.findViewById(R.id.edtnm);
        edtmob = view.findViewById(R.id.edtmob);
        edtadd = view.findViewById(R.id.edtaddr);
        edtadd2 = view.findViewById(R.id.edtaddr1);
        edtbarcode = view.findViewById(R.id.edtbarcode);
        imgview = view.findViewById(R.id.imgview);
        edtnm.setText(client.getCustomer_name());
        edtmob.setText(client.getMobile_number());
        edtadd.setText(client.getLocal_address());
        edtbarcode.setText(client.getCustomer_qrcode());
        edtadd2.setText(client.getAddress());
        edtnm.requestFocus();

        String s = client.getImage();

        if (s.equals(""))
        {

        } else
            {
            Picasso.with(getApplicationContext())
                    .load(client.getImage())
                    .into(imgview);

                Picasso.with(getApplicationContext())
                        .load(client.getImage())
                        .into(imgcus);

        }
        //client profile
        view.findViewById(R.id.btnallclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.findViewById(R.id.btnscannbarcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add barcode scann code herer
                //Toast.makeText(Admin_view_all_client.this, "barcode scanner", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.fbupload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });
        if (bitmap == null) {

        } else
            {

            imgview.setImageBitmap(bitmap);

        }
        view.findViewById(R.id.btnaddclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void noimagedata() {
//without image data add

        String nm = edtnm.getText().toString();
        String mob = edtmob.getText().toString();
        String add = edtadd.getText().toString();
        String add2 = edtadd2.getText().toString();
        String qrcode = edtbarcode.getText().toString();


        Validation_text valid = new Validation_text();

        if (!valid.isValidName(nm)) {
            edtnm.setError("Invalid Name");
        } else if (!valid.isValidName(add)) {
            edtadd.setError("Invalid Address");
        } else if (!valid.isValidName(add2)) {
            edtadd2.setError("Invalid Address");
        } else {

            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Customer_data");
            Map<String, String> users = new HashMap<>();
            users.put("Customer_name", edtnm.getText().toString());
            users.put("Mobile_number", edtmob.getText().toString());
            users.put("Local_address", edtadd.getText().toString());
            users.put("Customer_qrcode", edtbarcode.getText().toString());
            users.put("Address", edtadd2.getText().toString());
            users.put("image", "");
            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Profile Update Successfully.", Snackbar.LENGTH_SHORT);
            snackbar1.show();
            mDatabaseReference.child(mono).setValue(users);

        }
    }
    private void adddata() {
//with image data add
        String nm = edtnm.getText().toString();
        String mob = edtmob.getText().toString();
        String add = edtadd.getText().toString();
        String add2 = edtadd2.getText().toString();
        String qrcode = edtbarcode.getText().toString();

        Validation_text valid = new Validation_text();

        if (!valid.isValidName(nm)) {
            edtnm.setError("Invalid Name");
        } else if (!valid.isValidName(add)) {
            edtadd.setError("Invalid Address");
        } else if (!valid.isValidName(add2)) {
            edtadd2.setError("Invalid Address");
        } else {

            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Customer_data");
            Map<String, String> users = new HashMap<>();
            users.put("Customer_name", edtnm.getText().toString());
            users.put("Mobile_number", edtmob.getText().toString());
            users.put("Local_address", edtadd.getText().toString());
            users.put("Customer_qrcode", edtbarcode.getText().toString());
            users.put("Address", edtadd2.getText().toString());
            users.put("image", filedownloadpath);
            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Profile Update Successfully.", Snackbar.LENGTH_SHORT);
            snackbar1.show();

            mDatabaseReference.child(mono).setValue(users);
            if (filedownloadpath.equals("")) {

            } else {
                Picasso.with(getApplicationContext())
                        .load(filedownloadpath)
                        .into(imgview);
            }
        }
    }

    private void animateFAB() {
        if (isFabOpen) {

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

        } else {

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

    private void showFileChooser() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("key_name", true); // Storing boolean - true/false
        editor.commit(); // commit changes
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        final String STORAGE_PATH_UPLOADS = "uploads/";
        final String DATABASE_PATH_UPLOADS = "Customer_data";
        DatabaseReference mDatabase;
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);

        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            //getting the storage reference

            StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast

                            //creating the upload object to store uploaded image details
                            Client cn = new Client();
                            //Client upload = new Client(cn.getCustomer_name(),cn.getMobile_number(),cn.getAddress(),taskSnapshot.getDownloadUrl().toString());
                            filedownloadpath = client.getImage();
                            filedownloadpath = taskSnapshot.getDownloadUrl().toString();

                            if (filedownloadpath == "") {
                                filedownloadpath = "";
                                adddata();
                            } else {
                                adddata();
                            }
                            //adding an upload to firebase database
                            /*String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(upload);*/
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

        } else {
            filedownloadpath = client.getImage();
            adddata();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
