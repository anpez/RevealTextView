package com.antonionicolaspina.revealtextview.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.antonionicolaspina.revealtextview.RevealTextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        ((RevealTextView)findViewById(R.id.text)).setAnimatedText("Sample test");
      }
    }, 5000);
  }
}
