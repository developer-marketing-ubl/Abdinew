package com.trisula.abdinew.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trisula.abdinew.Config.SessionSharePreference;
import com.trisula.abdinew.Fragment.FragmentRekap;
import com.trisula.abdinew.Fragment.FragmentBeranda;
import com.trisula.abdinew.Fragment.FragmentProfil;
import com.trisula.abdinew.R;

public class BerandaActivity extends AppCompatActivity {
    FragmentBeranda fragmentBeranda;
    FragmentProfil fragmentProfil;
    FragmentRekap fragmentRekap;
    SessionSharePreference session;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentBeranda = new FragmentBeranda();
                    goToFragment(fragmentBeranda,true);
                    return true;
                case R.id.navigation_rekap:
                    fragmentRekap = new FragmentRekap();
                    goToFragment(fragmentRekap,true);
                    return true;
                case R.id.navigation_profile:
                    fragmentProfil = new FragmentProfil();
                    goToFragment(fragmentProfil,true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        session = new SessionSharePreference(BerandaActivity.this.getApplicationContext());
        String nama = session.getNama();

        fragmentBeranda = new FragmentBeranda();
        goToFragment(fragmentBeranda,true);
    }

    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
