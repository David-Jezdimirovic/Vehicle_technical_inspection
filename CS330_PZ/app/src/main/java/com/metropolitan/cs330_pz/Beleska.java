package com.metropolitan.cs330_pz;

public class Beleska {

    private int beleska_id;
    private String naslov;
    private String beleska;
    private String datum3;
    private String status2;

    public Beleska(){

    }

    public Beleska(int beleska_id, String naslov, String beleska, String datum3, String status2) {
        this.beleska_id = beleska_id;
        this.naslov = naslov;
        this.beleska = beleska;
        this.datum3 = datum3;
        this.status2 = status2;
    }

    public int getBeleska_id() {
        return beleska_id;
    }

    public void setBeleska_id(int beleska_id) {
        this.beleska_id = beleska_id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getBeleska() {
        return beleska;
    }

    public void setBeleska(String beleska) {
        this.beleska = beleska;
    }

    public String getDatum3() {
        return datum3;
    }

    public void setDatum3(String datum3) {
        this.datum3 = datum3;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    @Override
    public String toString() {
        return "Beleska{" +
                "beleska_id=" + beleska_id +
                ", naslov='" + naslov + '\'' +
                ", beleska='" + beleska + '\'' +
                ", datum3='" + datum3 + '\'' +
                ", status2='" + status2 + '\'' +
                '}';
    }
}
