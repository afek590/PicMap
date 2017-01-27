package com.example.afek.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final int GPS_PERMISSION = 200;
    private final int CAMERA_REQUEST = 1888;
    private final long MIN_TIME_FOR_UPDATE = 500;
    private final long MIN_DIS_FOR_UPDATE = 0;
    private ImageButton editBtn, searchBtn, cameraBtn;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Location currentLocation;
    private DbHelper dbHelper;
    SQLiteDatabase readDb, writeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBtn = (ImageButton) findViewById(R.id.edit_button);
        searchBtn = (ImageButton) findViewById(R.id.search_button);
        cameraBtn = (ImageButton) findViewById(R.id.camera_button);
        editBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item);
        gridView.setAdapter(gridAdapter);

        permissionCheck();
        dbHelper = new DbHelper(this);
        readDb = dbHelper.getReadableDatabase();
        writeDb = dbHelper.getWritableDatabase();

    }

    private void permissionCheck()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION);
        else
            setLocationMethod();
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == editBtn.getId())
        {

        }
        else if (v.getId() == searchBtn.getId())
        {

        }
        else if (v.getId() == cameraBtn.getId())
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void setLocationMethod()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DIS_FOR_UPDATE, locationListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {

        }
    }
}
