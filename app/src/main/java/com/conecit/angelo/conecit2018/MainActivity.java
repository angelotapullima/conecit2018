package com.conecit.angelo.conecit2018;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.conecit.angelo.conecit2018.fragments.ConcursosFragment;
import com.conecit.angelo.conecit2018.fragments.ConferenciasFragment;
import com.conecit.angelo.conecit2018.fragments.CronogramaFragment;
import com.conecit.angelo.conecit2018.fragments.InfoFragment;
import com.conecit.angelo.conecit2018.fragments.TalleresFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cronogramaItem:
                    CronogramaFragment cronogramaFragment = new CronogramaFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,cronogramaFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();

                    return true;
                case R.id.talleresItem:
                    TalleresFragment talleresFragment = new TalleresFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,talleresFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();

                    return true;
                case R.id.conferenciasItem:
                    ConferenciasFragment conferenciasFragment = new ConferenciasFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,conferenciasFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();

                    return true;
                case R.id.concursosItem:
                    ConcursosFragment concursosFragment= new ConcursosFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,concursosFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();

                    return true;
                case R.id.infoItem:
                    InfoFragment infoFragment = new InfoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,infoFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
