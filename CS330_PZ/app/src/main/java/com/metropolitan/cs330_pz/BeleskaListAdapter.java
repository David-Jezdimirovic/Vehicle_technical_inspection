package com.metropolitan.cs330_pz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BeleskaListAdapter extends ArrayAdapter<Beleska> {

    private int resourceLayout;
    private Context mContext;
    private List<Beleska> items;

    public BeleskaListAdapter(Context context, int resource, List<Beleska> items) {
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
    public Beleska getItem(int position) {
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

        final Beleska beleska = getItem(position);

        if (beleska != null) {

            final TextView na = (TextView) v.findViewById(R.id.na);
            final   TextView da = (TextView) v.findViewById(R.id.da);
            final   TextView st = (TextView) v.findViewById(R.id.st);

            final TextView tt1 = (TextView) v.findViewById(R.id.nas);
            final   TextView tt2 = (TextView) v.findViewById(R.id.dat);
            final   TextView tt3 = (TextView) v.findViewById(R.id.sta);

            ImageButton write = (ImageButton) v.findViewById(R.id.write);
            ImageButton delete = (ImageButton) v.findViewById(R.id.del);

            class MyOnClickListener implements View.OnClickListener
            {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(getContext(), BeleskaInfo.class);
                    myIntent.putExtra("beleska_id",String.valueOf(beleska.getBeleska_id()));
                    myIntent.putExtra("naslov",tt1.getText());
                    myIntent.putExtra("beleska",beleska.getBeleska());
                    myIntent.putExtra("datum3",tt2.getText());
                    myIntent.putExtra("status2",tt3.getText());

                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);
                    Toast.makeText(getContext(),"Prebacijem  Vas na novu aktivnost", Toast.LENGTH_LONG).show();
                }
            }

            na.setOnClickListener( new MyOnClickListener() );
            da.setOnClickListener( new MyOnClickListener() );
            st.setOnClickListener( new MyOnClickListener() );


            tt1.setOnClickListener( new MyOnClickListener() );
            tt2.setOnClickListener( new MyOnClickListener() );
            tt3.setOnClickListener( new MyOnClickListener() );


//            if (tt1 != null) {
            // tt1.setText(p.toString());

            tt1.setText(String.valueOf(beleska.getNaslov()));
            tt2.setText(beleska.getDatum3());
            tt3.setText(beleska.getStatus2());

//            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(getContext());


                    showDialog(beleska);


                }


            });



            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   DatabaseHelper db = new DatabaseHelper(getContext());
//
                    Intent myIntent = new Intent(getContext(), BeleskaInfo.class);
                    myIntent.putExtra("beleska_id",String.valueOf(beleska.getBeleska_id()));
                  //  Log.e("id",String.valueOf(beleska.getBeleska_id()));
                    myIntent.putExtra("naslov",tt1.getText());
                    myIntent.putExtra("beleska",beleska.getBeleska());
                    myIntent.putExtra("datum3",tt2.getText());
                    myIntent.putExtra("status2",tt3.getText());
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

    private void showDialog(final Beleska b) {
        new AlertDialog.Builder(mContext)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Da li ste sigurni da želite da obrišete ovu belešku?")
                .setIcon(
                        mContext.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))//ic_dialog_alert
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                DatabaseHelper db = new DatabaseHelper(getContext());

                                int id = b.getBeleska_id();

                                Integer deleteB = db.deleteBeleska(id);
                                if(deleteB > 0){
                                    Toast.makeText(getContext(),"Beleška je obrisana.", Toast.LENGTH_LONG).show();
                                    // mContext.startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                                    Intent myIntent = new Intent(getContext(),ListaBeleski.class);
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    mContext.startActivity(myIntent);

                                }else{
                                    Toast.makeText(getContext(), "Beleška nije obrisana", Toast.LENGTH_LONG).show();
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
