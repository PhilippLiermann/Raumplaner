package com.example.philippliermann.raumplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Activity2 extends AppCompatActivity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        result = (TextView)findViewById(R.id.result);
        getWebsite();
    }

    private void getWebsite(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://cis.technikum-wien.at/cis/infoterminal/index.php?raumtyp_kurzbz=HS&standort_id=").get();
                    Elements gruen = doc.getElementsByClass("gruen_mitteText");
                    builder.append(gruen).append("\n");
                    Elements orange = doc.getElementsByClass("orange_mitteText");
                    builder.append(orange).append("\n");
                    Elements rot = doc.getElementsByClass("rot_mitteText");
                    builder.append(rot).append("\n");

                } catch (IOException e){
                    builder.append("error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();

    }
}
