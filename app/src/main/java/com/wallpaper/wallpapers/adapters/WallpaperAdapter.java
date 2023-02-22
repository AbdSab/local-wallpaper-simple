package com.wallpaper.wallpapers.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.squareup.picasso.Callback;
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

    public WallpaperAdapter(Context context, @NonNull List<Wallpaper> wallpapers) {
        this.context = context;
        this.wallpaperList = new ArrayList<>();
        int i = 0;
        for(Wallpaper wallpaper: wallpapers){
            if(((i+1) % 16) == 0) {
                this.wallpaperList.add(new Item(AD, null));
                i++;
            }
            this.wallpaperList.add(new Item(WALLPAPER, wallpaper));
            i++;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.wallpaperList.get(position).type;
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
        Log.d("RORO", position + "--" + wallpaperList.get(position).type + "--" + getItemViewType(position));
        if(wallpaperList.get(position).type == WALLPAPER) {
            Log.d("HELLO bb", getItemViewType(position) + "");
            Picasso.get().load(wallpaperList.get(position).wallpaper.thumb).into(((MyViewHolder) holder).wallpaper, new Callback() {
                @Override
                public void onSuccess() {
                    ((MyViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }else {
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
                        public void onAdFailedToLoad(LoadAdError adError) {
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().build())
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
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
        private ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wallpaper = itemView.findViewById(R.id.wallpaper_image_item);
            progressBar = itemView.findViewById(R.id.wallpaper_item_progress);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FullWallpaperActivity.class);
                intent.putExtra("wallpaper", wallpaperList.get(getAdapterPosition()).wallpaper);
                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, wallpaper, "wallpaperTransition");
                }
                context.startActivity(intent, options.toBundle());
            });
        }
    }
}
