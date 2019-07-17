package com.trisula.abdinew.Fragment;
// Create Ari & Selamat


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.trisula.abdinew.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRekap extends Fragment {


    public FragmentRekap() {
        // Required empty public constructor
    }

    TextView txtBuatLaporan,txtCekLaporan,txtProfil;
    CardView cardLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_rekap, container, false);

        return view;
    }




}
