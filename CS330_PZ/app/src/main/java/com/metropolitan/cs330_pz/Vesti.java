package com.metropolitan.cs330_pz;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Vesti extends AppCompatActivity {

    ArrayAdapter adapter;
    ListView listView;
    List<String> vesti = new ArrayList<>();
    List<String> href = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vesti);



        new RetrieveVesti().execute("https://www.blic.rs/tehnicki-pregled");

         listView = (ListView) findViewById(R.id.listavesti);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent,
                                     View view, int index, long id)
            {
              
               String vst = (String) parent.getAdapter().getItem(index);
                String adr = href.get(index);
                Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(adr));


                startActivityForResult(myIntent, 0);

                Toast.makeText(getBaseContext(),
                        "Izabrali ste stavku : " + vst,
                        Toast.LENGTH_SHORT).show();

            }

        });

    }






    class RetrieveVesti extends AsyncTask<String, Void, List> {

        private Exception exception;

        protected List doInBackground(String... urls) {
            try {


                    String url="http://www.kuhinjica.rs/";
                Document doc = Jsoup.connect(urls[0]).get();
                   
                    Elements newsHeadlines = doc.select("article > div > h3 > a");
                
                    for(Element e: newsHeadlines) {
                      //  String href = newsHeadlines.attr("href");
                    href.add(String.format("%s\n", e.attr("href")));
                    vesti.add(String.format("%s\n\n", e.html()));
                      //  vesti.add(href);
                }
                    return vesti;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }

        }

        protected void onPostExecute(List vesti) {
          
            adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, vesti);
            // adapter = new IzvestajiListAdapter(this, R.layout.izvestaji_list_adapter, izvestaji);
            listView.setAdapter(adapter);


        
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
