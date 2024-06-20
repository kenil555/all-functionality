package com.hassle.video.players.Fragments.BottomFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hassle.video.players.Adapters.MusicViewPager;
import com.hassle.video.players.Adapters.VideoViewPager;
import com.hassle.video.players.Fragments.ArtistFragment;
import com.hassle.video.players.Fragments.PlaylistFragment;
import com.hassle.video.players.Fragments.SongFragment;
import com.hassle.video.players.databinding.MusicFragmentBinding;
import com.hassle.video.players.databinding.VideoFragmentBinding;
import com.iten.tenoku.utils.AdConstant;

public class MusicFragment extends Fragment {
    MusicFragmentBinding binding;
    Activity activity;
    MusicViewPager videoViewPager;
    @Override
    public void onStart() {
        super.onStart();
        AdConstant.isResume = true;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MusicFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoViewPager = new MusicViewPager(getChildFragmentManager());
        videoViewPager.addFragment(new SongFragment(), "Songs");
        videoViewPager.addFragment(new PlaylistFragment(), "Playlists");
        videoViewPager.addFragment(new ArtistFragment(), "Artist");
        binding.viewPager.setOffscreenPageLimit(-1);
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setAdapter(videoViewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
