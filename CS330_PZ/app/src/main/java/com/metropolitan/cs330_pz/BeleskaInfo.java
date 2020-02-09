package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BeleskaInfo extends AppCompatActivity {


    DatabaseHelper db;


    TextView datum;
    EditText naslov, beleska;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;
    ImageButton save,delete;

    String beleska_id, naslo, belesk, datu , statu, datumst;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beleska_info);

         db = new DatabaseHelper(this);

        naslov = (EditText) findViewById(R.id.naslov3);
        beleska = (EditText) findViewById(R.id.beleska3);
        datum = (TextView) findViewById(R.id.datum7);

        radioGroup = (RadioGroup)findViewById(R.id.radioCheck4);
        radioButton = (RadioButton)findViewById(R.id.radioUradjeno2);
        radioButton2 = (RadioButton)findViewById(R.id.radioNijeUradjeno2);


        ImageButton save = (ImageButton) findViewById(R.id.save4);
        ImageButton del = (ImageButton) findViewById(R.id.delete7);

        Intent intent = getIntent();
        beleska_id = intent.getStringExtra("beleska_id");
        naslo = intent.getStringExtra("naslov");
        belesk = intent.getStringExtra("beleska");
        datu = intent.getStringExtra("datum3");
        statu = intent.getStringExtra("status2");
        //Log.e("status",statu);

        naslov.setText(naslo);
        beleska.setText(belesk);
        datum.setText(datu);


        if(statu.equals(radioButton.getText().toString())) {
            radioButton.setChecked(true);
        }else {
            radioButton2.setChecked(true);

        }
       // datum3.setText(datum4);




        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        datumst = day + "." + (month + 1) + "." + year + ".";


        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedButtonId);
        statu = radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                radioButton = (RadioButton) findViewById(checkedId);
                statu = radioButton.getText().toString();
                Log.e("radio", statu);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updateBeleska(
                        Integer.parseInt(beleska_id),
                        naslov.getText().toString(),
                        beleska.getText().toString(),
                        datumst,
                        statu);
                if(isUpdate) {
                    Toast.makeText(getBaseContext(), "Beleška uspešno ažurirana", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);

                    startActivity(myIntent);
                    // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                } else {
                    Toast.makeText(BeleskaInfo.this, "Došlo je do greške", Toast.LENGTH_LONG).show();
                }
            }
        });



        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog7(beleska_id);


            }
        });


    }


    private void showDialog7(final String beleska_id){
        new AlertDialog.Builder(this)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Da li ste sigurni da želite da izbrišete belešku?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

//                               // int id = k.getId();
//
                                // Integer deleteK = db.deleteKlijentAndReports(kl.getId());
                                Integer deleteI = db.deleteBeleska(Integer.parseInt(beleska_id));
                                if(deleteI > 0){
                                    Toast.makeText(getBaseContext(),"Beleška uspešno obrisana", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);

                                    startActivity(myIntent);
                                    // startActivityForResult(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"),0);
                                }else{
                                    Toast.makeText(getBaseContext(), "Beleška nije obrisana", Toast.LENGTH_LONG).show();
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
