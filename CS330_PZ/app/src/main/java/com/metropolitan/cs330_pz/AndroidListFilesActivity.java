package com.metropolitan.cs330_pz;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AndroidListFilesActivity extends ListActivity {

    File root;
    File pdf;

    private List<String> fileList = new ArrayList<String>();
String put;
String tip;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        super.onCreate(savedInstanceState);
        put=i.getStringExtra("put");
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/cs330_pz/"+ put);

        Log.e("put",Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/"+ put);
        // ListDir(root);

       // pdf = new File(root, "PDF");
        ListDir(root);
    }

    void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        fileList.add(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/cs330_pz/"+ put+"/");
        tip=Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/cs330_pz/"+ put+"/";
        for (File file : files) {
            fileList.add(file.getPath());

        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fileList);

        setListAdapter(directoryList);

    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        //selection.setText(fileList.indexOf(simple_list_item_1));
        OpenPdf(fileList.get(position).toString());
    }

    public void OpenPdf(String path)
    {
        File file = new File(path);
        if (file.exists()) {
            Uri p = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setDataAndType(p,"*/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            }
            catch (ActivityNotFoundException e){
            }
        }
    }
}
