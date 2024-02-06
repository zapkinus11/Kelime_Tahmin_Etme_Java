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

public class OyunActivity extends AppCompatActivity {
    private TextView txtBilgi, txtSehirYazdir, puanTablosu;
    private EditText tahminEt;
    private String [] iller = {"Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman", "Bayburt",
            "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir",
            "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Iğdır", "Isparta", "İstanbul", "İzmir", "Kahramanmaraş", "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kırıkkale",
            "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Mardin", "Mersin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Şanlıurfa",
            "Siirt", "Sinop", "Sivas", "Şırnak", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"};
    private Random rndIl, rndHarf;
    private int rndIlNumber, rndHarfNumber, baslangictakiHarfSayisi;
    private String gelenIl, ilDegeri="", editTxtGelenTahmin;
    private ArrayList<Character> sehirHarfleri;
    private int maximumPuan = 100, azaltilacakPuan, toplamPuan = 0, bolumToplamPuan = 0, artanPuan = 12, puanAzalt=4, tahminPuanDusurme=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);
        txtBilgi=(TextView) findViewById(R.id.txtViewSoru);
        txtSehirYazdir=(TextView) findViewById(R.id.txtViewSehir);
        puanTablosu=(TextView)findViewById(R.id.guncelPoint);
        tahminEt=(EditText) findViewById(R.id.txtTahmin);

        rndHarf = new Random();
        rndIl = new Random();
        rndIlNumber = rndIl.nextInt(iller.length); //random şehir numarası için şehirlerin tümünün uzunluğunu alır.
        gelenIl = iller[rndIlNumber];
        System.out.println(gelenIl); //logcat'a hangi il geldiyse cevabını yazar;

        txtBilgi.setText(gelenIl.length()+" Harfli ilimiz");

        for(int n = 0; n<gelenIl.length();n++){
            if (n < gelenIl.length() - 1) {
                ilDegeri += "_ ";
            } else
                ilDegeri += "_";
        }
        txtSehirYazdir.setText(ilDegeri);
        sehirHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray()) {
            sehirHarfleri.add(c);
        }
    }

    //HARF AL BUTONUNA BASILDIĞINDA
    public void btnOyunHarfi (View v ){
        if (sehirHarfleri.size() > 0){
            randomHarfAl();
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
        else {
            Toast.makeText(getApplicationContext(), "Harf kalmadı! Yeni oyuna başlanıyor!", Toast.LENGTH_SHORT).show();
            System.out.println("Harf bitti! Yeni bir şehir getiriliyor...");

            //yeni şehir getirme metodu oluştur ve ekrana getir!
            yeniRandomSehir();

            // Puanı güncelleme
            puanTablosu.setTextColor(Color.RED);
            toplamPuan -= puanAzalt;
            puanGetir();
        }
    }

    // TAHMİN ET BUTONUNA BASILDIĞINDA
    public void btnOyunTahmini (View v ){
        editTxtGelenTahmin = tahminEt.getText().toString();

        if(!TextUtils.isEmpty(editTxtGelenTahmin)){
            if(editTxtGelenTahmin.equalsIgnoreCase(gelenIl)){
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
        ilDegeri = "";

        rndIl = new Random();
        rndIlNumber = rndIl.nextInt(iller.length);
        gelenIl = iller[rndIlNumber];
        System.out.println(rndIlNumber + " = " + gelenIl);
        txtBilgi.setText(gelenIl.length() + " Harfli İlimiz");

        if (gelenIl.length() >= 5 && gelenIl.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenIl.length() >= 8 && gelenIl.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenIl.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenIl.length(); i++) {
            if (i < gelenIl.length() - 1)
                ilDegeri += "_ ";
            else
                ilDegeri += "_";
        }

        txtSehirYazdir.setText(ilDegeri);
        sehirHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray())
            sehirHarfleri.add(c);

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
        if (txtSehirYazdir.getText().toString().contains("_")) { // '_' karakteri hala varsa devam et
            rndHarfNumber = rndHarf.nextInt(sehirHarfleri.size());
            String[] txtHarfler = txtSehirYazdir.getText().toString().split(" ");
            char[] gelenIlHarfler = gelenIl.toCharArray();

            for (int i = 0; i < gelenIl.length(); i++) {
                if (i < txtHarfler.length && i < gelenIlHarfler.length &&
                        txtHarfler[i].equals("_") && gelenIlHarfler[i] == sehirHarfleri.get(rndHarfNumber)) {

                    txtHarfler[i] = String.valueOf(sehirHarfleri.get(rndHarfNumber));
                    ilDegeri = "";
                    for (int j = 0; j < gelenIl.length(); j++) {
                        if (j == i)
                            ilDegeri += txtHarfler[j] + " ";
                        else if (j < gelenIl.length() - 1)
                            ilDegeri += txtHarfler[j] + " ";
                        else
                            ilDegeri += txtHarfler[j];
                    }
                    break;
                }
            }
            txtSehirYazdir.setText(ilDegeri);
            sehirHarfleri.remove(rndHarfNumber);
        } else {
            //oyunu bitir yada yeni bir kelime başlat
        }
    }
    private void yeniRandomSehir(){
        ilDegeri = "";

        rndIl = new Random();
        rndIlNumber = rndIl.nextInt(iller.length);
        gelenIl = iller[rndIlNumber];
        System.out.println(rndIlNumber + " = " + gelenIl);
        txtBilgi.setText(gelenIl.length() + " Harfli İlimiz");

        if (gelenIl.length() >= 5 && gelenIl.length() <= 7)
            baslangictakiHarfSayisi = 1;
        else if (gelenIl.length() >= 8 && gelenIl.length() < 10)
            baslangictakiHarfSayisi = 2;
        else if (gelenIl.length() >= 10)
            baslangictakiHarfSayisi = 3;
        else
            baslangictakiHarfSayisi = 0;

        for (int i = 0; i < gelenIl.length(); i++) {
            if (i < gelenIl.length() - 1)
                ilDegeri += "_ ";
            else
                ilDegeri += "_";
        }
        txtSehirYazdir.setText(ilDegeri);
        sehirHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray())
            sehirHarfleri.add(c);

        for (int c = 0; c < baslangictakiHarfSayisi; c++)
            randomHarfAl();
    }
    private void puanGetir() {
        puanTablosu.setText("Puanınız: " + toplamPuan);
    }
}