package com.harun.timertaskkullanimi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

// https://harun.xyz/

public class MainActivity extends AppCompatActivity {

    private Timer sureTimer;
    private Handler sureHandler;
    private int sayac = 0;

    private TextView sure;
    private Button baslat, duraklat, durdurSifirla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sure = findViewById(R.id.sure);
        baslat = findViewById(R.id.baslat);
        duraklat = findViewById(R.id.duraklat);
        durdurSifirla = findViewById(R.id.durdurSifirla);

        baslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureSayac();
            }
        });

        duraklat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureSayacDurdur();
            }
        });

        durdurSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureSayacDurdur();
                sayac = 0;
                sure.setText(String.valueOf(sayac));
            }
        });

    }

    private void sureSayacDurdur(){

        // Timer çalışıyorsa öncelikle ondan çıkış yapıyoruz
        if(sureTimer != null) sureTimer.cancel();

        // Ardından Handler ve Timerı sıfıra eşitliyoruz
        sureHandler = null;
        sureTimer = null;

    }

    // Süreyi sayan sayacımız
    private void sureSayac(){

        // Eğer çalışan bir süre sayacı varsa onu kapatıyoruz
        sureSayacDurdur();

        sureHandler = new Handler();
        sureTimer = new Timer();

        TimerTask sureTimerTask = new TimerTask() {
            @Override
            public void run() {

                sureHandler.post(new Runnable() {
                    @Override
                    public void run() { //Her saniye girilen metot

                        sayac++;

                        // Herhangi bir hata oluşmaması için runOnUiThread içinde başlatıyoruz
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Süreyi her defasında sure bileşenimizde gösteriyoruz
                                sure.setText(String.valueOf(sayac));

                            }
                        });

                    }
                });

            }
        };

        // İlk parametre TimerTaskımızı içeriyor, ikincisi başlarken oluşacak gecikme süresi
        // Üçüncü parametre kaç saniyede bir yenileneceğini soruyor biz 1000 milisaniye yani 1 saniye yaptık
        sureTimer.schedule(sureTimerTask,0,1000); //Saniyede bir kez girilecek

    }

}
