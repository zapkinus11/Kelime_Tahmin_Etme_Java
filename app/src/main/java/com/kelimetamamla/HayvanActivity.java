package com.kelimetamamla;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class HayvanActivity extends AppCompatActivity {
    private TextView txtBilgi, txtHayvanYazdir, puanTablosu;
    private EditText tahminEt;
    private String [] hayvanlar = {"Akbaba", "Antilop","Antelope","Arı","Aslan","At","Ayı","Balık","Balina","Baykuş",
            "Boğa","Böcek","Çakal","Çekirge","Çita","Denizanası","Deve","Domuz","Eşşek","Fare","Fil","Güvercin","Hamster","Hindi","Horoz","İmpala",
            "İnek","Jaguar","Kanguru","Kaplan","Kaplumbağa","Karga","Karınca","Karıncayiyen","Kartal","Keçi","Kedi","Koala",
            "Koyun","KöpekBalığı","Köpek","Köstebek","Kunduz","Kurbağa","Kurt","Kuş","Kuzu","Martı","Maymun","Ördek","Örümcek","Papağan","Pelikan",
            "Penguen","Rakun","Serçe","Sivrisinek","Şahin","Tavşan","Tavuk","Tilki","Timsah","Yarasa","Yılan","Yunus","Zebra","Zürafa"};
    private Random rndHayvan, rndHarf;
    private int rndHayvanNumber, rndHarfNumber, baslangictakiHarfSayisi;
    private String gelenHayvan, hayvanDegeri="", editTxtGelenTahmin;
    private ArrayList<Character> hayvanHarfleri;
    private int azaltilacakPuan, toplamPuan = 0, bolumToplamPuan = 0, artanPuan = 12, puanAzalt=4, tahminPuanDusurme=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan);
        txtBilgi=(TextView) findViewById(R.id.txtHayvanSoru);
        txtHayvanYazdir=(TextView) findViewById(R.id.txtViewHayvan);
        puanTablosu=(TextView)findViewById(R.id.hayvanPuan);
        tahminEt=(EditText) findViewById(R.id.txtHayvanTahmin);

        rndHarf = new Random();
        rndHayvan = new Random();
        rndHayvanNumber = rndHayvan.nextInt(hayvanlar.length); //random hayvan numarası için hayvanların tümünün uzunluğunu alır.
        gelenHayvan = hayvanlar[rndHayvanNumber];
        System.out.println(gelenHayvan); //logcat'a hangi hayvan geldiyse cevabını yazar;

        txtBilgi.setText(gelenHayvan.length()+" Harfli hayvan adı");

        for(int n = 0; n<gelenHayvan.length();n++){
            if (n < gelenHayvan.length() - 1) {
                hayvanDegeri += "_ ";
            } else
                hayvanDegeri += "_";
        }
        txtHayvanYazdir.setText(hayvanDegeri);
        hayvanHarfleri = new ArrayList<>();

        for (char c : gelenHayvan.toCharArray()) {
            hayvanHarfleri.add(c);
        }
    }
    //HARF AL BUTONUNA BASILDIĞINDA
    public void harfHayvan (View v ){
        if (hayvanHarfleri.size() > 0){
            randomHarfAl();
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
        else {
            Toast.makeText(getApplicationContext(), "Harf kalmadı! Yeni oyuna başlanıyor!", Toast.LENGTH_SHORT).show();
            System.out.println("Harf bitti! Yeni bir hayvan getiriliyor...");

            //yeni hayvan getirme metodu oluştur ve ekrana getir!
            yeniRandomHayvan();

            // Puanı güncelleme
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
    }

    // TAHMİN ET BUTONUNA BASILDIĞINDA
    public void tahminHayvan (View v ){
        editTxtGelenTahmin = tahminEt.getText().toString();

        if(!TextUtils.isEmpty(editTxtGelenTahmin)){
            if(editTxtGelenTahmin.equalsIgnoreCase(gelenHayvan)){
                bolumToplamPuan += artanPuan;
                Toast.makeText(getApplicationContext(),"Doğru Tahminde Bulundunuz!", Toast.LENGTH_SHORT).show();
                tahminEt.setText("");
                randomDegerleriBelirle();
                puanTablosu.setTextColor(Color.GREEN);
                puanGetir();
            }
            else{
                Toast.makeText(getApplicationContext(),"Yanlış tahmin!", Toast.LENGTH_SHORT).show();
                System.out.println("Yanlış Tahminde Bulundun!");
                puanTablosu.setTextColor(Color.RED);
                toplamPuan -= puanAzalt;
                puanGetir();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Tahmin değeri boş olamaz!", Toast.LENGTH_SHORT).show();
            System.out.println("Tahmin Değeri Boş Olamaz.");
            puanTablosu.setTextColor(Color.YELLOW);
            toplamPuan -= tahminPuanDusurme;
            puanGetir();
        }
    }

    //RANDOM DEĞER BELİRLEDİĞİMİZ METOD
    private void randomDegerleriBelirle (){
        hayvanDegeri = "";

        rndHayvan = new Random();
        rndHayvanNumber = rndHayvan.nextInt(hayvanlar.length);
        gelenHayvan = hayvanlar[rndHayvanNumber];
        System.out.println(rndHayvanNumber + " = " + gelenHayvan);
        txtBilgi.setText(gelenHayvan.length() + " harfli hayvan adı");

        if (gelenHayvan.length() >= 5 && gelenHayvan.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenHayvan.length() >= 8 && gelenHayvan.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenHayvan.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenHayvan.length(); i++) {
            if (i < gelenHayvan.length() - 1)
                hayvanDegeri += "_ ";
            else
                hayvanDegeri += "_";
        }

        txtHayvanYazdir.setText(hayvanDegeri);
        hayvanHarfleri = new ArrayList<>();

        for (char c : gelenHayvan.toCharArray())
            hayvanHarfleri.add(c);

        for (int c = 0; c < baslangictakiHarfSayisi; c++)
            randomHarfAl();

        //Puan hesabı alanı
        azaltilacakPuan = toplamPuan - 5;
        toplamPuan += artanPuan;
        puanTablosu.setTextColor(Color.WHITE);
        puanGetir();
    }
    //RANDOM HARF ALINAN METOD
    private void randomHarfAl() {
        if (txtHayvanYazdir.getText().toString().contains("_")) { // '_' karakteri hala varsa devam et
            rndHarfNumber = rndHarf.nextInt(hayvanHarfleri.size());
            String[] txtHarfler = txtHayvanYazdir.getText().toString().split(" ");
            char[] gelenHayvanHarfler = gelenHayvan.toCharArray();

            for (int i = 0; i < gelenHayvan.length(); i++) {
                if (i < txtHarfler.length && i < gelenHayvanHarfler.length &&
                        txtHarfler[i].equals("_") && gelenHayvanHarfler[i] == hayvanHarfleri.get(rndHarfNumber)) {

                    txtHarfler[i] = String.valueOf(hayvanHarfleri.get(rndHarfNumber));
                    hayvanDegeri = "";
                    for (int j = 0; j < gelenHayvan.length(); j++) {
                        if (j == i)
                            hayvanDegeri += txtHarfler[j] + " ";
                        else if (j < gelenHayvan.length() - 1)
                            hayvanDegeri += txtHarfler[j] + " ";
                        else
                            hayvanDegeri += txtHarfler[j];
                    }
                    break;
                }
            }
            txtHayvanYazdir.setText(hayvanDegeri);
            hayvanHarfleri.remove(rndHarfNumber);
        } else {
            //oyunu bitir yada yeni bir kelime başlat
        }
    }
    private void yeniRandomHayvan(){
        hayvanDegeri = "";

        rndHayvan = new Random();
        rndHayvanNumber = rndHayvan.nextInt(hayvanlar.length);
        hayvanDegeri = hayvanlar[rndHayvanNumber];
        System.out.println(rndHayvanNumber + " = " + gelenHayvan);
        txtBilgi.setText(gelenHayvan.length() + " Harfli hayvan adı");

        if (gelenHayvan.length() >= 5 && gelenHayvan.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenHayvan.length() >= 8 && gelenHayvan.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenHayvan.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenHayvan.length(); i++) {
            if (i < gelenHayvan.length() - 1)
                hayvanDegeri += "_ ";
            else
                hayvanDegeri += "_";
        }
        txtHayvanYazdir.setText(hayvanDegeri);
        hayvanHarfleri = new ArrayList<>();

        for (char c : gelenHayvan.toCharArray())
            hayvanHarfleri.add(c);

        for (int c = 0; c < baslangictakiHarfSayisi; c++)
            randomHarfAl();
    }
    private void puanGetir() {
        puanTablosu.setText("Puanınız: " + toplamPuan);
    }
}