package com.wallpaper.wallpapers.components;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class AdManager {
    public final String bannerId = "ca-app-pub-3940256099942544/6300978111";
    public final String interId = "ca-app-pub-3940256099942544/1033173712";
    public static final String nativeId = "ca-app-pub-3940256099942544/2247696110";

    private AdView bannerView;
    private InterstitialAd mInterstitialAd;
    private Activity activity;

    public AdManager(Activity activity, boolean showInter){
        this.activity = activity;
        MobileAds.initialize(activity, initializationStatus -> {});

        AdRequest adRequest = new AdRequest.Builder().build();
        if(showInter) {
            InterstitialAd.load(activity, interId, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    AdManager.this.mInterstitialAd = interstitialAd;
                    showInter(activity);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd = null;
                }
            });
        }
    }

    public void showBanner(LinearLayout layout){
        bannerView = new AdView(activity);
        bannerView.setAdSize(AdSize.BANNER);
        bannerView.setAdUnitId(bannerId);
        layout.addView(bannerView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);
    }

    public void showInter(Activity activity){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        }
    }
}