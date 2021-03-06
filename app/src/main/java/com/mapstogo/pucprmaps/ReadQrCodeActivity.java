package com.mapstogo.pucprmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReadQrCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr_code);
        configImageViewReadCamera();
    }

    private void configImageViewReadCamera() {
        ImageView view = findViewById(R.id.imageViewReadQrCode);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intentGoToMainActivity = new Intent(this, MainActivity.class);
        intentGoToMainActivity.putExtra("imgIntent", R.drawable.bloco_azul_terrreo_destino_escada);
        startActivity(intentGoToMainActivity);
    }
}