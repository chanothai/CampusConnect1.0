package com.company.zicure.registerkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister = null;
    private String deviceID;

    @Bind(R.id.loadKey)
    ProgressBar loadKey;
    @Bind(R.id.imgComplete)
    ImageView imgComplete;
    //Property class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
    }

    @Subscribe
    public void onEvent(BaseResponse registerResponse){
            imgComplete.setImageResource(R.drawable.ic_mood_black);
            loadKey.setVisibility(View.GONE);

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
    }

    @Override
    public void onClick(View v) {
        loadKey.setVisibility(View.VISIBLE);
//        ClientHttp.getInstance(this).generateKey();

        //Test
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }
}
