package com.wallpaper.wallpapers.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.squareup.picasso.Picasso;
import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.activities.FullWallpaperActivity;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static int WALLPAPER = 0;
    public static int AD = 1;

    Context context;
    List<Item> wallpaperList;

    public WallpaperAdapter(Context context, List<Wallpaper> wallpaperList) {
        this.context = context;
        this.wallpaperList = new ArrayList<>();
        for(int i=0; i<wallpaperList.size(); i++) {
            if(i % 9 == 0 && i != 0) {
                this.wallpaperList.add(new Item(null, AD));
            }
            this.wallpaperList.add(new Item(wallpaperList.get(i), WALLPAPER));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 9 == 0 && position != 0) {
            return AD;
        } else {
            return WALLPAPER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == AD) {
            AdViewHolder adView = new AdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_item, null, false));
            return adView;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == WALLPAPER) {
            Picasso.get().load(wallpaperList.get(position).wallpaper.thumb).into(((MyViewHolder) holder).wallpaper);
            return;
        }
        final AdLoader adLoader = new AdLoader.Builder(context, AdManager.nativeId)
                .forNativeAd(nativeAd -> {
                    NativeTemplateStyle styles = new
                            NativeTemplateStyle.Builder().build();
                    TemplateView template = ((AdViewHolder) holder).templateView;
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {}
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        TemplateView templateView;
        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            templateView = itemView.findViewById(R.id.ad_template);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView wallpaper;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wallpaper = itemView.findViewById(R.id.wallpaper_image_item);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FullWallpaperActivity.class);
                intent.putExtra("wallpaper", wallpaperList.get(getAdapterPosition()));
                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, wallpaper, "wallpaperTransition");
                }
                context.startActivity(intent, options.toBundle());
            });
        }
    }
}
