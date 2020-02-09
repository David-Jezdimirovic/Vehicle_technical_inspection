package com.metropolitan.cs330_pz;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;


    private static final int notification_one = 101;
    private NotificationHelper notificationHelper;
    int yr, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        notificationHelper = new NotificationHelper(this);
        try {
            // copydatabase();
            db.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button zakonik = (Button) findViewById(R.id.zakon);
        Button vesti = (Button) findViewById(R.id.vesti);
        Button prikazi = (Button) findViewById(R.id.show_notification);
        Button podesi = (Button) findViewById(R.id.config_notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            podesi.setVisibility(View.VISIBLE);
        }else{
            podesi.setVisibility(View.GONE);
        }

        WebView wv = (WebView) findViewById(R.id.webview1);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);

        wv.setWebViewClient(new Callback());
        wv.loadUrl("https://tpholliday.com/?gclid=EAIaIQobChMIwNudrpP94QIVBZ3VCh2ptQUnEAAYASAAEgKqavD_BwE");

        Calendar today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);



        zakonik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.paragraf.rs/propisi/pravilnik-o-tehnickom-pregledu-vozila.html"));
                startActivity(myIntent);
            }
        });

        vesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(getApplicationContext(),Vesti.class);
                startActivity(myIntent2);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            postNotification(notification_one,"Obaveštenje");
        }else{
            addNotification();
        }

        prikazi.setOnClickListener(new View.OnClickListener() {
         //   @RequiresApi(api = Build.VERSION_CODES.O)
           // @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    postNotification(notification_one,"Obaveštenje");
                }else{
                    addNotification();
                }


            }
        });

        podesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
            }
        });
    }



    // ako je verzija sistema manja od android Oreo
//     Creates and displays a notification
    private void addNotification() {
        String dt = day + "." + (month + 1) + "." + yr + ".";
        // klijenti = db.getAllKlijentiSaDatumom(dt);
        int total = db.prebrojZakazaneKlijenteSaDatumom(dt);
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_round_icon2_24x24)
                .setContentTitle("Obaveštenje")
                .setContentText("Danas imate zakazano "+ total + " klijenata")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, ListaKlijenata.class);
        notificationIntent.putExtra("datum",dt);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }



// ako je verzija sistema veca ili jednaka od Android Oreo
   // @RequiresApi(api = Build.VERSION_CODES.O)
    public void postNotification(int id, String title) {
        Notification.Builder notificationBuilder = null;
        String dt = day + "." + (month + 1) + "." + yr + ".";

        int total = db.prebrojZakazaneKlijenteSaDatumom(dt);

        switch (id) {
            case notification_one:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    notificationBuilder = notificationHelper.getNotification1(title,
                            "Danas imate zakazano "+ total + " klijenata");

                    Intent notificationIntent = new Intent(this, ListaKlijenata.class);
                    notificationIntent.putExtra("datum",dt);
                    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    notificationBuilder.setContentIntent(contentIntent);

                }
                break;

//            case notification_two:
//                notificationBuilder = notificationHelper.getNotification2(title,
//                        getString(R.string.channel_two_body));
//                break;
        }

        if (notificationBuilder != null) {
            notificationHelper.notify(id, notificationBuilder);
        }
    }

    // ako je verzija sistema veca ili jednaka od Android Oreo otvara podešavanja notifikacija
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
       // overridePendingTransition(R.anim.fade_in, R.anim.zoom_in);
    }




    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
