package com.example.admin.tsplib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences sharedPref ;
    public static SharedPreferences.Editor editor;
    public static String filePath="solution";
    public static FrameLayout root;
    public static ArrayList<Integer> CityListUsingSA;
    public static ArrayList<Integer> CityListUsingLocalSearch;
    public static city[] cities;
    public static Button btn,startBtn;
    public static TextView detail;
    public static boolean isLS;
    public static double LSAnswer,SAAnswer;
    public static MyView myView;
    public static ArrayList<ArrayList<Double>> DistanceAmongCities;
    public static Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cities=city.getInitCities();
        btn=findViewById(R.id.changeLSorSA);
        startBtn=findViewById(R.id.startBut);
        detail=findViewById(R.id.LSorSA);
        isLS=true;
        sharedPref = MainActivity.this.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        editor= sharedPref.edit();
        city[] citiesForLocalSearch;
        root=(FrameLayout)findViewById(R.id.root);



        citiesForLocalSearch=city.getInitCities();
        DistanceAmongCities=city.calculateDistanceAmongCities(citiesForLocalSearch);

        CityListUsingLocalSearch=localSearch.getCityListUsingLocalSearch(citiesForLocalSearch,citiesForLocalSearch[0],DistanceAmongCities);
        LSAnswer=city.getTotalDisByCitiesArr(citiesForLocalSearch,CityListUsingLocalSearch,DistanceAmongCities);


        CityListUsingSA=new ArrayList<>(CityListUsingLocalSearch);

        myView=new MyView(MainActivity.this);
        //下面在手机界面画图
        root.addView(myView);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulatedAnnealingThread sThread=new simulatedAnnealingThread();
                sThread.start();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLS){//跳转至SA
                    btn.setText("跳转至LS");
                    detail.setText("SA算法路径长度："+(int)MainActivity.SAAnswer);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            myView.postInvalidate();
                        }
                    }).start();
                    isLS=false;
                }else{
                    btn.setText("跳转至AS");
                    detail.setText("LS算法路径长度："+(int)MainActivity.LSAnswer);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            myView.postInvalidate();
                        }
                    }).start();
                    isLS=true;
                }
            }
        });






    }


}
