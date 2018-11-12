package com.example.admin.tsplib;

import java.util.ArrayList;

public class city {
    int num;
    int xCoordinate;
    int yCoordinate;
    boolean isVisited;

    city(){
        this.num=0;
        this.xCoordinate=0;
        this.yCoordinate=0;
        isVisited=false;
    }

    city(int num,int xCoordinate,int yCoordinate){
        this.num=num;
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
        isVisited=false;
    }

    public static city[] getInitCities(){
        city[] cities=new city[280];
        int[] xCoordinateArr={288 ,288 ,256 ,270 ,246 ,256 ,228 ,236 ,220 ,228 ,204 ,212 ,188 ,196 ,188 ,196 ,164 ,172 ,148 ,156 ,148 ,140 ,172 ,164 ,140 ,156 ,124 ,132 ,104 ,116 ,104 ,104 , 80 , 90 , 64 , 64 , 56 , 56 , 56 , 56 , 56 , 56 , 40 , 56 , 40 , 40 , 40 , 40 , 40 , 40 , 32 , 32 , 32 , 32 , 32 , 32 , 32 , 32 , 56 , 40 , 48 , 56 , 32 , 40 , 24 , 32 , 16 , 16 ,  8 ,  8 ,  8 ,  8 ,  8 ,  8 , 16 ,  8 ,  8 ,  8 , 32 , 24 , 32 , 32 , 32 , 32 , 40 , 32 , 40 , 40 , 44 , 40 , 44 , 44 , 24 , 32 , 16 , 16 , 32 , 24 ,  56, 44 , 56 , 56 , 56 , 56 , 72 , 64 , 56 , 72 , 56 , 48 , 48 , 56 , 56 , 48 , 48 , 56 , 56 , 56 ,104 ,104 ,104 ,104 ,104 ,104 ,116 ,104 ,132 ,124 ,140 ,132 ,156 ,148 ,172 ,164 ,172 ,172 ,172 ,172 ,180 ,172 ,180 ,180 ,180 ,180 ,172 ,172 ,172 ,172 ,148 ,164 ,124 ,124 ,124 ,124 ,124 ,124 ,104 ,104 ,104 ,104 ,104 ,104 ,104 ,104 , 92 ,104 , 72 , 80 , 72 , 64 , 80 , 80 , 88 , 80 ,124 ,104 ,132 ,124 ,132 ,140 ,124 ,124 ,124 ,124 ,132 ,124 ,120 ,124 ,136 ,128 ,162 ,148 ,172 ,156 ,180 ,180 ,172 ,172 ,180 ,172 ,188 ,180 ,204 ,196 ,220 ,212 ,228 ,228 ,236 ,236 ,228 ,236 ,236 ,228 ,228 ,236 ,236 ,228 ,228 ,236 ,236 ,228 ,260 ,252 ,260 ,260 ,260 ,260 ,260 ,260 ,276 ,276 ,276 ,276 ,284 ,284 ,284 ,284 ,284 ,284 ,288 ,284 ,276 ,280 ,276 ,276 ,260 ,268 ,260 ,252 ,236 ,260 ,228 ,228 ,236 ,236 ,228 ,228 ,228 ,228 ,212 ,220 ,196 ,204 ,180 ,188 ,180 ,180 ,180 ,180 ,204 ,196 ,220 ,212 ,236 ,228 ,252 ,246 ,280 ,260 ,};
        int[] yCoordinateArr={149,129,133,141,157,157,169,169,161,169,169,169,169,169,161,145,145,145,145,145,145,169,169,169,169,169,169,169,161,153,161,169,165,157,157,165,169,161,153,145,137,129,121,121,129,137,145,153,161,169,169,161,153,145,137,129,121,113,113,113,105,99,99,97,89,89,97,109,109,97,89,81,73,65,57,57,49,41,45,41,49,57,65,73,81,83,73,63,51,43,35,27,25,25,25,17,17,17,11,9  ,17,25,33,41,41,41,49,49,51,57,65,63,73,73,81,83,89,97,97,105,113,121,129,137,145,145,145,145,137,137,137,137,137,125,117,109,101,93,85,85,77,69,61,53,53,61,69,77,81,85,85,93,109,125,117,101,89,81,73,65,49,41,33,25,17,9  ,9  ,9  ,21,25,25,25,41,49,57,69,77,81,65,61,61,53,45,37,29,21,21,9  ,9  ,9  ,9  ,9  ,25,21,21,29,29,37,45,45,37,41,49,57,65,73,69,77,77,69,61,61,53,53,45,45,37,37,29,29,21,21,21,29,37,45,53,61,69,77,77,69,61,53,53,61,69,77,85,93,101,109,109,101,93,85,97,109,101,93,85,85,85,93,93,101,101,109,117,125,125,117,109,101,93,93,101,109,117,125,145,145,145,145,145,145,141,125,129,133};

        for(int i=0;i<280;i++){
            cities[i]=new city(i,xCoordinateArr[i],yCoordinateArr[i]);
        }
        return cities;
    }

    /*用于存储两个城市之间的相互距离*/
    public static ArrayList<ArrayList<Double>> calculateDistanceAmongCities(city[] cities){
        ArrayList<ArrayList<Double>> DistanceAmongCities=new ArrayList<ArrayList<Double>>();
        for(int i=0;i<cities.length;i++){
            ArrayList<Double> arrTemp=new ArrayList<Double>();
            for(int j=0;j<cities.length;j++){
                //计算i到j的距离
                Double distance=Math.sqrt( Math.pow(cities[i].xCoordinate-cities[j].xCoordinate,2)+
                        Math.pow(cities[i].yCoordinate-cities[j].yCoordinate,2));
                arrTemp.add(distance);
            }
            DistanceAmongCities.add(arrTemp);
        }
        return DistanceAmongCities;
    }
    /*通过经过的城市序列计算总的路程长度.*/
    public static double getTotalDisByCitiesArr(city[] cities, ArrayList<Integer> citiesList,ArrayList<ArrayList<Double>> DistanceAmongCities){
        double result=0;
        int firstCityNum,secondCityNum;
        for(int i=0;i<citiesList.size()-1;i++){
            firstCityNum=citiesList.get(i);
            secondCityNum=citiesList.get(i+1);
            result+=DistanceAmongCities.get(firstCityNum).get(secondCityNum);
        }
        return result;
    }

}
