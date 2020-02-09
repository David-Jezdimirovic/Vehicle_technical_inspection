package com.metropolitan.cs330_pz;

public class Izvestaj {

    private int izvestaj_id;
    private int klijent_id;
    private String marka;
    private String model;
    private String motor;
    private String kocnice;
    private String svetla;
    private String status;
    private String datum2;

    public Izvestaj(){

    }

    public Izvestaj(int izvestaj_id, int klijent_id, String marka, String model, String motor, String kocnice, String svetla, String status, String datum2) {
        this.izvestaj_id = izvestaj_id;
        this.klijent_id = klijent_id;
        this.marka = marka;
        this.model = model;
        this.motor = motor;
        this.kocnice = kocnice;
        this.svetla = svetla;
        this.status = status;
        this.datum2 = datum2;
    }

    public int getIzvestaj_id() {
        return izvestaj_id;
    }

    public void setIzvestaj_id(int izvestaj_id) {
        this.izvestaj_id = izvestaj_id;
    }

    public int getKlijent_id() {
        return klijent_id;
    }

    public void setKlijent_id(int klijent_id) {
        this.klijent_id = klijent_id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getKocnice() {
        return kocnice;
    }

    public void setKocnice(String kocnice) {
        this.kocnice = kocnice;
    }

    public String getSvetla() {
        return svetla;
    }

    public void setSvetla(String svetla) {
        this.svetla = svetla;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatum2() {
        return datum2;
    }

    public void setDatum2(String datum2) {
        this.datum2 = datum2;
    }

    @Override
    public String toString() {

             return   '\n' +
                      "Marka:    " + marka + '\n' +
                      "Model:    " + model + '\n' +
                      "Status:    " + status + '\n' +
                      "Datum:    " + datum2  + '\n';

    }
}

