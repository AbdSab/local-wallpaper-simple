package com.wallpaper.wallpapers.components;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.wallpaper.wallpapers.R;

public class AdManager {
    public final String bannerId = "ca-app-pub-3940256099942544/6300978111";
    public final String interId = "ca-app-pub-3940256099942544/1033173712";

    private View view;
    private Context context;

    private AdView bannerView;
    private InterstitialAd interView;

    public AdManager(View view){
        this.view = view;
        this.context = this.view.getContext();

        MobileAds.initialize(view.getContext(), initializationStatus -> {
        });

        interView = new InterstitialAd(this.view.getContext());
        interView.setAdUnitId(interId);
        interView.loadAd(new AdRequest.Builder().build());
        interView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                interView.show();
            }
            @Override
            public void onAdClosed() {
                //interView.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void showBanner(LinearLayout layout){
        bannerView = new AdView(context);
        bannerView.setAdSize(AdSize.BANNER);
        bannerView.setAdUnitId(bannerId);
        layout.addView(bannerView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);
    }

    public void showInter(){
        if(interView.isLoaded()){
            interView.show();
        }
    }
}