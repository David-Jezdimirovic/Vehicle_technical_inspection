package com.metropolitan.cs330_pz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Scroller;
import android.widget.Toast;

import java.util.Calendar;

public class AddReport extends AppCompatActivity {

    DatabaseHelper db;
    AutoCompleteTextView marka, model;
    EditText motor,kocnice,svetla;
    String status, datum;
    int year, month, day;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;
    private Button addReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_report);

        Intent intent = getIntent();
     final  int id = Integer.parseInt(intent.getStringExtra("id"));
      final String  im = intent.getStringExtra("ime");
        final String pr = intent.getStringExtra("prezime");
        final String  dt = intent.getStringExtra("datum");
        final String vr = intent.getStringExtra("vreme");
        Log.e("id3",String.valueOf(id));
        db = new DatabaseHelper(this);

        marka = (AutoCompleteTextView) findViewById(R.id.marka);
        model = (AutoCompleteTextView) findViewById(R.id.model);
        motor = (EditText) findViewById(R.id.motor);
        kocnice = (EditText) findViewById(R.id.kocnice);
        svetla = (EditText) findViewById(R.id.svetla);

        radioGroup = (RadioGroup) findViewById(R.id.radioCheck);
        addReport = (Button) findViewById(R.id.addreport2);

        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedButtonId);
        status = radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                radioButton = (RadioButton) findViewById(checkedId);
                status = radioButton.getText().toString();
                Log.e("radio", status);
            }
        });





//        Toast.makeText(MyAndroidAppActivity.this,
//                radioSexButton.getText(), Toast.LENGTH_SHORT).show();

        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        datum = day + "." + (month + 1) + "." + year + ".";

 String[] marke = {"Peugeot","Citroen", "Renault", "Mercedes", "BMW", "VolksWagen" ,"Škoda", ""};
 String[] modeli = {"206","207", "308", "307", "serija 3 318d", "serija 3 320d" ,"Octavia", "Superb", "Golf 7" ,"Golf 6", "C class 220d", "E class 220d" ,"Clio", "Megan", "Talisman" ,"C5", "C4"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddReport.this,
                android.R.layout.simple_dropdown_item_1line, marke);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddReport.this,
                android.R.layout.simple_dropdown_item_1line, modeli);
        marka.setAdapter(adapter);
        model.setAdapter(adapter2);



        motor.setScroller(new Scroller(getApplicationContext()));
        motor.setMaxLines(3);
        motor.setVerticalScrollBarEnabled(true);
        motor.setMovementMethod(new ScrollingMovementMethod());

        kocnice.setScroller(new Scroller(getApplicationContext()));
        kocnice.setMaxLines(3);
        kocnice.setVerticalScrollBarEnabled(true);
        kocnice.setMovementMethod(new ScrollingMovementMethod());

        svetla.setScroller(new Scroller(getApplicationContext()));
        svetla.setMaxLines(3);
        svetla.setVerticalScrollBarEnabled(true);
        svetla.setMovementMethod(new ScrollingMovementMethod());

        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean isKreiran =  db.addReport(id, marka.getText().toString(),model.getText().toString(),motor.getText().toString(),kocnice.getText().toString(),svetla.getText().toString(),status,datum);
                if(isKreiran) {
                    Toast.makeText(AddReport.this, "Izveštaj uspešno kreiran", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(),KlijentInfo.class);
                    myIntent.putExtra("id",String.valueOf(id));
                    myIntent.putExtra("ime",im);
                    myIntent.putExtra("prezime",pr);
                    myIntent.putExtra("datum",dt);
                    myIntent.putExtra("vreme",vr);
                    startActivity(myIntent);
                   // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                } else{
                    Toast.makeText(AddReport.this, "Ups!!! Došlo je do greške.", Toast.LENGTH_LONG).show();
                }

            }
        });
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
