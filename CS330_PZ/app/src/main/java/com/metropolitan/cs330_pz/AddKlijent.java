package com.metropolitan.cs330_pz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class AddKlijent extends AppCompatActivity {

    DatabaseHelper db;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    TimePicker timePicker;
    DatePicker datePicker;
    int hour, minute;
    int yr, month, day;

    EditText ime,prezime,date,vreme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_klijent);

         db = new DatabaseHelper(this);
         ime = (EditText) findViewById(R.id.ime);
         prezime = (EditText) findViewById(R.id.prezime);
         date = (EditText) findViewById(R.id.datum);
         vreme = (EditText) findViewById(R.id.vreme);
         Button add = (Button) findViewById(R.id.addklijent);
        ImageButton clock = (ImageButton) findViewById(R.id.clock);
        ImageButton calendar = (ImageButton) findViewById(R.id.calendar);

        Calendar today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        hour = today.get(Calendar.HOUR_OF_DAY);
        minute = today.get(Calendar.MINUTE);

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isDodato =  db.addKlijent(ime.getText().toString(),prezime.getText().toString(),date.getText().toString(),vreme.getText().toString());
                if(isDodato==true)
                    Toast.makeText(AddKlijent.this, "Klijent uspešno zakazan", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddKlijent.this, "Ups!!! Došlo je do greške.", Toast.LENGTH_LONG).show();
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
