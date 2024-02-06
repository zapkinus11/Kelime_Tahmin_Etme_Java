package com.kelimetamamla;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class AyarlarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        Button modSecimiButton = findViewById(R.id.btnMod);
        modSecimiButton.setOnClickListener(view -> showModeSelectionDialog());

        //BUTONA BASINCA GOOGLEDAN LINKE YONLENDIRME
        Button btnGitHub = findViewById(R.id.btnGithub);
        btnGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/zapkinus11";
                Intent a = new Intent(Intent.ACTION_VIEW);
                a.setData(Uri.parse(url));
                startActivity(a);
            }
        });
    }
    private void showModeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mod Seçimi");
        builder.setItems(new CharSequence[]{"- Şehir", "- Araba", "- Hayvanlar"}, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    saveModeSelection("Şehir");
                    break;
                case 1:
                    saveModeSelection("Araba");
                    break;
                case 2:
                    saveModeSelection("Hayvanlar");
                    break;
            }
        });
        builder.show();
    }
    private void saveModeSelection(String selectedMode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selected_mode", selectedMode);
        editor.apply();
    }
}