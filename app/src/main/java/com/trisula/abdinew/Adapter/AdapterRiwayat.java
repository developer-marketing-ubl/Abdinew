package com.trisula.abdinew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trisula.abdinew.Kelas.Riwayat_class;
import com.trisula.abdinew.R;

import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.MyViewHolder> {

    private Context mContext;
    private List<Riwayat_class> riwayatList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTermin,txtWaktu;
        public ImageView imgFoto;
        //public CircleImageView imgFoto;
        public CardView cv_main;
        public RelativeLayout relaList;

        public MyViewHolder(View view) {
            super(view);
            imgFoto = view.findViewById(R.id.imgFoto);
            cv_main = view.findViewById(R.id.cardlist_item);
            relaList = view.findViewById(R.id.relaList);
            txtTermin = view.findViewById(R.id.txtTermin);
            txtWaktu = view.findViewById(R.id.txtWaktuAbsen);

        }
    }

    public AdapterRiwayat(Context mContext, List<Riwayat_class> riwayatList) {
        this.mContext = mContext;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_riwayat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(riwayatList.isEmpty()){
            Toast.makeText(mContext.getApplicationContext(),"Belum ada riwayat absen",Toast.LENGTH_LONG).show();
        }else{
            final Riwayat_class riwayatClass = riwayatList.get(position);

            if(riwayatClass.getStatus_absen().equals("Y")){
                holder.imgFoto.setImageResource(R.drawable.ic_hadir);
            }else if(riwayatClass.getStatus_absen().equals("I")){
                holder.imgFoto.setImageResource(R.drawable.ic_ijin);
            }

            holder.txtTermin.setText(riwayatClass.getTermin());
            holder.txtWaktu.setText(riwayatClass.getWaktu_absen());
        }

    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }


}
