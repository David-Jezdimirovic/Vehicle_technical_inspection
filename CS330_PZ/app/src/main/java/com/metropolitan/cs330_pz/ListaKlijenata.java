package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaKlijenata extends AppCompatActivity {

    DatabaseHelper db;
    List<Klijent> klijenti = new ArrayList<>();
    KlijentListAdapter adapter;
    int yr, month, day;
    EditText datum3;

    private static final int notification_one = 101;
    private NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_klijenata);

        Button prikaziSve = (Button)findViewById(R.id.prikaziSve);
        ImageButton date = (ImageButton)findViewById(R.id.calendar3);
        ImageButton search = (ImageButton)findViewById(R.id.id_search);
         datum3 = (EditText)findViewById(R.id.id_datum) ;

        final TextView poruka = (TextView)findViewById(R.id.poruka) ;

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String dat = intent.getStringExtra("datum");

        poruka.setVisibility(View.GONE);

        if(!dat.equals("") ){
            klijenti = db.getAllKlijentiSaDatumom(dat);
            if(klijenti.isEmpty()){
                poruka.setVisibility(View.VISIBLE);
                adapter = new KlijentListAdapter(this, R.layout.klijent_list_adapter, klijenti);
                datum3.setText(dat);
            }else {
                poruka.setVisibility(View.GONE);
                adapter = new KlijentListAdapter(this, R.layout.klijent_list_adapter, klijenti);
                datum3.setText(dat);
            }
        }else {
            // getAll();
            klijenti = db.getAllKlijenti2();
            if(klijenti.isEmpty()){
                poruka.setVisibility(View.VISIBLE);
                adapter = new KlijentListAdapter(this, R.layout.klijent_list_adapter, klijenti);
            }else {
                poruka.setVisibility(View.GONE);
                //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, klijenti);
                adapter = new KlijentListAdapter(this, R.layout.klijent_list_adapter, klijenti);
            }
        }



      final  ListView listView = (ListView) findViewById(R.id.listaklijenata);
        listView.setAdapter(adapter);




        Calendar today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        prikaziSve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                klijenti.clear();
                adapter.clear();
                // adapter.notifyDataSetChanged();
                klijenti = db.getAllKlijenti2();

                if(klijenti.isEmpty()){
                    //  showDialog2();
                    poruka.setVisibility(View.VISIBLE);
                }else {
                    // adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, klijenti);
                    adapter = new KlijentListAdapter(ListaKlijenata.this, R.layout.klijent_list_adapter, klijenti);
                    listView.setAdapter(adapter);
                    poruka.setVisibility(View.GONE);
                }
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klijenti.clear();

                adapter.clear();
               // adapter.notifyDataSetChanged();
                klijenti = db.getAllKlijentiSaDatumom(datum3.getText().toString());

                if(klijenti.isEmpty()){
                //  showDialog2();
                    poruka.setVisibility(View.VISIBLE);
                }else {
                    // adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, klijenti);
                    adapter = new KlijentListAdapter(ListaKlijenata.this, R.layout.klijent_list_adapter, klijenti);
                    listView.setAdapter(adapter);
                    poruka.setVisibility(View.GONE);
                }
            }
        });



    }

    // kreira animaciju vraćanjem na back dugme na prethodni intent
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
//            case TIME_DIALOG_ID:
//                return new TimePickerDialog(
//                        this, mTimeSetListener, hour, minute, true);
            case 0:
                return new DatePickerDialog(
                        this, mDateSetListener, yr, month, day);

        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener()
            {
                public void onDateSet(
                        DatePicker view, int year, int monthOfYear, int dayOfMonth)
                {
                    yr = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                    String datum = day + "." + (month + 1) + "." + year + ".";
                    datum3.setText(datum);


//                    Toast.makeText(getBaseContext(),
//                            "Izabrali ste : " + (month + 1) +
//                                    "/" + day + "/" + year,
//                            Toast.LENGTH_SHORT).show();
                }
            };


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
               mnu1.setIcon(R.drawable.add_klijent_512x512);
            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
