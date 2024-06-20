package com.hassle.video.players.Dialogs;

import static com.hassle.video.players.Activitys.VideoPlayingActivity.VideoPlayingActivity.MY_PREF;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hassle.video.players.Adapters.AllVideoAdapter;
import com.hassle.video.players.Models.Video.VideoModel;
import com.hassle.video.players.R;
import com.hassle.video.players.Utills.FileUtils;

import java.util.ArrayList;

public class PlaylistDialog extends BottomSheetDialogFragment {

    ArrayList<VideoModel> arrayList = new ArrayList<>();
    AllVideoAdapter videoFilesAdapter;
    BottomSheetDialog bottomSheetDialog;
    RecyclerView recyclerView;
    TextView folder;
    Activity activity;

    public PlaylistDialog(Activity activity,ArrayList<VideoModel> arrayList, AllVideoAdapter videoFilesAdapter) {
        this.activity=activity;
        this.arrayList = arrayList;
        this.videoFilesAdapter = videoFilesAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.playlist_bs_layout, null);
        bottomSheetDialog.setContentView(view);

        recyclerView = view.findViewById(R.id.playlist_rv);
        folder = view.findViewById(R.id.playlist_name);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        String folderName = preferences.getString("playlistFOlderName","abc");
        folder.setText(folderName);

        arrayList = fetchMedia(folderName);
        videoFilesAdapter = new AllVideoAdapter(activity,arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoFilesAdapter);
        videoFilesAdapter.notifyDataSetChanged();

        return bottomSheetDialog;

    }

    private ArrayList<VideoModel> fetchMedia(String folderName) {
        ArrayList<VideoModel> videoFiles = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Video.Media.DATA+" like?";
        String[] selectionArg = new String[]{"%"+folderName+"%"};
        Cursor cursor = getContext().getContentResolver().query(uri, null,
                selection, selectionArg, null);
        if (cursor != null) {

            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int resolutionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION);
            int date_addedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
            int data_Column = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            while (cursor.moveToNext()) {

                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String resolution = cursor.getString(resolutionColumn);
                String date_added = cursor.getString(date_addedColumn);
                date_added = FileUtils.convertDate(date_added, "dd/MM/yyyy hh:mm:ss");
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                videoFiles.add(new VideoModel(String.valueOf(contentUri), name, duration, size, date_added, resolution, data));
            }

        }
        return videoFiles;
    }

}
