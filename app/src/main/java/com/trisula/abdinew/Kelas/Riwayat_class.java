package com.trisula.abdinew.Kelas;

public class Riwayat_class {
    public String termin;
    public String waktu_absen;
    public String status_absen;

    public Riwayat_class(){

    }

    public Riwayat_class(String termin, String waktu_absen, String status_absen) {
        this.termin = termin;
        this.waktu_absen = waktu_absen;
        this.status_absen = status_absen;
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

    public String getStatus_absen() {
        return status_absen;
    }

    public void setStatus_absen(String status_absen) {
        this.status_absen = status_absen;
    }
}
