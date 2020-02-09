package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KlijentInfo extends AppCompatActivity {

    DatabaseHelper db;
    List<Izvestaj> izvestaji = new ArrayList<>();
    ArrayAdapter adapter;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    TimePicker timePicker;
    DatePicker datePicker;
    int hour, minute;
    int yr, month, day;

    EditText ime,prezime,date,vreme;
    String id2,im,pr,dt,vr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.klijent_info);


        db = new DatabaseHelper(this);
        ime = (EditText) findViewById(R.id.ime2);
        prezime = (EditText) findViewById(R.id.prezime2);
        date = (EditText) findViewById(R.id.datum2);
        vreme = (EditText) findViewById(R.id.vreme2);
        ImageButton save = (ImageButton) findViewById(R.id.save);
        ImageButton clock = (ImageButton) findViewById(R.id.clock2);
        ImageButton calendar = (ImageButton) findViewById(R.id.calendar2);
        ImageButton delete = (ImageButton) findViewById(R.id.delete2);
        ImageButton addreport = (ImageButton) findViewById(R.id.addreport);// postavljeno na action listener kroz metodu

        final ListView listView = (ListView) findViewById(R.id.listaizvestaja);


        Calendar today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        hour = today.get(Calendar.HOUR_OF_DAY);
        minute = today.get(Calendar.MINUTE);

        Intent intent = getIntent();
         final  Klijent kli = new Klijent();
           kli.setId(Integer.parseInt(intent.getStringExtra("id")));

        //  String a=(getExtraData(sifra));
        id2 = intent.getStringExtra("id");
        im = intent.getStringExtra("ime");
        pr = intent.getStringExtra("prezime");
        dt = intent.getStringExtra("datum");
        vr = intent.getStringExtra("vreme");


//        Log.e("id2",klijent_id);
        ime.setText(intent.getStringExtra("ime"));
        prezime.setText(intent.getStringExtra("prezime"));
        date.setText(intent.getStringExtra("datum"));
        vreme.setText(intent.getStringExtra("vreme"));

        final TextView poruka = (TextView)findViewById(R.id.poruka2) ;
      //  poruka.setVisibility(View.GONE);

        // getAll();
        izvestaji = db.getAllIzvestajiByIdKlijenta(Integer.parseInt(id2));
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, izvestaji);
        // adapter = new IzvestajiListAdapter(this, R.layout.izvestaji_list_adapter, izvestaji);
        listView.setAdapter(adapter);

        if(izvestaji.size()!= 0) {
            poruka.setVisibility(View.GONE);

        } else{
            poruka.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog2( kli);



            }
        });

                calendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDialog(DATE_DIALOG_ID);
                    }

                });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //        mapFragment.getView().setVisibility(View.VISIBLE);

                showDialog(TIME_DIALOG_ID);
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdate = db.updateKlijent(Integer.parseInt(id2),
                        ime.getText().toString(),
                        prezime.getText().toString(),
                        date.getText().toString(),
                        vreme.getText().toString());
                if(isUpdate)
                    Toast.makeText(getBaseContext(),"Klijent uspešno ažuriran", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(KlijentInfo.this,"Došlo je do greške",Toast.LENGTH_LONG).show();


            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent,
                                     View view, int index, long id)
            {
                //  Kontakt kon = (Kontakt) listView.getItemAtPosition(index);
                //  Object o = parent.getAdapter().getItem(index);
                Izvestaj izv=(Izvestaj) parent.getAdapter().getItem(index);
                //  textView.setText(kon.toString());
                //  textView.setText(listView.getItemAtPosition(index).toString());


                Intent myIntent = new Intent(view.getContext(), IzvestajInfo.class);
                myIntent.putExtra("izvestaj_id",String.valueOf(izv.getIzvestaj_id()));
                myIntent.putExtra("marka",izv.getMarka());
                myIntent.putExtra("model",String.valueOf(izv.getModel()));
                myIntent.putExtra("motor",String.valueOf(izv.getMotor()));
                myIntent.putExtra("kocnice",String.valueOf(izv.getKocnice()));
                myIntent.putExtra("svetla",String.valueOf(izv.getSvetla()));
                myIntent.putExtra("status",String.valueOf(izv.getStatus()));
                myIntent.putExtra("datum2",String.valueOf(izv.getDatum2()));


                myIntent.putExtra("id",id2);
                myIntent.putExtra("ime",im);
                myIntent.putExtra("prezime",pr);
                myIntent.putExtra("datum",dt);
                myIntent.putExtra("vreme",vr);

                startActivityForResult(myIntent, 0);

                Toast.makeText(getBaseContext(),
                        "Izabrali ste stavku : " + izv,
                        Toast.LENGTH_SHORT).show();

            }

        });






    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(
                        this, mTimeSetListener, hour, minute, true);
            case DATE_DIALOG_ID:
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
                    date.setText(datum);


//                    Toast.makeText(getBaseContext(),
//                            "Izabrali ste : " + (month + 1) +
//                                    "/" + day + "/" + year,
//                            Toast.LENGTH_SHORT).show();
                }
            };


    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(
                        TimePicker view, int hourOfDay, int minuteOfHour)
                {
                    hour = hourOfDay;
                    minute = minuteOfHour;

                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
                    Date date2 = new Date(0,0,0, hour, minute);
                    String strTime = timeFormat.format(date2);

                    vreme.setText(strTime);

//                    Toast.makeText(getBaseContext(),
//                            "Izabrali ste " + strDate,
//                            Toast.LENGTH_SHORT).show();
                }
            };

    public void addReport(View v){

        Intent myIntent = new Intent(getBaseContext(), AddReport.class);
        myIntent.putExtra("id",id2);
        myIntent.putExtra("ime",im);
        myIntent.putExtra("prezime",pr);
        myIntent.putExtra("datum",dt);
        myIntent.putExtra("vreme",vr);

        startActivity(myIntent);

    }

                             //   final String klijent_id
        private void showDialog2(final Klijent kl){
        new AlertDialog.Builder(this)
                .setTitle("Obaveštenje")
                .setMessage(
                       "Brisanjem klijenta brišete i sve njegove izveštaje.Da li želite da nastavite?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                Integer deleteK = db.deleteKlijentAndReports(Integer.parseInt(id2));
                                if(deleteK > 0){
                                    Toast.makeText(getBaseContext(),"Klijent i njegovi izvestaji su obrisani", Toast.LENGTH_LONG).show();
                                   // startActivityForResult(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"),0);
                                    Intent myInt = new Intent(getBaseContext(), ListaKlijenata.class);
                                    myInt.putExtra("datum","");
                                    startActivityForResult(myInt, 0);
                                }else{
                                    Toast.makeText(getBaseContext(), "Klijent nije obrisan", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .setNegativeButton("ne",  new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        dialog.dismiss();
                    }
                }).show();
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
