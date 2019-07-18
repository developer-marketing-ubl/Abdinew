package com.trisula.abdinew.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trisula.abdinew.Adapter.AdapterRiwayat;
import com.trisula.abdinew.Kelas.Api_class;
import com.trisula.abdinew.Kelas.Riwayat_class;
import com.trisula.abdinew.Kelas.SharedVariabel;
import com.trisula.abdinew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiwayatActivity extends AppCompatActivity {
RecyclerView recyclerView;
AdapterRiwayat adapter;
List<Riwayat_class> riwayatList;
ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        riwayatList = new ArrayList<Riwayat_class>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_hadir);
        adapter = new AdapterRiwayat(getApplication(),riwayatList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getHadir(SharedVariabel.NIP);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_hadir:
                    getHadir(SharedVariabel.NIP);
                    return true;
                case R.id.navigation_ijin:
                    getIjin(SharedVariabel.NIP);
                    return true;
            }
            return false;
        }
    };


    private void getHadir(final String Nip){

        loading = ProgressDialog.show(RiwayatActivity.this,"Please wait...","Fetching...",false,false);

        String url = Api_class.API_HADIR+Nip;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                loading.dismiss();
                //Toast.makeText(getApplication(),"Data "+response,Toast.LENGTH_LONG).show();
                showHadir(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }


    private void showHadir(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        riwayatList.clear();
        adapter.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String termin = jo.getString("termin");
                String waktu = jo.getString("waktu_absen");
                String status = jo.getString("status");

                SharedVariabel.TERMIN = termin;

                Riwayat_class riwayat = new Riwayat_class(
                        termin,
                        waktu,
                        status
                );
                riwayatList.add(riwayat);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

    }


    private void getIjin(final String Nip){

        loading = ProgressDialog.show(RiwayatActivity.this,"Please wait...","Fetching...",false,false);

        String url = Api_class.API_IJIN+Nip;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showIjin(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }


    private void showIjin(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        riwayatList.clear();
        adapter.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String termin = jo.getString("termin");
                String waktu = jo.getString("waktu_absen");
                String status = jo.getString("status");

                SharedVariabel.TERMIN = termin;

                Riwayat_class riwayat = new Riwayat_class(
                        termin,
                        waktu,
                        status
                );
                riwayatList.add(riwayat);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

    }


}
