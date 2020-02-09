package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class IzvestajiListAdapter extends ArrayAdapter<Izvestaj> {

    private int resourceLayout;
    private Context mContext;
    private List<Izvestaj> items;

    public IzvestajiListAdapter(Context context, int resource, List<Izvestaj> items) {
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
    public Izvestaj getItem(int position) {
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

        final Izvestaj izv = getItem(position);

        if (izv != null) {
            final TextView tt1 = (TextView) v.findViewById(R.id.marka);
            final   TextView tt2 = (TextView) v.findViewById(R.id.model);
            final   TextView tt3 = (TextView) v.findViewById(R.id.status);
            final   TextView tt4 = (TextView) v.findViewById(R.id.id_datum2);
            Button info = (Button) v.findViewById(R.id.id_info2);
            ImageButton delete = (ImageButton) v.findViewById(R.id.delete3);

            class MyOnClickListener implements View.OnClickListener
            {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(getContext(), IzvestajInfo.class);
                    myIntent.putExtra("izvestaj_id",String.valueOf(izv.getIzvestaj_id()));
                    myIntent.putExtra("marka",tt1.getText());
                    myIntent.putExtra("model",tt2.getText());
                    myIntent.putExtra("motor",izv.getMotor());
                    myIntent.putExtra("kocnice",izv.getKocnice());
                    myIntent.putExtra("svetla",izv.getSvetla());
                    myIntent.putExtra("status",tt3.getText());
                    myIntent.putExtra("datum2",tt4.getText());
                    mContext.startActivity(myIntent);
                    Toast.makeText(getContext(),"Prebacijem  Vas na novu aktivnost", Toast.LENGTH_LONG).show();
                }
            }

            tt1.setOnClickListener( new MyOnClickListener() );
            tt2.setOnClickListener( new MyOnClickListener() );
            tt3.setOnClickListener( new MyOnClickListener() );
            tt4.setOnClickListener( new MyOnClickListener() );

//            if (tt1 != null) {
            // tt1.setText(p.toString());
         //   tt1.setText(String.valueOf(kl.getIme()));
            tt1.setText(String.valueOf(izv.getMarka()));
            tt2.setText(izv.getModel());
            tt3.setText(String.valueOf(izv.getStatus()));
            tt4.setText(String.valueOf(izv.getDatum2()));
//            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       DatabaseHelper db = new DatabaseHelper(getContext());


                    showDialog3(izv);



                }


            });



            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   DatabaseHelper db = new DatabaseHelper(getContext());
//
                    Intent myIntent = new Intent(getContext(), IzvestajInfo.class);
                    myIntent.putExtra("izvestaj_id",String.valueOf(izv.getIzvestaj_id()));
                    myIntent.putExtra("marka",tt1.getText());
                    myIntent.putExtra("model",tt2.getText());
                    myIntent.putExtra("motor",izv.getMotor());
                    myIntent.putExtra("kocnice",izv.getKocnice());
                    myIntent.putExtra("svetla",izv.getSvetla());
                    myIntent.putExtra("status",tt3.getText());
                    myIntent.putExtra("datum2",tt4.getText());
                   // myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    mContext.startActivity(myIntent);

//                    Toast.makeText(getContext(),
//                            "Izabrali ste stavku : " + kon,
//                            Toast.LENGTH_SHORT).show();
                }


            });

        }


        return v;

    }

    private void showDialog3(final Izvestaj iz) throws Resources.NotFoundException {
        new AlertDialog.Builder(mContext)
                .setTitle("Upozorenje!!!")
                .setMessage(
                        "Da li ste sigurni da želite da obrišete izvestaj? Brisanje se ne može poništiti.")
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



                                int izvestaj_id = iz.getIzvestaj_id();



                    Integer deleteI = db.deleteIzvestaj(izvestaj_id);
                    if(deleteI > 0){
                        Toast.makeText(getContext(),"Izveštaj je obrisan", Toast.LENGTH_LONG).show();
                        //mContext.startActivity(new Intent("com.metropolitan.cs330_pz.KlijentInfo"));
                        mContext.startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));

                    }else{
                        Toast.makeText(getContext(), "Izveštaj nije obrisan", Toast.LENGTH_LONG).show();
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
