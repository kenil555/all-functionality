package com.hassle.video.players.Activitys.SearchVideosActivitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.hassle.video.players.Activitys.VideoPlayingActivity.VideoPlayingActivity;
import com.hassle.video.players.Adapters.AllVideoAdapter;
import com.hassle.video.players.CommanActivity.BaseActivity;
import com.hassle.video.players.Dialogs.CustomDialog;
import com.hassle.video.players.Dialogs.PropertiesDialog;
import com.hassle.video.players.Dialogs.RenameDialog;
import com.hassle.video.players.Dialogs.VideoMoreDialog;
import com.hassle.video.players.Models.Video.VideoModel;
import com.hassle.video.players.R;
import com.hassle.video.players.Utills.DialogType;
import com.hassle.video.players.Utills.FileUtils;
import com.hassle.video.players.Utills.GlobalVariable;
import com.hassle.video.players.Utills.Utility;
import com.hassle.video.players.databinding.ActivitySearchVideosBinding;
import com.hassle.video.players.interfaces.onDialogClicked;
import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.HandleClick.HandleClick;
import com.iten.tenoku.utils.AdUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchVideosActivity extends BaseActivity {
    private static final String TAG = "SearchVideosActivity";
    Activity activity;
    ActivitySearchVideosBinding binding;
    ArrayList<VideoModel> videoModelList = new ArrayList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    AllVideoAdapter allVideoAdapter;
    RenameDialog renamedialog;
    VideoMoreDialog videoMoreDialog;
    PropertiesDialog propertiesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchVideosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        todo();
    }

    private void todo() {
        if (getIntent().hasExtra(GlobalVariable.FROM)) {
            binding.toolbar.tvToolbar.setText(R.string.all_videos);
        } else {
            binding.toolbar.tvToolbar.setText(R.string.search_videos);
        }

        binding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.ivSearchToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vGrpEdtSearch.setVisibility(View.VISIBLE);
                binding.ivSearchClose.setVisibility(View.VISIBLE);
                binding.ivSearchToolbar.setVisibility(View.GONE);
            }
        });
        binding.ivSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivSearchClose.setVisibility(View.GONE);
                binding.vGrpEdtSearch.setVisibility(View.GONE);
                binding.ivSearchToolbar.setVisibility(View.VISIBLE);
            }
        });
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                allVideoAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getAllVideosFromDevice() {
        videoModelList.clear();
        Uri collection;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DATA};
        String sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC";
        Cursor cursor = activity.getContentResolver().query(collection, projection, null, null, sortOrder);
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
                Log.e(TAG, "name: ---------------->  " + name);
                Log.e(TAG, "date_added: ---------------->  " + date_added);
                date_added = FileUtils.convertDate(date_added, "dd/MM/yyyy");
                Log.e(TAG, "convertDate: ---------------->  " + date_added);
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                videoModelList.add(new VideoModel(String.valueOf(contentUri), name, duration, size, resolution, date_added, data));
            }

        }
    }

    private void SetAllVideo() {
        binding.progressBarVideoLoading.setVisibility(View.GONE);
        if (videoModelList.isEmpty()) {
            //Video Not Available
            binding.layoutDateNotFound.setVisibility(View.VISIBLE);
            binding.recyclerViewVideo.setVisibility(View.GONE);
        } else {

            //Video Found
            binding.layoutDateNotFound.setVisibility(View.GONE);
            binding.recyclerViewVideo.setVisibility(View.VISIBLE);
            if (getIntent().hasExtra(GlobalVariable.FAVORITES)) {
//                if (MyApp.sharedPreferenceHelper.getFavorites() != null) {
//                    if (MyApp.sharedPreferenceHelper.getFavorites().isEmpty()) {
//                        x.layoutDateNotFound.setVisibility(View.VISIBLE);
//                        x.recyclerViewVideo.setVisibility(View.GONE);
//                    } else {
//
//                        for (int i = 0; i < MyApp.sharedPreferenceHelper.getFavorites().size(); i++) {
//                            for (int j = 0; j < videoList.size(); j++) {
//                                if (videoList.get(j) != null) {
//                                    if (MyApp.sharedPreferenceHelper.getFavorites().get(i).getName().equals(videoList.get(j).getName())) {
//                                        favoritesList.add(videoList.get(j));
//                                    }
//                                }
//
//                            }
//                        }
//                        if (favoritesList.isEmpty()) {
//                            x.layoutDateNotFound.setVisibility(View.VISIBLE);
//                            x.recyclerViewVideo.setVisibility(View.GONE);
//                        } else {
//                            if (AdConstant.LIST_VIEW_PER_AD != 0) {
//                                for (int i = 0; i <= favoritesList.size(); i++) {
//                                    if (i % AdConstant.LIST_VIEW_PER_AD == 0) {
//                                        favoritesList.add(i, null);
//                                    }
//                                }
//
//                            }
//                            allVideoAdapter = new AllVideoDownloadAdapter(activity, favoritesList);
//                            x.recyclerViewVideo.setAdapter(allVideoAdapter);
//                        }
//                    }
//
//
//                } else {
//                    x.layoutDateNotFound.setVisibility(View.VISIBLE);
//                    x.recyclerViewVideo.setVisibility(View.GONE);
//                }

            } else {
                allVideoAdapter = new AllVideoAdapter(activity, videoModelList);
                binding.recyclerViewVideo.setAdapter(allVideoAdapter);
            }

        }
    }

    public void onVideoClick(int position) {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                Intent intent = new Intent(activity, VideoPlayingActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("video_title", videoModelList.get(position).getName());
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("videoArrayList", videoModelList);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        }, AdUtils.ClickType.MAIN_CLICK);

    }

    public void onMoreClick(int position) {
        videoMoreDialog = new VideoMoreDialog(activity, videoModelList.get(position).getName(), new onDialogClicked() {
            @Override
            public void onPerformClick(String type, String rename) {
                switch (type) {
                    case "Rename":
                        renamedialog = new RenameDialog(activity, videoModelList.get(position).getName(), new onDialogClicked() {
                            @Override
                            public void onPerformClick(String type, String rename) {
                                switch (type) {
                                    case "RENAME":
                                        renamedialog.dismiss();
                                        Utility.Showloader(activity);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Utility.cancelLoader();
                                                        FileUtils.rename(activity, Uri.parse(videoModelList.get(position).getUri()), rename);
                                                    }
                                                });

                                            }
                                        }, 2000);

                                        Toast.makeText(SearchVideosActivity.this, "RENAME-------> " + rename, Toast.LENGTH_SHORT).show();
                                        break;
                                    case "CANCEL":
                                        renamedialog.dismiss();
                                        break;
                                }
                            }
                        });
                        renamedialog.show();
                        break;
                    case "Delete":
                        videoMoreDialog.dismiss();
                        if (Build.VERSION.SDK_INT > 29) {
                            Log.e(TAG, ">> 29");
                            Log.e(TAG, "getData: ---------------->  " + videoModelList.get(position).getData());
                            Log.e(TAG, "getName: ---------------->  " + videoModelList.get(position).getName());
                            Log.e(TAG, "getUri: ---------------->  " + videoModelList.get(position).getUri());
                            MediaScannerConnection.scanFile(activity, new String[]{videoModelList.get(position).getData()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.e(TAG, "onScanCompleted: --------->uri " + uri);
                                    sendResultLauncher(uri);

                                }
                            });

                        } else {
                            Log.e(TAG, "< 29");
                            File filebnine = new File(videoModelList.get(position).getUri());
                            if (filebnine.exists()) {
                                if (filebnine.delete()) {
                                    DeleteSuccessfull();
                                } else {
                                    Log.e(TAG, "deleteFileFromFolder: ---------->Dleteing Faild");
                                }
                            }
                        }
                        break;
                    case "Share":
                        Uri videoUri = null;
                        if (Build.VERSION.SDK_INT > 30) {
                            videoUri = Uri.parse(videoModelList.get(position).getUri());
                        } else {
                            File videoFile = new File(videoModelList.get(position).getUri());
                            videoUri = Uri.fromFile(videoFile);
                        }

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
                        startActivity(Intent.createChooser(shareIntent, "Share video"));

                        break;
                    case "Properties":
                        videoMoreDialog.dismiss();
                        Log.e(TAG, "Properties Click: -------> " + videoModelList.get(position).getDate_added());
                        propertiesDialog = new PropertiesDialog(activity, videoModelList.get(position).getName()
                                + "*" + videoModelList.get(position).getDuration()
                                + "*" + videoModelList.get(position).getSize()
                                + "*" + videoModelList.get(position).getDate_added()
                                + "*" + videoModelList.get(position).getData()
                                , new onDialogClicked() {
                            @Override
                            public void onPerformClick(String type, String rename) {
                                propertiesDialog.dismiss();
                            }
                        });
                        propertiesDialog.show();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
            }
        });
        videoMoreDialog.show(getSupportFragmentManager(), videoMoreDialog.getTag());
    }


    private void sendResultLauncher(Uri uri) {
        List<Uri> list = new ArrayList<>();
        list.add(uri);
        IntentSender intentSender = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            intentSender = MediaStore.createDeleteRequest(activity.getContentResolver(), list).getIntentSender();
            IntentSenderRequest senderRequest = new IntentSenderRequest.Builder(intentSender)
                    .setFillInIntent(null)
                    .setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION, 0)
                    .build();
            try {
                deleteResultLauncher.launch(senderRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    ActivityResultLauncher<IntentSenderRequest> deleteResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        DeleteSuccessfull();
                    }

                }
            }
    );

    public void DeleteSuccessfull() {
        new CustomDialog("Delete", "Delete Successfully", DialogType.SUCCESS, false, false, false, new CustomDialog.DialogCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onWarning() {

            }

            @Override
            public void onDismiss() {

            }

            @Override
            public void onComplete(Dialog dialog) {
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBarVideoLoading.setVisibility(View.VISIBLE);
                        getAllVideosFromDevice();
                        handler.post(() -> SetAllVideo());
                    }
                });


            }
        }).show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdShow.getInstance(activity).ShowNativeAd(binding.nativeBig.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        AdShow.getInstance(activity).ShowNativeAd(binding.nativeSmall.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
        binding.progressBarVideoLoading.setVisibility(View.VISIBLE);
        getAllVideosFromDevice();
        handler.post(() -> SetAllVideo());

    }

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                SearchVideosActivity.super.onBackPressed();

            }
        }, AdUtils.ClickType.BACK_CLICK);

    }
}