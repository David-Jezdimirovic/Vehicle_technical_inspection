package com.metropolitan.cs330_pz;

import java.sql.Time;
import java.util.Date;

public class Klijent {

    private int id;
    private String ime;
    private String prezime;
    private String datum;
    private String vreme;

    public Klijent(){

    }

    public Klijent(int id, String ime, String prezime, String datum, String vreme) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.datum = datum;
        this.vreme = vreme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    @Override
    public String toString() {
        return "Klijent: " +
//                "id=" + id +
                 ime  + " " +
                 prezime + '\n' +
                "Datum i vreme: " +
                 datum + "   " +
                 vreme
                ;
    }


}
