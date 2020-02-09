package com.metropolitan.cs330_pz;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerGallery extends AppCompatActivity {


    String direct = "/cs330_pz/";
    String ime, izvestaj_id;
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    ArrayList<String> putanje = new ArrayList<String>();
    Bitmap[] myBitmap;


//    int images[] = {R.drawable.metpozadina, R.drawable.pic1, R.drawable.pic2,
//            R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7};

    //int images[]={R.drawable.app_round_icon_512x512,R.drawable.calenda_512x512,R.drawable.camera2_512x512};
   // List images = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_gallery);


        TextView poruka = (TextView)findViewById(R.id.poruka4);

        Intent intent = getIntent();

        izvestaj_id = intent.getStringExtra("izvestaj_id");
        ime = intent.getStringExtra("ime");
        direct += ime + izvestaj_id;

        final File targetDirector = new File(

                Environment.getExternalStorageDirectory() + direct + "/");
        File[] files = targetDirector.listFiles();

        if(files != null) {
            poruka.setVisibility(View.GONE);
             myBitmap = new Bitmap[files.length];

            for (int i = 0; i < files.length; i++)
            {

                putanje.add(files[i].getAbsolutePath());
                myBitmap[i] = BitmapFactory.decodeFile(files[i].getAbsolutePath());
                //myBitmap[i] = BitmapFactory.decodeFile(putanje.get(i));
                Log.e("putanja",files[i].getAbsolutePath());
            }
//            for (int j = 0; j < f.size(); j++) {
//                myBitmap[j] = BitmapFactory.decodeFile(putanje.get(j));
//            }
        }else{
            myBitmap = new Bitmap[0];
            poruka.setVisibility(View.VISIBLE);
        }

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        myPagerAdapter = new MyPagerAdapter(ViewPagerGallery.this, myBitmap);
        viewPager.setAdapter(myPagerAdapter);



        /////////////////////////////////////////////////////////
// vraca sve slike sa uredjaja
//        ImageView j = (ImageView)findViewById(R.id.im);
//        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//
//        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
//        final String orderBy = MediaStore.Images.Media._ID;
////Stores all the images from the gallery in Cursor
//        Cursor cursor = getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
//                null, orderBy);
////Total number of images
//        int count = cursor.getCount();
//
////Create an array to store path to all the images
//        String[] arrPath = new String[count];
////
//        for (int i = 0; i < count; i++) {
//            cursor.moveToPosition(i);
//            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//            //Store the path of the image
//            arrPath[i]= cursor.getString(dataColumnIndex);
//            Log.i("PATH", arrPath[i]);
//            j.setImageResource(dataColumnIndex);
//         //   images2[i]=dataColumnIndex;
//        }
//// The cursor should be freed up after use with close()
//        cursor.close();
////////////////////////////////////////////////////


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
