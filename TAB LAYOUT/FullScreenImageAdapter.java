package com.hassle.video.players.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hassle.video.players.Models.Model_images;
import com.hassle.video.players.R;
import com.hassle.video.players.databinding.AdapterFullscreenBinding;

import java.util.ArrayList;


public class FullScreenImageAdapter extends PagerAdapter {
    Activity context;
    ArrayList<String> img;
    ArrayList<Model_images> arrayList;
    boolean fromRecent;

    public FullScreenImageAdapter(Boolean fromRecent, Activity context, ArrayList<String> img, ArrayList<Model_images> arrayList) {
        this.context = context;
        this.img = img;
        this.fromRecent = fromRecent;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (fromRecent) {
            return arrayList.size();
        } else {
            return img.size();
        }
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int i) {
        AdapterFullscreenBinding binding = AdapterFullscreenBinding.inflate(LayoutInflater.from(context), container, false);
        if (fromRecent) {
            if (arrayList.get(i) != null) {
                binding.image.setVisibility(View.VISIBLE);
                Glide.with(context).asBitmap()
                        .placeholder(R.drawable.ic_place_holder)
                        .load(arrayList.get(i).getStr_folder())
                        .placeholder(R.drawable.ic_place_holder)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                binding.image.setImageBitmap(resource);
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.image);
            }
        } else {
            if (img.get(i) != null) {
                binding.image.setVisibility(View.VISIBLE);
                Glide.with(context).asBitmap()
                        .placeholder(R.drawable.ic_place_holder)
                        .load(img.get(i))
                        .placeholder(R.drawable.ic_place_holder)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                binding.image.setImageBitmap(resource);
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.image);
            }
        }

        container.addView(binding.getRoot());
        return binding.getRoot();


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
