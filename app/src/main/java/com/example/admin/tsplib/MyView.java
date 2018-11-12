package com.example.admin.tsplib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MyView extends View{
    Context MyContext;
    public MyView(Context context) {
        super(context);
        MyContext=context;
        this.setWillNotDraw(false);
    }

    float[] pts;
    float[] points;

    public void setPts(ArrayList<Integer> cityList,city[] cities,int MainXMLWidth,int MainXMLHeight){
        pts=new float[4*(cities.length-1)];//路线数量*4,画一条线要四个xy坐标
        int biggestX=0,biggestY=0;
        for(int i=0;i<cities.length;i++){
            biggestX=Math.max(biggestX,cities[i].xCoordinate);
            biggestY=Math.max(biggestY,cities[i].yCoordinate);
        }
        for(int i=0;i<cities.length-1;i++){
            pts[4*i]=(float) cities[cityList.get(i)].xCoordinate/(float)biggestX*(float)MainXMLWidth;
            pts[4*i+1]=(float)cities[cityList.get(i)].yCoordinate/(float)biggestY*(float)MainXMLHeight;
            pts[4*i+2]=(float)cities[cityList.get(i+1)].xCoordinate/(float)biggestX*(float)MainXMLWidth;
            pts[4*i+3]=(float)cities[cityList.get(i+1)].yCoordinate/(float)biggestY*(float)MainXMLHeight;
        }
    }
    public void setPoints(city[] cities,int MainXMLWidth,int MainXMLHeight){
        points=new float[cities.length*2];
        int biggestX=0,biggestY=0;
        for(int i=0;i<cities.length;i++){
            biggestX=Math.max(biggestX,cities[i].xCoordinate);
            biggestY=Math.max(biggestY,cities[i].yCoordinate);
        }
        for(int i=0;i<cities.length;i++){
            points[i*2]=(float)cities[i].xCoordinate/(float)biggestX*(float)MainXMLWidth;
            points[i*2+1]=(float)cities[i].yCoordinate/(float)biggestY*(float)MainXMLHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){//参考博客https://www.cnblogs.com/yongdaimi/p/8021965.html#_label0
        Log.i("Debug","onDraw");
        super.onDraw(canvas);

        final int MainXMLWidth = MainActivity.root.getWidth();
        final int MainXMLHeight = MainActivity.root.getHeight();

        final Paint ptspaint=new Paint();
        ptspaint.setAntiAlias(true);//抗锯齿功能
        ptspaint.setColor(Color.RED);  //设置画笔颜色
        ptspaint.setStyle(Paint.Style.FILL);//设置填充样式
        ptspaint.setStrokeWidth(5);
        canvas.drawRGB(255, 255,255);

        setPoints(MainActivity.cities,MainXMLWidth,MainXMLHeight);

        Paint pointpaint=new Paint();
        pointpaint.setColor(Color.BLACK);
        pointpaint.setStyle(Paint.Style.FILL);//设置填充样式
        pointpaint.setStrokeWidth(15);//设置画笔宽度

        if(MainActivity.isLS){
            setPts(MainActivity.CityListUsingLocalSearch,MainActivity.cities,MainXMLWidth,MainXMLHeight);
        }else{
            setPts(MainActivity.CityListUsingSA,MainActivity.cities,MainXMLWidth,MainXMLHeight);
        }

        canvas.drawLines(pts,ptspaint);
        canvas.drawPoints(points,pointpaint);
    }




}
