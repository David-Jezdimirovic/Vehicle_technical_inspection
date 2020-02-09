package com.metropolitan.cs330_pz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Scroller;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class IzvestajInfo extends AppCompatActivity {

String direct="/cs330_pz/";
    private static final int PERMISSION_REQUEST_CODE = 1;
    AutoCompleteTextView marka,model;
    EditText motor,kocnice,svetla,datum3;
    ImageButton save2, delete4, picture , camera;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;
    DatabaseHelper db;
    String id, ime, prezime, datum , vreme, izvestaj_id, marka2, model2, motor2, kocnice2, svetla2, status2, datum4;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.izvestaj_info);

        String[] marke = {"Peugeot","Citroen", "Renault", "Mercedes", "BMW", "VolksWagen" ,"Škoda", ""};
        String[] modeli = {"206","207", "308", "307", "serija 3 318d", "serija 3 320d" ,"Octavia", "Superb", "Golf 7" ,"Golf 6", "C class 220d", "E class 220d" ,"Clio", "Megan", "Talisman" ,"C5", "C4"};

        db = new DatabaseHelper(this);
        marka = (AutoCompleteTextView) findViewById(R.id.marka2);
        model = (AutoCompleteTextView) findViewById(R.id.model2);

        motor = (EditText) findViewById(R.id.motor2);
        kocnice = (EditText) findViewById(R.id.kocnice2);
        svetla = (EditText) findViewById(R.id.svetla2);
        datum3  = (EditText) findViewById(R.id.datum3);


        save2 = (ImageButton)findViewById(R.id.save2);
        delete4 = (ImageButton)findViewById(R.id.delete4);
        picture = (ImageButton)findViewById(R.id.picture);
        camera = (ImageButton)findViewById(R.id.camera);

        radioGroup = (RadioGroup)findViewById(R.id.radioCheck2);
        radioButton = (RadioButton)findViewById(R.id.radioProsao2);
        radioButton2 = (RadioButton)findViewById(R.id.radioNijeProsao2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(IzvestajInfo.this,
                android.R.layout.simple_dropdown_item_1line, marke);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(IzvestajInfo.this,
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

        Intent intent = getIntent();

        izvestaj_id = intent.getStringExtra("izvestaj_id");
        marka2 = intent.getStringExtra("marka");
        model2 = intent.getStringExtra("model");
        motor2 = intent.getStringExtra("motor");
        kocnice2 = intent.getStringExtra("kocnice");
        svetla2 = intent.getStringExtra("svetla");
        status2 = intent.getStringExtra("status");
        datum4 = intent.getStringExtra("datum2");

//        Log.e("izv_id",izvestaj_id);
//        Log.e("marka",marka2);

        id = intent.getStringExtra("id");
        ime = intent.getStringExtra("ime");
        prezime = intent.getStringExtra("prezime");
        datum = intent.getStringExtra("datum");
        vreme = intent.getStringExtra("vreme");


       marka.setText(marka2);
       model.setText(model2);
       motor.setText(motor2);
       kocnice.setText(kocnice2);
        svetla.setText(svetla2);

        if(status2.equals(radioButton.getText().toString())) {
            radioButton.setChecked(true);
        }else {
            radioButton2.setChecked(true);
        }
       datum3.setText(datum4);




        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        datum = day + "." + (month + 1) + "." + year + ".";


        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedButtonId);
        status2 = radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                radioButton = (RadioButton) findViewById(checkedId);
                status2 = radioButton.getText().toString();
                Log.e("radio", status2);
            }
        });



        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updateReport(
                        Integer.parseInt(izvestaj_id),
                        marka.getText().toString(),
                        model.getText().toString(),
                        motor.getText().toString(),
                        kocnice.getText().toString(),
                        svetla.getText().toString(),
                        status2,
                        datum);
                if(isUpdate) {
                    Toast.makeText(getBaseContext(), "Izveštaj uspešno ažuriran", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(), KlijentInfo.class);
                    myIntent.putExtra("id", String.valueOf(id));
                    myIntent.putExtra("ime", ime);
                    myIntent.putExtra("prezime", prezime);
                    myIntent.putExtra("datum", datum);
                    myIntent.putExtra("vreme", vreme);
                    startActivity(myIntent);
                    // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                } else {
                    Toast.makeText(IzvestajInfo.this, "Došlo je do greške", Toast.LENGTH_LONG).show();
                }
            }
        });



        delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog3(izvestaj_id);


            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
             @Override
                 public void onClick(View v) {
                 if(checkPermission()) {
                     Intent intent2 = new Intent(getBaseContext(), ViewPagerGallery.class);
                     intent2.putExtra("izvestaj_id", izvestaj_id);
                     intent2.putExtra("ime", ime);
                     startActivity(intent2);
                 }else{
                     checkPermission();
                 }
                 }
                });



        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("dozvola", "Dozvola je već odobrena.");
            } else {
                requestPermission();
            }
        }

        Intent i = getIntent();
        String a = i.getStringExtra("ime");
        String b = i.getStringExtra("izvestaj_id");
        direct += a + b;

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()) {

                    Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                   
                    startActivityForResult(camera, 1);
               
                }else{
                    requestPermission();
                }
            }
        });
            }


    private void showDialog3(final String izvestaj_id){
        new AlertDialog.Builder(this)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Da li ste sigurni da želite da izbrišete izveštaj?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                Integer deleteI = db.deleteIzvestaj(Integer.parseInt(izvestaj_id));
                                if(deleteI > 0){
                                    Toast.makeText(getBaseContext(),"Izveštaj uspešno obrisan", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(getBaseContext(), KlijentInfo.class);
                                    myIntent.putExtra("id", String.valueOf(id));
                                    myIntent.putExtra("ime", ime);
                                    myIntent.putExtra("prezime", prezime);
                                    myIntent.putExtra("datum", datum);
                                    myIntent.putExtra("vreme", vreme);
                                    startActivity(myIntent);
                                   // startActivityForResult(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"),0);
                                }else{
                                    Toast.makeText(getBaseContext(), "Izveštaj nije obrisan", Toast.LENGTH_LONG).show();
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


    //provera dozvole
    private boolean checkPermission() {
        int cameraresult = ContextCompat.checkSelfPermission(IzvestajInfo.this, Manifest.permission.CAMERA);
        int readPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeontactPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraresult == PackageManager.PERMISSION_GRANTED && readPermissionResult == PackageManager.PERMISSION_GRANTED &&
                writeontactPermissionResult == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    //zahteva proveru dozvole
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    //vraća rezultate provere dozvole SEND_SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(grantResults.length > 0) {
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean write = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if(camera && read && write) {
                        Toast.makeText(IzvestajInfo.this,
                                "Dozvola je prihvaćena", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(IzvestajInfo.this,
                            "Dozvola nije prihvaćena", Toast.LENGTH_LONG).show();



                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(IzvestajInfo.this, "Slika je sačuvana!", Toast.LENGTH_SHORT).show();
     
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + direct + "/");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), options);

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
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
