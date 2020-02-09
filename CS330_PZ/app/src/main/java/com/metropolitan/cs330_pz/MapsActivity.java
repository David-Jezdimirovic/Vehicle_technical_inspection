package com.metropolitan.cs330_pz;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private  EditText duzina,sirina;
    private Button prikazi;
    private Double d,s;
    SupportMapFragment mapFragment;

    private static final int REQUEST_FINE_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        sirina = (EditText) findViewById(R.id.sirina);
        duzina = (EditText) findViewById(R.id.duzina);
        prikazi = (Button) findViewById(R.id.prikazi);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
      //  mapFragment.getView().setVisibility(View.GONE);
                                            //INVISIBLE

        s=44.740172;
        d=19.679820;

        //provera da li je dozvola već odobrena
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("dozvola", "Dozvola je već odobrena.");
            } else {
                requestPermission();
            }



        }


        prikazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
//                mapFragment.getView().setVisibility(View.VISIBLE);

                if(duzina.getText().toString().equals("") || sirina.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Podrazumevane koordinate ako ne unesete nikakve vrednosti: sirina: "+s+"; duzina: "+d, Toast.LENGTH_SHORT).show();
//                    s=90.0;
//                    d=180.0;

                }else{

                    s = Double.parseDouble(sirina.getText().toString());
                    d = Double.parseDouble(duzina.getText().toString());
                }

            }

        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.e("sirina=",String.valueOf(s));
        Log.e("duzina=",String.valueOf(d));

        // Add a marker in Sydney and move the camera
        LatLng koordinate = new LatLng(s, d);
        mMap.addMarker(new MarkerOptions().position(koordinate).title("Koordinate: Širina "+s+" Dužina "+d));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(koordinate)); //postavljanje markera na zadatu lokaciju
        mMap.getUiSettings().setZoomControlsEnabled(true);//zoom kontrola
        mMap.getUiSettings().setCompassEnabled(true);//postavljanje kompasa
        mMap.getUiSettings().setCompassEnabled(true);//kontrola kompas
        mMap.getUiSettings().setAllGesturesEnabled(true);//jedan prst zumira,dva odzumiraju
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //postavljanje tipa mape
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);//promenom u false, gasi se dugme

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }

        //Geokodiranje - prikazivanje adrese iz koordinata
        Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.addMarker(new MarkerOptions().position(position));
                Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());
                try {

                    List<Address> adr;
                    adr = gk.getFromLocation(position.latitude, position.longitude, 1);

                    //čitanje adrese iz postavljenog markera

                    if (adr != null && adr.size() > 0) {
                        Address addr = adr.get(0);
                        String addressText = addr.getAddressLine(0);
                        Toast.makeText(getBaseContext(), addressText, Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //Adresa postavljenog markera
        try {

            List<Address> adr;
            adr = gk.getFromLocation(koordinate.latitude, koordinate.longitude, 1);
            String ad = "";

            //čitanje adrese iz postavljenog markera

            if (adr != null && adr.size() > 0) {
                Address addr = adr.get(0);
                String addressText = addr.getAddressLine(0);
                Toast.makeText(getBaseContext(), addressText, Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    //provera dozvole
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    //zahteva proveru dozvole
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

    }

    //vraća rezultate provere dozvole
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                // if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (grantResults.length > 0) {
                    boolean access = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (access) {

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);//promenom u false, gasi se dugme
                        }
                        Toast.makeText(MapsActivity.this,
                                "Dozvola je prihvaćena", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(MapsActivity.this,
                            "Dozvola nije prihvaćena", Toast.LENGTH_LONG).show();



                }
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu) {
        // menu.setQwertyMode(true);

        menu.add(0,0,0,"Početna strana");
        MenuItem mnu1 = menu.add(0, 1, 1, "Dodaj klijenta");
        {

            //   mnu1.setAlphabeticShortcut('a');
            //   mnu1.setIcon(R.mipmap.ic_launcher);
            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
//            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        MenuItem mnu2 = menu.add(0, 2, 2, "Lista klijenata");
        {
            // mnu2.setAlphabeticShortcut('b');
            //   mnu2.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 3, 3, "Dodaj belešku");
        {
            // mnu3.setAlphabeticShortcut('c');
            //   mnu3.setIcon(R.mipmap.ic_launcher);
        }
        menu.add(0,4,4,"Lista beleški");
        menu.add(0,5,5,"Kontakti");
        menu.add(0,6,6,"Mapa");
    }

    private boolean MenuChoice(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "Početna strana",
                        Toast.LENGTH_LONG).show();
                Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);
                startActivityForResult(myIntent0, 0);

                return true;
            case 1:
                Toast.makeText(this, "Dodavanje novog klijenta",
                        Toast.LENGTH_LONG).show();
                Intent myIntent1 = new Intent(getBaseContext(), AddKlijent.class);
                startActivityForResult(myIntent1, 0);

                return true;
            case 2:
                Toast.makeText(this, "Lista klijenata",
                        Toast.LENGTH_LONG).show();
                Intent myIntent2 = new Intent(getBaseContext(), ListaKlijenata.class);
                myIntent2.putExtra("datum","");
                startActivityForResult(myIntent2, 0);
                return true;
            case 3:
                Toast.makeText(this, "Dodaj belešku",
                        Toast.LENGTH_LONG).show();
                Intent myIntent3 = new Intent(getBaseContext(), AddBeleska.class);
                startActivityForResult(myIntent3, 0);
                return true;
            case 4:
                Toast.makeText(this, "Lista beleški",
                        Toast.LENGTH_LONG).show();
                Intent myIntent4 = new Intent(getBaseContext(), ListaBeleski.class);
                startActivityForResult(myIntent4, 0);
                return true;
            case 5:
                Toast.makeText(this, "Kontakti",
                        Toast.LENGTH_LONG).show();
                Intent myIntent5 = new Intent(getBaseContext(), ListaKontakata.class);
                startActivityForResult(myIntent5, 0);
                return true;
            case 6:
                Toast.makeText(this, "Mapa",
                        Toast.LENGTH_LONG).show();
                Intent myIntent6 = new Intent(getBaseContext(), MapsActivity.class);
                startActivityForResult(myIntent6, 0);
                return true;
        }
        return false;
    }



}

