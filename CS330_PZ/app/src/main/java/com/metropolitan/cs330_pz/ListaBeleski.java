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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaBeleski extends AppCompatActivity {

    DatabaseHelper db;
    List<Beleska> beleske = new ArrayList<>();
    BeleskaListAdapter adapter;
    ListView listView;
    ImageButton add;
    Button izbrisiSve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_beleski);

        db = new DatabaseHelper(this);
        beleske=db.getAllBeleske();
        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, beleske);
        adapter = new BeleskaListAdapter(this, R.layout.beleska_list_adapter, beleske);

        //Log.e("li",beleske.toString());
        listView = (ListView) findViewById(R.id.listabeleski);
        listView.setAdapter(adapter);

        add = (ImageButton) findViewById(R.id.addBeleska) ;
        izbrisiSve = (Button) findViewById(R.id.izbrisiSve);

//        final ListView listView = (ListView) findViewById(R.id.listabeleski);
//        listView.setAdapter(adapter);

        final TextView poruka = (TextView)findViewById(R.id.poruka3) ;
        //  poruka.setVisibility(View.GONE);

        if(beleske.size()!= 0) {
            poruka.setVisibility(View.GONE);


        } else{
            poruka.setVisibility(View.VISIBLE);
            showDialog4();
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListaBeleski.this, AddBeleska.class);
                startActivity(myIntent);
            }
        });


        izbrisiSve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog8();


            }
        });


     }




    private void showDialog4(){
        new AlertDialog.Builder(this)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Trenutno nemate nijednu belešku.")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_info))
                .setPositiveButton(
                        "U redu",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                           dialog.dismiss();
                            }
                        }).show();

    }



    private void showDialog8(){
        new AlertDialog.Builder(this)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Da li ste sigurni da želite da obrišete sve beleške?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                int a = db.deleteAllBeleske();
                                if(a!=0) {
                                    Toast.makeText(getApplicationContext(),"Sve beleške su obrisane",Toast.LENGTH_SHORT).show();
//                    Intent myIntent = new Intent(getBaseContext(), KlijentInfo.class);
//                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(myIntent);

                                    Intent intt = getIntent();
                                    intt.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // sakriva animaciju
                                    overridePendingTransition(0,0); // sakriva animaciju
                                    finish();
                                    startActivity(intt);
                                    overridePendingTransition(0,0);
                                    //   startActivity(getIntent()); //
                                }else{
                                    Toast.makeText(getApplicationContext(),"Došlo je do greške",Toast.LENGTH_SHORT).show();
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
