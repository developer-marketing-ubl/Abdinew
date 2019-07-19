package com.trisula.abdinew.Fragment;
// Create Ari & Selamat


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.trisula.abdinew.Kelas.SharedVariabel;
import com.trisula.abdinew.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRekap extends Fragment {
    private WebView webs;

    public FragmentRekap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_rekap, container, false);
        webs = view.findViewById(R.id.web);
        webs.getSettings().setJavaScriptEnabled(true);
        webs.setWebViewClient(new MyBrowser());
        webs.loadUrl("http://ppikubl.com/api_absensi/dt_rekap.php?nip="+ SharedVariabel.NIP);

        return view;
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
