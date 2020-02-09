package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class KlijentListAdapter extends ArrayAdapter<Klijent> {

    private int resourceLayout;
    private Context mContext;
    private List<Klijent> items;

    public KlijentListAdapter(Context context, int resource, List<Klijent> items) {
        super(context, resource, items);
        this.mContext=context;
        this.resourceLayout = resource;
        this.items = items;

    }

//    public ListAdapter(ListAdapter listAdapter, int list_adapter, List<Student> items) {
//        super(listAdapter,list_adapter,items);
//    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Klijent getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }                  //R.layout.list_adapter

        final Klijent kl = getItem(position);

        if (kl != null) {
            final TextView im = (TextView) v.findViewById(R.id.im);
            final   TextView pre = (TextView) v.findViewById(R.id.pre);
            final   TextView dt = (TextView) v.findViewById(R.id.dt);
            final   TextView vr = (TextView) v.findViewById(R.id.vr);

            final TextView tt1 = (TextView) v.findViewById(R.id.ime);
            final   TextView tt2 = (TextView) v.findViewById(R.id.prezime);
            final   TextView tt3 = (TextView) v.findViewById(R.id.datum);
            final   TextView tt4 = (TextView) v.findViewById(R.id.vreme);
            ImageButton info = (ImageButton) v.findViewById(R.id.id_info);
            ImageButton delete = (ImageButton) v.findViewById(R.id.delete);

            class MyOnClickListener implements View.OnClickListener
            {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(getContext(), KlijentInfo.class);
                    myIntent.putExtra("id",String.valueOf(kl.getId()));
                    myIntent.putExtra("ime",tt1.getText());
                    myIntent.putExtra("prezime",tt2.getText());
                    myIntent.putExtra("datum",tt3.getText());
                    myIntent.putExtra("vreme",tt4.getText());
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);
                    Toast.makeText(getContext(),"Prebacijem  Vas na novu aktivnost", Toast.LENGTH_LONG).show();
                }
            }

            im.setOnClickListener( new MyOnClickListener() );
            pre.setOnClickListener( new MyOnClickListener() );
            dt.setOnClickListener( new MyOnClickListener() );
            vr.setOnClickListener( new MyOnClickListener() );

            tt1.setOnClickListener( new MyOnClickListener() );
            tt2.setOnClickListener( new MyOnClickListener() );
            tt3.setOnClickListener( new MyOnClickListener() );
            tt4.setOnClickListener( new MyOnClickListener() );

//            if (tt1 != null) {
                // tt1.setText(p.toString());

                tt1.setText(String.valueOf(kl.getIme()));
                tt2.setText(kl.getPrezime());
                tt3.setText(String.valueOf(kl.getDatum()));
                tt4.setText(String.valueOf(kl.getVreme()));
//            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(getContext());


                showDialog(kl);


                }


            });



            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   DatabaseHelper db = new DatabaseHelper(getContext());
//
                    Intent myIntent = new Intent(getContext(), KlijentInfo.class);
                    myIntent.putExtra("id",String.valueOf(kl.getId()));
                    Log.e("id",String.valueOf(kl.getId()));
                    myIntent.putExtra("ime",tt1.getText());
                    myIntent.putExtra("prezime",tt2.getText());
                    myIntent.putExtra("datum",tt3.getText());
                    myIntent.putExtra("vreme",tt4.getText());
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);

//                    Toast.makeText(getContext(),
//                            "Izabrali ste stavku : " + kon,
//                            Toast.LENGTH_SHORT).show();
                }


            });

        }


        return v;

    }

    private void showDialog(final Klijent k) {
        new AlertDialog.Builder(mContext)
                .setTitle("Upozorenje!!!")
                .setMessage(
                        "Da li ste sigurni da želite da obrišete klijenta? Brisanjem klijenta brišete i sve njegove izveštaje.")
                .setIcon(
                        mContext.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                DatabaseHelper db = new DatabaseHelper(getContext());



                                int id = k.getId();

                                Integer deleteK = db.deleteKlijentAndReports(id);
                                if(deleteK > 0){
                                    Toast.makeText(getContext(),"Klijent i njegovi izveštaji su obrisani", Toast.LENGTH_LONG).show();
                                   // mContext.startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                                    Intent myIntent = new Intent(getContext(),ListaKlijenata.class);
                                    myIntent.putExtra("datum","");
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    mContext.startActivity(myIntent);

                                }else{
                                    Toast.makeText(getContext(), "Klijent nije obrisan", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                .setNegativeButton(
                        "Ne",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }



}
