package com.trisula.abdinew.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trisula.abdinew.Kelas.Riwayat_class;
import com.trisula.abdinew.R;

import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.MyViewHolder> {

    private Context mContext;
    private List<Riwayat_class> riwayatList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLayanan,txtBiaya,txtWaktu,txtAlamat,txtIdlayanan;
        public ImageView imgStatus,imgFoto;
        //public CircleImageView imgFoto;
        public CardView cv_main;
        public RelativeLayout relaList;

        public MyViewHolder(View view) {
            super(view);
            txtLayanan = view.findViewById(R.id.txtLayanan);
            txtBiaya = view.findViewById(R.id.txtBiaya);
            txtWaktu = view.findViewById(R.id.txtWaktuPesan);
            txtAlamat = view.findViewById(R.id.txt_alamat_pesan);
            txtIdlayanan = view.findViewById(R.id.txt_idlayanan);
            imgStatus = view.findViewById(R.id.imgStatus);
            imgFoto = view.findViewById(R.id.imgFoto);
            cv_main = view.findViewById(R.id.cardlist_item);
            relaList = view.findViewById(R.id.relaList);
        }
    }
}
