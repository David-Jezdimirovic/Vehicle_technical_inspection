package com.metropolitan.cs330_pz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "baza.db";
    public static final String TABLE_NAME1 = "klijenti";
    public static final String TABLE_NAME2 = "izvestaji";
    public static final String TABLE_NAME3 = "beleske";

    //tabela klijenti
    public static final String COL_ID = "id";
    public static final String COL_IME = "ime";
    public static final String COL_PREZIME = "prezime";
    public static final String COL_DATUM = "datum";
    public static final String COL_VREME= "vreme";

    //tabela izvestaji
    public static final String COL_IZVESTAJ_ID = "izvestaj_id";
    public static final String COL_KLIJENT_ID = "klijent_id";
    public static final String COL_MARKA = "marka";
    public static final String COL_MODEL = "model";
    public static final String COL_MOTOR = "motor";
    public static final String COL_KOCNICE = "kocnice";
    public static final String COL_SVETLA= "svetla";
    public static final String COL_STATUS= "status";
    public static final String COL_DATUM2= "datum2";

    //tabela beleske
    public static final String COL_BELESKA_ID = "beleska_id";
    public static final String COL_NASLOV = "naslov";
    public static final String COL_BELESKA = "beleska";
    public static final String COL_DATUM3 = "datum3";
    public static final String COL_STATUS2 = "status2";

    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        try {
//            // copydatabase();
//            createdatabase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        db.execSQL("create table " + TABLE_NAME1 + " if not EXISTS " + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ime TEXT, prezime TEXT, datum TEXT, vreme TEXT)");
//        db.execSQL("create table " + TABLE_NAME2 +" (izvestaj_id INTEGER PRIMARY KEY AUTOINCREMENT, klijent_id INTEGER, marka TEXT, model TEXT, motor TEXT, kocnice TEXT, svetla TEXT, status TEXT, datum2 TEXT, FOREIGN KEY(klijent_id) REFERENCES klijenti(id))");
//        db.execSQL("create table " + TABLE_NAME3 +" (beleska_id INTEGER PRIMARY KEY AUTOINCREMENT, naslov TEXT, beleska TEXT, datum3 TEXT, status2 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
//
//        onCreate(db);
    }



    public SQLiteDatabase myDataBase;




    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(!dbexist) {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String DB_PATH ="/data/data/"+mContext.getPackageName()+"/databases/";
            String myPath = DB_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        String DB_PATH ="/data/data/"+mContext.getPackageName()+"/databases/";
        InputStream myinput = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }




    public boolean addKlijent(String ime, String prezime, String datum, String vreme) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(COL_IME, ime);
        value.put(COL_PREZIME, prezime);
        value.put(COL_DATUM, datum);
        value.put(COL_VREME, vreme);

        long result = db.insert(TABLE_NAME1, null, value);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    public Cursor getAllKlijenti() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor data = db.rawQuery("select * from "+TABLE_NAME1,null);
//        return data;
//    }


    public List<Klijent> getAllKlijenti2() {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, ime, prezime, datum, vreme FROM klijenti";
        Cursor cursor = db.rawQuery(query, null);
        List<Klijent> list = new ArrayList<Klijent>();
        while(cursor.moveToNext()) {
            Klijent klijent = new Klijent();
            klijent.setId(cursor.getInt(0));
            klijent.setIme(cursor.getString(1));
            klijent.setPrezime(cursor.getString(2));
            klijent.setDatum(cursor.getString(3));
            klijent.setVreme(cursor.getString(4));
            list.add(klijent);
        }
        db.close();
        return list;
    }

    public List<Klijent> getAllKlijentiSaDatumom(String datumstr) {

        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
       String query;
        if(datumstr.equals("")){
             query = "SELECT id, ime, prezime, datum, vreme FROM klijenti";
        }else {
             query = "SELECT id, ime, prezime, datum, vreme FROM klijenti WHERE datum LIKE \"" + datumstr + "\"";
        }
        Cursor cursor = db.rawQuery(query, null);
        List<Klijent> list2 = new ArrayList<Klijent>();
        while(cursor.moveToNext()) {
            Klijent klijent = new Klijent();
            klijent.setId(cursor.getInt(0));
            klijent.setIme(cursor.getString(1));
            klijent.setPrezime(cursor.getString(2));
            klijent.setDatum(cursor.getString(3));
            klijent.setVreme(cursor.getString(4));
            list2.add(klijent);
        }
        db.close();
        return list2;
    }

    public int prebrojZakazaneKlijenteSaDatumom(String datum){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(datum) AS result FROM klijenti WHERE datum LIKE \"" + datum + "\"";
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        while(cursor.moveToNext()) {
             result = cursor.getInt(0);
        }
        db.close();
        return result;
    }

    public boolean updateKlijent(int id,String ime,String prezime, String datum, String vreme) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(COL_INDEKS,broj_indeksa);
        contentValues.put(COL_IME,ime);
        contentValues.put(COL_PREZIME,prezime);
        contentValues.put(COL_DATUM,datum);
        contentValues.put(COL_VREME,vreme);

        long result1 = db.update(TABLE_NAME1, contentValues, COL_ID+"="+id,null);
        db.close();
        if(result1 == -1)
            return false;
        else
            return true;


    }


    public Integer deleteKlijentAndReports (int indeks) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_NAME2, COL_KLIJENT_ID+"="+indeks,null);
         int b = db.delete(TABLE_NAME1, COL_ID+"="+indeks,null);


        return a + b;
    }

    public Integer deleteIzvestaj (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_NAME2, COL_IZVESTAJ_ID+"="+id,null);

        return a ;
    }



    public List<Izvestaj> getAllIzvestajiByIdKlijenta(int id) {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM izvestaji WHERE klijent_id = \"" + id + "\"";
        Cursor cursor = db.rawQuery(query, null);
        List<Izvestaj> list = new ArrayList<Izvestaj>();
        while(cursor.moveToNext()) {
            Izvestaj izvestaj = new Izvestaj();
            izvestaj.setIzvestaj_id(cursor.getInt(0));
            izvestaj.setKlijent_id(cursor.getInt(1));
            izvestaj.setMarka(cursor.getString(2));
            izvestaj.setModel(cursor.getString(3));
            izvestaj.setMotor(cursor.getString(4));
            izvestaj.setKocnice(cursor.getString(5));
            izvestaj.setSvetla(cursor.getString(6));
            izvestaj.setStatus(cursor.getString(7));
            izvestaj.setDatum2(cursor.getString(8));

            list.add(izvestaj);
        }
        db.close();
        return list;
    }




    public boolean addReport(int klijent_id, String marka, String model , String motor, String kocnice, String svetla, String status, String datum) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(COL_KLIJENT_ID, klijent_id);
        value.put(COL_MARKA, marka);
        value.put(COL_MODEL, model);
        value.put(COL_MOTOR, motor);
        value.put(COL_KOCNICE, kocnice);
        value.put(COL_SVETLA, svetla);
        value.put(COL_STATUS, status);
        value.put(COL_DATUM2, datum);


        long result = db.insert(TABLE_NAME2, null, value);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean updateReport(int izvestaj_id, String marka, String model , String motor, String kocnice, String svetla, String status, String datum2) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(COL_INDEKS,broj_indeksa);
      //  contentValues.put(COL_IZVESTAJ_ID, izvestaj_id);
      // contentValues.put(COL_KLIJENT_ID, id);
        contentValues.put(COL_MARKA, marka);
        contentValues.put(COL_MODEL, model);
        contentValues.put(COL_MOTOR, motor);
        contentValues.put(COL_KOCNICE, kocnice);
        contentValues.put(COL_SVETLA, svetla);
        contentValues.put(COL_STATUS, status);
        contentValues.put(COL_DATUM2, datum2);

        long result1 = db.update(TABLE_NAME2, contentValues, COL_IZVESTAJ_ID+"="+izvestaj_id,null);
        db.close();
        if(result1 == -1)
            return false;
        else
            return true;


    }

    public List<Beleska> getAllBeleske() {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT beleska_id, naslov, beleska, datum3, status2 FROM beleske";
        Cursor cursor = db.rawQuery(query, null);
        List<Beleska> list = new ArrayList<Beleska>();
        while(cursor.moveToNext()) {
            Beleska beleska = new Beleska();
            beleska.setBeleska_id(cursor.getInt(0));
            beleska.setNaslov(cursor.getString(1));
            beleska.setBeleska(cursor.getString(2));
            beleska.setDatum3(cursor.getString(3));
            beleska.setStatus2(cursor.getString(4));
            list.add(beleska);
        }
        db.close();
        return list;
    }

    public boolean addBeleska(String naslov, String beleska, String datum3, String status2) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(COL_NASLOV, naslov);
        value.put(COL_BELESKA, beleska);
        value.put(COL_DATUM3, datum3);
        value.put(COL_STATUS2, status2);

        long result = db.insert(TABLE_NAME3, null, value);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateBeleska(int id,String naslov,String beleska, String datum3, String status2 ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(COL_INDEKS,broj_indeksa);
        contentValues.put(COL_NASLOV,naslov);
        contentValues.put(COL_BELESKA,beleska);
        contentValues.put(COL_DATUM3,datum3);
        contentValues.put(COL_STATUS2,status2);

        long result1 = db.update(TABLE_NAME3, contentValues, COL_BELESKA_ID+"="+id,null);
        db.close();
        if(result1 == -1)
            return false;
        else
            return true;


    }


    public Integer deleteBeleska (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_NAME3, COL_BELESKA_ID+"="+id,null);

        return a ;
    }

    public Integer deleteAllBeleske () {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_NAME3, null,null);

        return a ;
    }

}
