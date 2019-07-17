package com.trisula.abdinew.Kelas;

public class Riwayat_class {
    public String termin;
    public String waktu_absen;

    public Riwayat_class(){

    }

    public Riwayat_class(String termin, String waktu_absen) {
        this.termin = termin;
        this.waktu_absen = waktu_absen;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getWaktu_absen() {
        return waktu_absen;
    }

    public void setWaktu_absen(String waktu_absen) {
        this.waktu_absen = waktu_absen;
    }
}
