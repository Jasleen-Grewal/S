package com.example.n01204206.milestone;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private View view;
    DataStructure mData;


    private TextView moisture;
    private TextView temp;
    private TextView humidity;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getDatabase();
        findAllViews();
        retrieveData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Initialization of Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void findAllViews() {
        moisture = findViewById(R.id.readmoisture);
        temp = findViewById(R.id.readtemp);
        humidity = findViewById(R.id.readhumidity);
        time = findViewById(R.id.readtime);
    }
    private void getDatabase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("realdata");
    }

    private void retrieveData() {
        // TODO: Get the data on a single node.
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                moisture.setText(ds.getMoisture());
                temp.setText(ds.getTemp());
                humidity.setText(ds.getHumidity());
               // time.setText(ds.getTime());
                time.setText(convertTimestamp(ds.getTime()));

            }

            private String convertTimestamp(String timestamp){

                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
                return df.format(mDate);
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                moisture.setText(ds.getMoisture());
                temp.setText(ds.getTemp());
                humidity.setText(ds.getHumidity());
                //time.setText(ds.getTime());
                time.setText(convertTimestamp(ds.getTime()));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MapleLeaf", "Data Loading Cancelled/Failed.", databaseError.toException());
            }


        });

        // TODO: Get the whole data array on a reference.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DataStructure> arraylist = new ArrayList<DataStructure>();

                // TODO: Now data is reteieved, needs to process data.
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    // iterate all the items in the dataSnapshot
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        DataStructure dataStructure = new DataStructure();
                        dataStructure.setMoisture(a.getValue(DataStructure.class).getMoisture());
                        dataStructure.setTemp(a.getValue(DataStructure.class).getTemp());
                        dataStructure.setHumidity(a.getValue(DataStructure.class).getHumidity());
                       // dataStructure.setTime(a.getValue(DataStructure.class).getTime());
                        dataStructure.setTime(a.getValue(DataStructure.class).getTime());


                        arraylist.add(dataStructure);  // now all the data is in arraylist.
                        Log.d("MapleLeaf", "dataStructure " + dataStructure.getTime());
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting data failed, log a message
                Log.d("MapleLeaf", "Data Loading Cancelled/Failed.", databaseError.toException());
            }
        });
    }


        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        Fragment fragment = null;
        Bundle bundle = new Bundle();

        if (id == R.id.home) {
            fragment = new HomeFragment();

        } else if (id == R.id.settings) {
            //goes to setting page
/*
            settings.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent i = new Intent(Main2Activity.this,Settings.class);
                    startActivity(i);
                }
            });
*/


        intent = new Intent(getApplicationContext(),Settings.class);

        } else if (id == R.id.help) {
            //goes to help page
        } else if (id == R.id.logout) {
            //exit
/*
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
*/

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }

        if(intent != null) {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


   public void onClick(View view) {
        Intent intent = new Intent(this, CameraOption.class);
        startActivity(intent);
    }
}

