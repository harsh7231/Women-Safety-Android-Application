package com.vinu.cyb_her;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    private static int splash_screen_time = 20000;
    Button skip_main;
    Animation leftamim,bottomanim;
    ImageView imv;
    TextView logo,slogana,sloganb,developer;
    SharedPreferences OnBoardingScreen;
    SharedPreferences login;
    SharedPreferences mainactivity;
    int []images;
    Timer t;
    public static int count=0;
    int[] drawablearray=new int[]{R.drawable.w1,R.drawable.w2,R.drawable.w3,R.drawable.w4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        leftamim = AnimationUtils.loadAnimation(this,R.anim.left_in);
        imv = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogana = findViewById(R.id.textView2);
        developer = findViewById(R.id.textView3);
        slogana.setAnimation(leftamim);
        imv.setAnimation(bottomanim);
        logo.setAnimation(bottomanim);
        developer.setAnimation(bottomanim);
        skip_main = findViewById(R.id.skip_btn_main);
        skip_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,OnBoardingScreen.class);
                startActivity(i);
            }
        });
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() // run on ui thread
                {
                    public void run() {
                        if (count < drawablearray.length) {

                            imv.setImageResource(drawablearray[count]);
                            count = (count + 1) % drawablearray.length;

                        }
                    }
                });
            }
        },1000,1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainactivity = getSharedPreferences("mainactivity",MODE_PRIVATE);
                boolean mainfirsttime = mainactivity.getBoolean("firsttimemainactivity",true);
                OnBoardingScreen = getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = OnBoardingScreen.getBoolean("firstTime",true);
                login = getSharedPreferences("login",MODE_PRIVATE);
                boolean loginisfirsttime = login.getBoolean("firsttimelogin",false);
                if(isFirstTime)
                {
                    SharedPreferences.Editor editor = OnBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,OnBoardingScreen.class);
                    startActivity(intent);
                    finish();
                }
                else if(loginisfirsttime)
                {
                    SharedPreferences.Editor editor = login.edit();
                    editor.putBoolean("firsttimelogin",false);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_screen_time);

    }
}
