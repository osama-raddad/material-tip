package com.github.fcannizzaro.materialtip.sample;

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
                .withBackgroundRes(R.color.tip_background)
                .withTextColorRes(R.color.tip_text)
                .withTitleColorRes(R.color.tip_title)
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
