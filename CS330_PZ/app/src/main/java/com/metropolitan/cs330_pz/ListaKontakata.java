package com.metropolitan.cs330_pz;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaKontakata extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    EditText phone,emailAddress,message;
    ImageButton sendSMS;
    ImageButton sendEMAIL;
    Button getContacts;
    ListView listView;
    Animation fadein,zoomin;

    ArrayList<Kontakt> contactsArray ;
    Cursor cursor, ce, cp ;
    String name, contactNumber, email ;
    AutoCompleteTextView textView;
//    Thread thread;
    //ArrayAdapter<Kontakt> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_kontakata);



        //provera da li je dozvola već odobrena
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("dozvola", "Dozvola je već odobrena.");
            } else {
                requestPermission();
            }
        }

        phone = (EditText)findViewById(R.id.phone);
        emailAddress =  (EditText)findViewById(R.id.emailAddress);
        message = (EditText)findViewById(R.id.message);
        sendSMS = (ImageButton) findViewById(R.id.sms);
        getContacts = (Button) findViewById(R.id.getContacts);
        textView = (AutoCompleteTextView) findViewById(R.id.contact);
        listView = (ListView) findViewById(R.id.lista);
        contactsArray = new ArrayList<Kontakt>();
        fadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        zoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);

        message.setScroller(new Scroller(getApplicationContext()));
        message.setMaxLines(3);
        message.setVerticalScrollBarEnabled(true);
        message.setMovementMethod(new ScrollingMovementMethod());

 

        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        Log.e("dozvola", "Dozvola je već odobrena.");
                        AddContactstoArray();



                    } else {
                        requestPermission();
                    }
                }



                ArrayAdapter<Kontakt> adapter = new ArrayAdapter<Kontakt>(ListaKontakata.this,
                        android.R.layout.simple_dropdown_item_1line, contactsArray);

                ArrayAdapter<Kontakt> adapter2 = new ArrayAdapter<Kontakt>(ListaKontakata.this,
                        android.R.layout.simple_list_item_multiple_choice, contactsArray);

                textView.setAdapter(adapter);

                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setTextFilterEnabled(true);


                listView.setAdapter(adapter2);
                listView.startAnimation(zoomin);
            }


        });

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent,
                                     View view, int index, long id)
            {


                Kontakt kon=(Kontakt) parent.getAdapter().getItem(index);

                Toast.makeText(getBaseContext(),
                        "Izabrali ste stavku : " + kon,
                        Toast.LENGTH_SHORT).show();



                phone.setText(kon.getPhone());
                emailAddress.setText(kon.getEmail());
                //String f=kon.getEmail();

                phone.startAnimation(fadein);
                emailAddress.startAnimation(fadein);
            }

        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent,
                                     View view, int index, long id)
            {

                //  Kontakt kontakt2= (Kontakt) listView.getAdapter().getItem(index);
                //  Kontakt kon = (Kontakt) listView.getItemAtPosition(index);
                //  Object o = parent.getAdapter().getItem(index);
                Kontakt kon=(Kontakt) parent.getAdapter().getItem(index);
                //  textView.setText(kon.toString());
                textView.setText(listView.getItemAtPosition(index).toString());


                Toast.makeText(getBaseContext(),
                        "Izabrali ste stavku : " + kon,
                        Toast.LENGTH_SHORT).show();
//                    phone.setText(contactsArray.get(index).getPhone());
//                    emailAddress.setText(contactsArray.get(index).getEmail());
                phone.setText(kon.getPhone());
                emailAddress.setText(kon.getEmail());

                phone.startAnimation(fadein);
                emailAddress.startAnimation(fadein);
            }

        });




        sendSMS.setOnClickListener(new View.OnClickListener() {
            //Slanje SMS poruke
            @Override
            public void onClick(View view) {
                String sms = message.getText().toString();
                String phoneNum = phone.getText().toString();
                if(!TextUtils.isEmpty(sms) && !TextUtils.isEmpty(phoneNum)) {
                    if(checkPermission()) {
                        SmsManager smsManager = SmsManager.getDefault();

                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {

                                Kontakt kontakt2= (Kontakt) listView.getAdapter().getItem(i);

                                String num = kontakt2.getPhone();
                                //   String num = contactsArray.get(i).getPhone();

                                Log.e("broj", num);
                                smsManager.sendTextMessage(num, null, sms, null, null);

                            }
                        }
                        
                        Toast.makeText(ListaKontakata.this, "SMS je poslat", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ListaKontakata.this, "Dozvola nije prihvaćena", Toast.LENGTH_SHORT).show();
                        message.setText("");
                        phone.setText("");
                    }
                }
            }
        });
    }


//    public void onListItemClick(AutoCompleteTextView parent, View v,
//                                int position, long id)
//    {
//        Toast.makeText(getApplicationContext(),
//                "Izabrali ste " + contactsArray.get(position),
//                Toast.LENGTH_SHORT).show();
//    }


    //provera dozvole
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ListaKontakata.this, Manifest.permission.SEND_SMS);
        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int ContactPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);

        if (result == PackageManager.PERMISSION_GRANTED && CallPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ContactPermissionResult == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    //zahteva proveru dozvole
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);

    }

    //vraća rezultate provere dozvole SEND_SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(grantResults.length > 0) {
                    boolean smsText = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean CallPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if(smsText && CallPermission && ReadContactsPermission) {
                        Toast.makeText(ListaKontakata.this,
                                "Dozvola je prihvaćena", Toast.LENGTH_LONG).show();
                        sendSMS.setEnabled(true);
                    }
                } else {
                    Toast.makeText(ListaKontakata.this,
                            "Dozvola nije prihvaćena", Toast.LENGTH_LONG).show();

                    sendSMS.setEnabled(false);

                }
                break;
        }
    }

    //uzima sve kontakte iz telefona
    public void AddContactstoArray(){



        contactsArray.clear();
        ContentResolver cr = getContentResolver();
        cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // get the contact's information
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                //get email address
                String email = "";
                ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                if (ce != null && ce.moveToFirst()) {
                    email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    ce.close();
                }

                // get the user's phone number
                String phoneNum = "";
                cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                if (cp != null && cp.moveToFirst()) {
                    phoneNum = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    cp.close();
                }
                Kontakt contact = new Kontakt();
                contact.setName(name);
                contact.setPhone(phoneNum);
                contact.setEmail(email);
                contactsArray.add(contact);


            } while (cursor.moveToNext());

            // clean up cursor
            cursor.close();
            cp.close();
            ce.close();
        }
    }


    public void onClickSendEmail(View v) {

        String[] to =
                {emailAddress.getText().toString(),
                        "osoba2@email.com"};
        String[] cc = {"osoba3@email.com"};
        sendEmail(to, cc, "Pozdrav!!!", message.getText().toString());
    }

    //-slanje e-mail poruke na drugi uređaj”-
    private void sendEmail(String[] emailAddresses, String[] carbonCopies,
                           String subject, String message)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddresses;
        String[] cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    public void makeCall(View view)
    {
        // final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        String phoneNum = phone.getText().toString();
        if(!TextUtils.isEmpty(phoneNum)) {
            String dial = "tel:" + phoneNum;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }else {
            Toast.makeText(ListaKontakata.this, "Unesite validan broj telefona", Toast.LENGTH_SHORT).show();
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

