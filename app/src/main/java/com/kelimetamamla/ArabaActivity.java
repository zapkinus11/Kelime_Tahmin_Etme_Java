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

public class ArabaActivity extends AppCompatActivity {
    private TextView txtBilgi, txtMarkaYazdir, puanTablosu;
    private EditText tahminEt;
    private String [] arabalar = {"Mercedes", "BMW", "Audi", "Toyota", "Honda", "Ford", "Volkswagen", "Fiat", "Renault", "Hyundai", "Kia", "Chevrolet", "Nissan", "Peugeot", "Skoda",
            "Mazda", "Volvo", "Subaru", "Suzuki", "Togg", "Jeep", "Tesla", "Porsche", "Jaguar", "Lexus", "Land Rover", "Maserati", "Ferrari", "Bentley", "Rolls Royce", "Bugatti", "Lamborghini",
            "AstonMartin", "AlfaRomeo", "MiniCooper", "Cadillac", "Chrysler", "Dodge", "Infiniti", "Lincoln", "Lotus", "McLaren", "Opel", "Pagani", "Seat", "Smart", "Tata", "Tesla", "Volvo"};
    private Random rndMarka, rndHarf;
    private int rndMarkaNumber, rndHarfNumber, baslangictakiHarfSayisi;
    private String gelenMarka, markaDegeri="", editTxtGelenTahmin;
    private ArrayList<Character> markaHarfleri;
    private int azaltilacakPuan, toplamPuan = 0, bolumToplamPuan = 0, artanPuan = 12, puanAzalt=4, tahminPuanDusurme=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_araba);
        txtBilgi=(TextView) findViewById(R.id.txtSoruA);
        txtMarkaYazdir=(TextView) findViewById(R.id.txtViewAraba);
        puanTablosu=(TextView)findViewById(R.id.guncelPuan);
        tahminEt=(EditText) findViewById(R.id.txtTahminA);

        rndHarf = new Random();
        rndMarka = new Random();
        rndMarkaNumber = rndMarka.nextInt(arabalar.length); //random marka numarası için markaların tümünün uzunluğunu alır.
        gelenMarka = arabalar[rndMarkaNumber];
        System.out.println(gelenMarka); //logcat'a hangi marka geldiyse cevabını yazar;

        txtBilgi.setText(gelenMarka.length()+" Harfli araba markası");

        for(int n = 0; n<gelenMarka.length();n++){
            if (n < gelenMarka.length() - 1) {
                markaDegeri += "_ ";
            } else
                markaDegeri += "_";
        }
        txtMarkaYazdir.setText(markaDegeri);
        markaHarfleri = new ArrayList<>();

        for (char c : gelenMarka.toCharArray()) {
            markaHarfleri.add(c);
        }
    }
    //HARF AL BUTONUNA BASILDIĞINDA
    public void harfAraba (View v ){
        if (markaHarfleri.size() > 0){
            randomHarfAl();
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
        else {
            Toast.makeText(getApplicationContext(), "Harf kalmadı! Yeni oyuna başlanıyor!", Toast.LENGTH_SHORT).show();
            System.out.println("Harf bitti! Yeni bir marka getiriliyor...");

            //yeni marka getirme metodu oluştur ve ekrana getir!
            yeniRandomMarka();

            // Puanı güncelleme
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
    }

    // TAHMİN ET BUTONUNA BASILDIĞINDA
    public void tahminAraba (View v ){
        editTxtGelenTahmin = tahminEt.getText().toString();

        if(!TextUtils.isEmpty(editTxtGelenTahmin)){
            if(editTxtGelenTahmin.equalsIgnoreCase(gelenMarka)){
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
        markaDegeri = "";

        rndMarka = new Random();
        rndMarkaNumber = rndMarka.nextInt(arabalar.length);
        gelenMarka = arabalar[rndMarkaNumber];
        System.out.println(rndMarkaNumber + " = " + gelenMarka);
        txtBilgi.setText(gelenMarka.length() + " harfli araba markası");

        if (gelenMarka.length() >= 5 && gelenMarka.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenMarka.length() >= 8 && gelenMarka.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenMarka.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenMarka.length(); i++) {
            if (i < gelenMarka.length() - 1)
                markaDegeri += "_ ";
            else
                markaDegeri += "_";
        }

        txtMarkaYazdir.setText(markaDegeri);
        markaHarfleri = new ArrayList<>();

        for (char c : gelenMarka.toCharArray())
            markaHarfleri.add(c);

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
        if (txtMarkaYazdir.getText().toString().contains("_")) { // '_' karakteri hala varsa devam et
            rndHarfNumber = rndHarf.nextInt(markaHarfleri.size());
            String[] txtHarfler = txtMarkaYazdir.getText().toString().split(" ");
            char[] gelenMarkaHarfler = gelenMarka.toCharArray();

            for (int i = 0; i < gelenMarka.length(); i++) {
                if (i < txtHarfler.length && i < gelenMarkaHarfler.length &&
                        txtHarfler[i].equals("_") && gelenMarkaHarfler[i] == markaHarfleri.get(rndHarfNumber)) {

                    txtHarfler[i] = String.valueOf(markaHarfleri.get(rndHarfNumber));
                    markaDegeri = "";
                    for (int j = 0; j < gelenMarka.length(); j++) {
                        if (j == i)
                            markaDegeri += txtHarfler[j] + " ";
                        else if (j < gelenMarka.length() - 1)
                            markaDegeri += txtHarfler[j] + " ";
                        else
                            markaDegeri += txtHarfler[j];
                    }
                    break;
                }
            }
            txtMarkaYazdir.setText(markaDegeri);
            markaHarfleri.remove(rndHarfNumber);
        } else {
            //oyunu bitir yada yeni bir kelime başlat
        }
    }
    private void yeniRandomMarka(){
        markaDegeri = "";

        rndMarka = new Random();
        rndMarkaNumber = rndMarka.nextInt(arabalar.length);
        gelenMarka = arabalar[rndMarkaNumber];
        System.out.println(rndMarkaNumber + " = " + gelenMarka);
        txtBilgi.setText(gelenMarka.length() + " Harfli araba markası");

        if (gelenMarka.length() >= 5 && gelenMarka.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenMarka.length() >= 8 && gelenMarka.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenMarka.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenMarka.length(); i++) {
            if (i < gelenMarka.length() - 1)
                markaDegeri += "_ ";
            else
                markaDegeri += "_";
        }
        txtMarkaYazdir.setText(markaDegeri);
        markaHarfleri = new ArrayList<>();

        for (char c : gelenMarka.toCharArray())
            markaHarfleri.add(c);

        for (int c = 0; c < baslangictakiHarfSayisi; c++)
            randomHarfAl();
    }
    private void puanGetir() {
        puanTablosu.setText("Puanınız: " + toplamPuan);
    }
}