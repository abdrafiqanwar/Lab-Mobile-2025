package com.example.praktikum3.data;

import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Home;

import java.util.ArrayList;

public class HomeData {
    public static ArrayList<Home> getHomeList() {
        ArrayList<Home> homeList = new ArrayList<>();

        ArrayList<Integer> LuoyiPosts = new ArrayList<>();
        LuoyiPosts.add(R.drawable.luoyi);

        ArrayList<Integer> AuroraPosts = new ArrayList<>();
        AuroraPosts.add(R.drawable.aurora);

        ArrayList<Integer> CecilionPosts = new ArrayList<>();
        CecilionPosts.add(R.drawable.cecilion);

        ArrayList<Integer> ChangePosts = new ArrayList<>();
        ChangePosts.add(R.drawable.change);

        ArrayList<Integer> LunoxPosts = new ArrayList<>();
        LunoxPosts.add(R.drawable.lunox);

        ArrayList<Integer> LyliaPosts = new ArrayList<>();
        LyliaPosts.add(R.drawable.lylia);

        ArrayList<Integer> NovariaPosts = new ArrayList<>();
        NovariaPosts.add(R.drawable.novaria);

        ArrayList<Integer> ValentinaPosts = new ArrayList<>();
        ValentinaPosts.add(R.drawable.valentina);

        ArrayList<Integer> VexanaPosts = new ArrayList<>();
        VexanaPosts.add(R.drawable.vexana);

        ArrayList<Integer> YvePosts = new ArrayList<>();
        YvePosts.add(R.drawable.yve);

//        u/pp
        homeList.add(new Home(R.drawable.luoyi, R.drawable.luoyi, "Ini Adalah Bio dari Luo yi", "Luoyi", LuoyiPosts));
        homeList.add(new Home(R.drawable.aurora, R.drawable.aurora, "Aurora", "Aurora", AuroraPosts));
        homeList.add(new Home(R.drawable.cecilion, R.drawable.cecilion, "Cecilion", "Cecilion", CecilionPosts));
        homeList.add(new Home(R.drawable.change, R.drawable.change, "Change", "Change", ChangePosts));
        homeList.add(new Home(R.drawable.lunox, R.drawable.lunox, "Lunox", "Lunox", LunoxPosts));
        homeList.add(new Home(R.drawable.lylia, R.drawable.lylia, "Lylia", "Lylia", LyliaPosts));
        homeList.add(new Home(R.drawable.novaria, R.drawable.novaria, "Novaria", "Novaria", NovariaPosts));
        homeList.add(new Home(R.drawable.valentina, R.drawable.valentina, "Valentina", "Valentina", ValentinaPosts));
        homeList.add(new Home(R.drawable.vexana, R.drawable.vexana, "Vexana", "Vexana", VexanaPosts));
        homeList.add(new Home(R.drawable.yve, R.drawable.yve, "Yve", "Yve", YvePosts));

        return homeList;
    }
}