package com.github.fcannizzaro.materialtip.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.fcannizzaro.materialtip.MaterialTip;
import com.github.fcannizzaro.materialtip.util.ButtonListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MaterialTip tip = (MaterialTip) findViewById(R.id.tip);

        tip.withTitle("Ok Google")
                .withText("Something!")
                .withPositive("save")
                .withNegative("discard")
                .withBackground(Color.parseColor("#363636"))
                .withTextColor(Color.parseColor("#f5f5f5"))
                .withTitleColor(Color.WHITE)
                .withButtonListener(new ButtonListener() {

                    @Override
                    public void onPositive(MaterialTip tip) {
                        System.out.println("positive");
                    }

                    @Override
                    public void onNegative(MaterialTip tip) {
                        System.out.println("negative");
                    }
                });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.toggle();
            }
        });

    }
}
