package com.company.zicure.registerkey.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.fragment.AppMenuFragment;

import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class BlocContentActivity extends AppCompatActivity {

    private String urlBloc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_content);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            urlBloc = bundle.getString(VariableConnect.PATH_BLOC);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_bloc, AppMenuFragment.newInstance(urlBloc));
            transaction.commit();
        }
    }
}
