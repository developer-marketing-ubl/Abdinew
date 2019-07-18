package com.trisula.abdinew.Fragment;
// Create Ari & Selamat


import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.AsyncTask;
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
import com.trisula.abdinew.Activity.RiwayatActivity;
import com.trisula.abdinew.Activity.ScanActivity;
import com.trisula.abdinew.Config.SessionSharePreference;
import com.trisula.abdinew.Kelas.Api_class;
import com.trisula.abdinew.Kelas.Riwayat_class;
import com.trisula.abdinew.Kelas.SharedVariabel;
import com.trisula.abdinew.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBeranda extends Fragment {
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    SessionSharePreference session;
    ImageView imaAbsen, imaRiwayat;
    TextView txtNamadepan;
    ProgressDialog loading;
    String termin_jv;

    public FragmentBeranda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        imaAbsen = view.findViewById(R.id.imaAbsen);
        imaRiwayat = view.findViewById(R.id.imaRiwayat);
        txtNamadepan = view.findViewById(R.id.txtNamadepan);
        session = new SessionSharePreference(getActivity());
        getData(session.getNama());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            termin_jv = "Pagi";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            termin_jv = "Siang";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            termin_jv = "Sore";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            termin_jv = "Malam";
        }

        imaAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SharedVariabel.TERMIN.equals(termin_jv)){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder.setTitle("Absensi");
                    alertDialogBuilder
                            .setMessage("Anda sudah absen "+termin_jv+" !")
                            //.setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else{
                    Intent intent = new Intent(getActivity(), ScanActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }

            }
        });

        imaRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RiwayatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");

                if (barcode.displayValue.equals("Y")) {
                    simpanAbsen(SharedVariabel.NIP, barcode.displayValue);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder.setTitle("Terjadi Kesalahan !");
                    alertDialogBuilder
                            .setMessage("Aplikasi tidak mengenali scan QRCode anda !")
                            //.setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                //Toast.makeText(getActivity(), "Data " + barcode.displayValue, Toast.LENGTH_LONG).show();
            }

        }
    }

    private void getData(final String Nip) {

        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String url = Api_class.API_PROFIL + Nip;

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
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showDetail(String json) {
        //Toast.makeText(getApplication(),"Data Dokter "+json,Toast.LENGTH_LONG).show();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject c = result.getJSONObject(i);
                String nip = c.getString("nip");
                String nama_karyawan = c.getString("nama_karyawan");
                SharedVariabel.NIP = nip;
                SharedVariabel.NAMA = nama_karyawan;
                txtNamadepan.setText(nama_karyawan);
                getAbsen(SharedVariabel.NIP);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Data " + e, Toast.LENGTH_LONG).show();
        }
    }


    void simpanAbsen(final String Nip, final String Status) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nip", Nip));
                nameValuePairs.add(new BasicNameValuePair("status", Status));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Api_class.API_ABSEN);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equalsIgnoreCase("success")) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder.setTitle("Absensi");
                    alertDialogBuilder
                            .setMessage("Terimakasih telah datang tepat waktu !")
                            //.setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getActivity(),FragmentBeranda.class);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(getActivity(), "Terjadi kesalahan sistem !", Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Nip, Status);
    }

    private void getAbsen(final String Nip) {

        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String url = Api_class.API_ALLABSEN + Nip;

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
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void showHadir(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject c = result.getJSONObject(i);
                String termin_db = c.getString("termin");
                SharedVariabel.TERMIN = termin_db;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Data " + e, Toast.LENGTH_LONG).show();
        }

    }
}

