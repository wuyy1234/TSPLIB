package com.example.admin.tsplib;

import java.util.ArrayList;

/*本地搜索*/
public class localSearch {
    /*返回经过的城市序号列*/
    public static ArrayList<Integer> getCityListUsingLocalSearch(city[] cities,city startingCities,ArrayList<ArrayList<Double>> DistanceAmongCities ){
        ArrayList<Integer> result=new ArrayList<Integer>();
        cities[ startingCities.num ].isVisited=true;
        result.add(startingCities.num);
        for(int i=0;i<cities.length-1;i++){
            //寻找最近且未访问过的城市
            city nearestNotVisitedCity=new city();
            double disTemp=100000;
            for(int j=0;j<cities.length;j++){
                if(!cities[j].isVisited&&DistanceAmongCities.get(i).get(j)<disTemp){
                    nearestNotVisitedCity=cities[j];
                    disTemp=DistanceAmongCities.get(i).get(j);
                }
            }
            cities[nearestNotVisitedCity.num].isVisited=true;
            result.add(nearestNotVisitedCity.num);
        }
        return result;
    }



}
