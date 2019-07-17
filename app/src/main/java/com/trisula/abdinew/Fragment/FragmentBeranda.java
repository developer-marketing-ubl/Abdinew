package com.trisula.abdinew.Fragment;
// Create Ari & Selamat


import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;
import com.trisula.abdinew.Activity.ScanActivity;
import com.trisula.abdinew.Config.SessionSharePreference;
import com.trisula.abdinew.Kelas.Api_class;
import com.trisula.abdinew.Kelas.SharedVariabel;
import com.trisula.abdinew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBeranda extends Fragment {
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    SessionSharePreference session;
    ImageView imaAbsen;
    TextView txtNamadepan;
    ProgressDialog loading;

    public FragmentBeranda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        imaAbsen = view.findViewById(R.id.imaAbsen);
        txtNamadepan = view.findViewById(R.id.txtNamadepan);
        session = new SessionSharePreference(getActivity());
        getData(session.getNama());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        imaAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                final Barcode barcode = data.getParcelableExtra("barcode");
                Toast.makeText(getActivity(), "Data " + barcode.displayValue, Toast.LENGTH_LONG).show();

            }

        }
    }

    private void getData(final String Nip){

        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String url = Api_class.API_PROFIL+Nip;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showDetail(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showDetail(String json){
        //Toast.makeText(getApplication(),"Data Dokter "+json,Toast.LENGTH_LONG).show();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject c = result.getJSONObject(i);
                String nip = c.getString("nip");
                String nama_karyawan = c.getString("nama_karyawan");
                SharedVariabel.NIP = nip;
                SharedVariabel.NAMA = nama_karyawan;
                txtNamadepan.setText(nama_karyawan);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Data "+e,Toast.LENGTH_LONG).show();
        }
    }


}
