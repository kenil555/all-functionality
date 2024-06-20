package com.hassle.video.players.Fragments;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hassle.video.players.Activitys.MusicActivity.PlayerActivity;
import com.hassle.video.players.Adapters.SongAdapter;
import com.hassle.video.players.Dialogs.SortingDialog;
import com.hassle.video.players.Models.MusicModels.MusicModel;
import com.hassle.video.players.databinding.SongsFragmentBinding;
import com.hassle.video.players.interfaces.onDialogClicked;
import com.hassle.video.players.interfaces.onItemClicked;
import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.HandleClick.HandleClick;
import com.iten.tenoku.utils.AdConstant;
import com.iten.tenoku.utils.AdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SongFragment extends Fragment {
    private static final String TAG = "SongFragment";
    Activity activity;
    public static SongsFragmentBinding binding;
    SongAdapter songAdapter;
    public static List<MusicModel> songlist = new ArrayList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    SortingDialog sortingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        AdConstant.isResume = true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        AdShow.getInstance(activity).ShowNativeAd(binding.nativeMedium.nativeAdLayout, AdUtils.NativeType.NATIVE_MEDIUM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SongsFragmentBinding.inflate(inflater, container, false);
        activity = getActivity();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todo();
        Log.e(TAG, "onViewCreated: SongFragment");

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: SongFragment");
        super.onDestroy();

    }

    private void todo() {
        binding.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingDialog = new SortingDialog(activity, "SONG", new onDialogClicked() {
                    @Override
                    public void onPerformClick(String type, String rename) {
                        switch (type) {
                            case "TRUE":
                                switch (rename) {
                                    case "NEW":
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o1.getModifiedDate().compareTo(o2.getModifiedDate());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        Log.e(TAG, "ivSorting NEW: ");
                                        break;
                                    case "OLD":
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o1.getDate().compareTo(o2.getDate());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        Log.e(TAG, "ivSorting OLD: ");
                                        break;

                                    case "ATOZ":
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o1.getDisplayname().compareTo(o2.getDisplayname());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        break;

                                    case "ZTOA":
                                        Log.e(TAG, "ivSorting ATOZ: ");
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o2.getDisplayname().compareTo(o1.getDisplayname());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        Log.e(TAG, "ivSorting ZTOA: ");
                                        break;
                                    case "FLARGE":
                                        Log.e(TAG, "ivSorting FLARGE: ");
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o1.getSize().compareTo(o2.getSize());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();

                                        break;
                                    case "FSMALL":
                                        Log.e(TAG, "ivSorting FSMALL: ");
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o2.getSize().compareTo(o1.getSize());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        break;
                                    case "DLARGE":
                                        Log.e(TAG, "ivSorting DLARGE: ");
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o1.getDuration().compareTo(o2.getDuration());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        break;
                                    case "DSMALL":
                                        Log.e(TAG, "ivSorting DSMALL: ");
                                        Collections.sort(songlist, new Comparator<MusicModel>() {
                                            @Override
                                            public int compare(MusicModel o1, MusicModel o2) {
                                                return o2.getDuration().compareTo(o1.getDuration());
                                            }
                                        });
                                        songAdapter.notifyDataSetChanged();
                                        break;

                                }
                                break;
                            case "FALSE":
                                break;
                        }
                    }
                });
                sortingDialog.show(getParentFragmentManager(), sortingDialog.getTag());
            }
        });
        executor.execute(() -> {
            binding.progressBarVideoLoading.setVisibility(View.VISIBLE);
            getAllAudioFromDevice();
            handler.post(this::SetAllVideo);
        });

    }

    private void getAllAudioFromDevice() {
        songlist.clear();
        Uri collection;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATE_TAKEN,
                MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media.DATE_ADDED,

        };
        String sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC";
        try {
            Cursor cursor = activity.getContentResolver().query(collection, projection, null, null, sortOrder);
            if (cursor != null) {
                Log.e(TAG, "cursor != null: ");
                while (cursor.moveToNext()) {
                    long idColumn = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String albumId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                    Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, idColumn);
                    Log.e(TAG, "uri------->  : " + uri.toString());
                    Log.e(TAG, "idColumn------->  : " + idColumn);

//                    Cursor albumCursor  = activity.getContentResolver().query(
//                            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
//                            new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
//                            MediaStore.Audio.Albums._ID + " = ?",
//                            new String[]{String.valueOf(albumId)},
//                            null);
//                    String pathAlbumArt = "null";
//
//                    if (albumCursor  != null) {
//                        try {
//                            Log.e(TAG, "cursoralbumArt NOT NULL");
//                            if (albumCursor .moveToFirst()) {
//                                Log.e(TAG, "cursoralbumArt.moveToFirst()");
//                                pathAlbumArt = albumCursor.getString(albumCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
//                                Log.e(TAG, "pathAlbumArt------->  : "+pathAlbumArt );
//                            }
//                        } finally {
//                            albumCursor.close(); // Close the cursor to avoid memory leaks
//                        }
//                    }


                    Log.e(TAG, "--------------------------- ");

                    songlist.add(new MusicModel(String.valueOf(uri),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED))));

                }
                cursor.close();
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }

    private void SetAllVideo() {
        binding.progressBarVideoLoading.setVisibility(View.GONE);
        if (songlist.isEmpty()) {
            //Video Not Available
            binding.layoutDateNotFound.setVisibility(View.VISIBLE);
            binding.recycler.setVisibility(View.GONE);
        } else {

            //Video Found
            binding.layoutDateNotFound.setVisibility(View.GONE);
            binding.recycler.setVisibility(View.VISIBLE);
            songAdapter = new SongAdapter(activity, songlist, new onItemClicked() {
                @Override
                public void onClicked(int position, int id) {
                    AdShow.getInstance(activity).ShowAd(new HandleClick() {
                        @Override
                        public void Show(boolean adShow) {
                            Intent intent = new Intent(activity, PlayerActivity.class);
                            intent.putExtra("positionAdapter", position);
                            intent.putExtra("className", "NowPlaying");
                            activity.startActivity(intent);
                        }
                    }, AdUtils.ClickType.MAIN_CLICK);

                }
            });
            binding.recycler.setAdapter(songAdapter);
        }
    }

}
