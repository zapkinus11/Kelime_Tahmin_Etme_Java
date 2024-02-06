package com.kelimetamamla;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private boolean arabaModuSecildiMi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void anasayfaButon(View v) {

        int viewId = v.getId();
        if (viewId == R.id.btnBasla) {
            // SharedPreferences'tan seçilen modu al
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String selectedMode = preferences.getString("selected_mode", "");

            // Seçilen modu kontrol et ve ilgili aktiviteyi başlat
            if ("Araba".equals(selectedMode)) {
                intent = new Intent(this, ArabaActivity.class);
            } else if ("Hayvanlar".equals(selectedMode)) {
                intent = new Intent(this, HayvanActivity.class);
            } else {
                intent = new Intent(this, OyunActivity.class);
            }
            startActivity(intent);
        } else if (viewId == R.id.btnAyarlar) {
            aktiviteyeGec("Ayarlar");
        } else if (viewId == R.id.btnCikis) {
            cikisYap();
        }
    }

    private void aktiviteyeGec(String aktiviteIsmi) {
        if (aktiviteIsmi.equals("Başla"))
            intent = new Intent(this, OyunActivity.class);
        else if (aktiviteIsmi.equals("Araba")) {
            intent = new Intent(this, ArabaActivity.class);
        } else
            intent = new Intent(this, AyarlarActivity.class);

        startActivity(intent);
    }
    private void cikisYap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Oyunu kapatmak istediğine emin misin?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity(); // uygulama kapat
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss(); // çıkış iptal etme
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}