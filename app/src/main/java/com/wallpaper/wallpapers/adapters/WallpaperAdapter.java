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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.activities.FullWallpaperActivity;
import com.wallpaper.wallpapers.models.Wallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.MyViewHolder> {

    Context context;
    List<Wallpaper> wallpaperList;

    public WallpaperAdapter(Context context, List<Wallpaper> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(wallpaperList.get(position).thumb).into(holder.wallpaper);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView wallpaper;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wallpaper = itemView.findViewById(R.id.wallpaper_image_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullWallpaperActivity.class);
                    intent.putExtra("wallpaper", wallpaperList.get(getAdapterPosition()));
                    ActivityOptions options = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, wallpaper, "wallpaperTransition");
                    }
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }
}
