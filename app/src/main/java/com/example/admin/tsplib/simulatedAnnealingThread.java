package com.example.admin.tsplib;

import android.util.Log;

import java.util.ArrayList;

public class simulatedAnnealingThread  extends Thread {
    @Override
    public void run(){//下面进行SA计算
        city[] cities=MainActivity.cities;
        ArrayList<Integer> cityList=MainActivity.CityListUsingLocalSearch;
        ArrayList<ArrayList<Double>> DistanceAmongCities=MainActivity.DistanceAmongCities;

        Log.i("debug","start SA");
        double T=2000;//初始温度
        double a=0.995;//每次降低的问题速率
        int Markov_length=200;//内部循环次数
        int stepsNotGetNewSolution=0;//记录已经有多少步未得到新解
        int refreshTime=0;//每100次刷新界面

        //cityList是城市的队列，用localSearch初始化
        ArrayList<Integer> result=new ArrayList<>(cityList);
        ArrayList<Integer> resultTemp=new ArrayList<Integer>(cityList);

        double totalDisPre= 0 ;//原来的代价
        double totalDisAfter=0;//变换后的代价

        while(T>0.000000001) {
            for (int j = 0; j < Markov_length; j++) {
                /*(1)对k=1，……，L做第(2)至第5步；*/
                //(2)产生新解s′；
                //随机采用下面两种产生新解的方式
                double chooseCreateNewAnsWay = Math.random();
                if (chooseCreateNewAnsWay >= 0.5) {
                    //二变换法 si= (c1,c2,…,cu,…,cv,…,cn),将cu~cv之间的队列顺序调转
                    ArrayList<Integer> temp = new ArrayList<>(resultTemp);
                    int maxTemp = (int) Math.floor(Math.random() * cities.length);
                    int minTemp = (int) Math.floor(Math.random() * cities.length);
                    int max = Math.max(maxTemp, minTemp);
                    int min = Math.min(maxTemp, minTemp);
                    for (int i = 0; i < max - min; i++) {
                        temp.set(i + min, resultTemp.get(max - i-1));
                    }
                    resultTemp = temp;
                } else {
                    //三变换法,将max~mid和mid~min之间的部分兑对调
                    ArrayList<Integer> temp = new ArrayList<>(resultTemp);
                    int maxTemp = (int) Math.floor(Math.random() * cities.length);
                    int midTemp = (int) Math.floor(Math.random() * cities.length);
                    int minTemp = (int) Math.floor(Math.random() * cities.length);
                    Integer[] num={maxTemp,midTemp,minTemp};
                    int numtemp;
                    for(int x=0;x<num.length-1;x++){//从小到大排序
                        for(int y=0;y<num.length-1;y++){
                            if(num[y]>num[y+1]){
                                numtemp=num[y];
                                num[y]=num[y+1];
                                num[y+1]=numtemp;
                            }
                        }
                    }
                    int max = num[2],min = num[0],mid=num[1];
                    for (int i = 0; i < max - mid; i++) {
                        temp.set(i + min, resultTemp.get(mid + i));
                    }
                    for (int i = 0; i < mid - min; i++) {
                        temp.set(min + max - mid + i, resultTemp.get(min + i));
                    }
                    resultTemp = temp;
                }
                //(3)计算增量cost=cost(s′)-cost(s)，其中cost(s)为评价函数；
                totalDisAfter = city.getTotalDisByCitiesArr(cities, resultTemp, DistanceAmongCities);
                totalDisPre = city.getTotalDisByCitiesArr(cities, result, DistanceAmongCities);
                //(4)若t<0则接受s′作为新的当前解，否则以概率exp(-t′/T)接受s′作为新的当前解；
                if (totalDisAfter <= totalDisPre) {
                    result = resultTemp;
                    stepsNotGetNewSolution=0;
                    MainActivity.CityListUsingSA=result;
                    if(refreshTime%500==0){
                        final double finalT = T;
                        Runnable runnable = new Runnable()  {
                            @Override
                            public void run() {
                                MainActivity.SAAnswer=city.getTotalDisByCitiesArr(MainActivity.cities,MainActivity.CityListUsingSA,MainActivity.DistanceAmongCities);
                                Log.i("debug","更新 SA算法路径长度："+MainActivity.SAAnswer);
                                Log.i("debug","T: "+ finalT);
                                MainActivity.detail.setText("SA算法路径长度："+(int)MainActivity.SAAnswer);
                                MainActivity.myView.postInvalidate();
                            }
                        };
                        MainActivity.uiHandler.post(runnable);
                    }

                    //Log.i("debug","better solution: "+totalDisAfter);
                    refreshTime++;
                } else {
                    double ran = Math.random();
                    double distanceChanged = totalDisAfter - totalDisPre;
                    double acceptRate = 1 / (1 + Math.exp(distanceChanged / T));//Math.exp(distanceChanged/T)>=1,acceptRate<=1/2
                    if (ran < acceptRate) {
                        result = resultTemp;
                        stepsNotGetNewSolution=0;
                    }else{//不接受
                        stepsNotGetNewSolution++;
                        resultTemp=result;
                    }
                }
            }
            //内循环结束
            T = a * T;

            //(5)如果满足终止条件则输出当前解作为最优解，结束程序。终止条件通常取为连续若干个新解都没有被接受时终止算法；
            //(6)T逐渐减少，且T趋于0，结束外层循环。
            if(stepsNotGetNewSolution>1000){
                Log.i("debug","stepsNotGetNewSolution>1000");
                break;
            }
        }
        Log.i("debug","finish SA");


    }
}
