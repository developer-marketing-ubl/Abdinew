package com.trisula.abdinew.Fragment;
// Create Ari & Selamat


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trisula.abdinew.Config.RequestHandler;
import com.trisula.abdinew.Config.SessionSharePreference;
import com.trisula.abdinew.Kelas.Api_class;
import com.trisula.abdinew.Kelas.SharedVariabel;
import com.trisula.abdinew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfil extends Fragment {
SessionSharePreference session;
CardView cardLogout;
TextView txtNip, txtNamaProfil,txtNamadetail;
ProgressDialog loading;

    public FragmentProfil() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profil, container, false);
        cardLogout = view.findViewById(R.id.cardLogout);
        session = new SessionSharePreference(getActivity());

        txtNip = view.findViewById(R.id.txtNIP);
        txtNamaProfil = view.findViewById(R.id.txtNamaProfil);
        txtNamadetail = view.findViewById(R.id.txtNamadetail);

        //getJSON(SharedVariabel.ID_USER_PERMANEN);

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setNama(null);
            }
        });

        txtNip.setText(SharedVariabel.NIP);
        txtNamaProfil.setText(SharedVariabel.NAMA);
        txtNamadetail.setText(SharedVariabel.NAMA);

        return view;
    }

}
