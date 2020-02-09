package com.metropolitan.cs330_pz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter{
    Context context;
    int[] images;
    LayoutInflater layoutInflater;
    Bitmap[] myBitmap;

    public MyPagerAdapter(Context context, Bitmap[] myBitmap) {
        this.context = context;
       // this.images = images;
        this.myBitmap = myBitmap;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return myBitmap.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//       // int[] ind= images.get(position);
        imageView.setImageBitmap( myBitmap[position]);


        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Slika broj " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

