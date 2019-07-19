package com.trisula.abdinew.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.trisula.abdinew.Fragment.FragmentBeranda;
import com.trisula.abdinew.Kelas.Api_class;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IjinActivity extends AppCompatActivity {
    String[] SPINNERLIST = {"Pilih Jenis","Road Show","Sakit","Lainnya"};
    Spinner spJenis;
    String jenis;
    EditText etIsiIjin;
    Button  btnSimpanIjin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijin);
        spJenis = (Spinner) findViewById(R.id.sp_jenis);
        etIsiIjin = (EditText) findViewById(R.id.etIsiIjin);
        btnSimpanIjin = (Button) findViewById(R.id.btnSimpanIjin);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        spJenis.setAdapter(arrayAdapter);
        spJenis.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        btnSimpanIjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SharedVariabel.JENIS.equals("Ijin")){
                    Toast.makeText(getApplication(),"Sudah ",Toast.LENGTH_LONG).show();
                }else{
                    if(spJenis.getSelectedItem().equals("Pilih Jenis")){
                        TextView errorText = (TextView)spJenis.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Jenis belum dipilih !");//changes the selected item text to this

                    }else if(etIsiIjin.getText().toString().isEmpty()){
                        etIsiIjin.setError("Ijin harus diisi !");
                    }else{
                        simpanIjin(SharedVariabel.NIP,jenis,etIsiIjin.getText().toString().trim());
                    }
                }

            }
        });
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        String firstItem = String.valueOf(spJenis.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spJenis.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                /*Toast.makeText(parent.getContext(),
                        "Kamu memilih : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();*/
                jenis = parent.getItemAtPosition(pos).toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }



    public void simpanIjin(final String Nip, final String Jenis, final String Alasan){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nip", Nip));
                nameValuePairs.add(new BasicNameValuePair("jenis", Jenis));
                nameValuePairs.add(new BasicNameValuePair("keterangan", Alasan));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Api_class.API_AJUKAN_ABSEN);
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
                if(result.equalsIgnoreCase("success")){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            IjinActivity.this);
                    alertDialogBuilder.setTitle("Absensi");
                    alertDialogBuilder
                            .setMessage("Ijin kehadiran berhasil diajukan !")
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
                    Toast.makeText(getApplication(),"Terjadi kesalahan Sistem",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Nip, Jenis, Alasan);

    }

}
