package com.hassle.video.players.Fragments.BottomFragment;

import static android.app.Activity.RESULT_OK;
import static com.hassle.video.players.Utills.Utility.getImageFile;

import android.app.Activity;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.hassle.video.players.Activitys.CleanActivity.CleanActivity;
import com.hassle.video.players.Activitys.PhotosActivity.RecentPhotosActivity;
import com.hassle.video.players.Adapters.AllPhotosFolderAdapter;
import com.hassle.video.players.Dialogs.SortingDialog;
import com.hassle.video.players.Models.Model_images;
import com.hassle.video.players.Utills.GlobalVariable;
import com.hassle.video.players.Utills.PermissionUtils;
import com.hassle.video.players.databinding.PhotoFragmentBinding;
import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.HandleClick.HandleClick;
import com.iten.tenoku.utils.AdConstant;
import com.iten.tenoku.utils.AdUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";
    PhotoFragmentBinding binding;
    Activity activity;
    ActivityResultLauncher<String[]> storagePermission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> o) {
                    boolean allPermissionClear = true;
                    List<String> blockPermissionCheck = new ArrayList<>();
                    for (String key : o.keySet()) {
                        if (!(o.get(key))) {
                            allPermissionClear = false;
                            blockPermissionCheck.add(PermissionUtils.getPermissionStatus(activity, key));
                        }
                    }
                }
            });
    ActivityResultLauncher<Intent> onResultActivityCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {

            }
        }
    });
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newSingleThreadExecutor();
    public static AllPhotosFolderAdapter allPhotosFolderAdapter;
    public static ArrayList<Model_images> all_images = new ArrayList<>();
    ;

    SortingDialog sortingDialog;
    boolean boolean_folder = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        AdConstant.isResume = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PhotoFragmentBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todo();
    }

    private void todo() {
        allPhotosFolderAdapter = new AllPhotosFolderAdapter(activity);
        binding.recycler.setAdapter(allPhotosFolderAdapter);
        executor.execute(() -> {
            binding.progressBarVideoLoading.setVisibility(View.VISIBLE);
            getAllFolers();
            handler.post(() -> {
                if (!all_images.isEmpty()) {
                    binding.layoutDateNotFound.setVisibility(View.GONE);
                    allPhotosFolderAdapter.setData(all_images);
                    binding.progressBarVideoLoading.setVisibility(View.GONE);
                } else {
                    binding.layoutDateNotFound.setVisibility(View.VISIBLE);
                }

            });
        });
        // TODO: 17-01-2024 UpperButton Click
        binding.vGrpRecentPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdShow.getInstance(activity).ShowAd(new HandleClick() {
                    @Override
                    public void Show(boolean adShow) {
                        startActivity(new Intent(activity, RecentPhotosActivity.class));
                    }
                }, AdUtils.ClickType.MAIN_CLICK);
            }
        });
        binding.vGrpCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdShow.getInstance(activity).ShowAd(new HandleClick() {
                    @Override
                    public void Show(boolean adShow) {
                        startActivity(new Intent(activity, RecentPhotosActivity.class).putExtra(GlobalVariable.FROM, true));
                    }
                }, AdUtils.ClickType.MAIN_CLICK);
            }
        });
        binding.vGrpCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intentPicture.resolveActivity(activity.getPackageManager()) != null) {
                    try {
                        File file = getImageFile(activity);
                        Uri uri;
                        uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        onResultActivityCallBack.launch(intentPicture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        binding.vGrpClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdShow.getInstance(activity).ShowAd(new HandleClick() {
                    @Override
                    public void Show(boolean adShow) {
                        startActivity(new Intent(activity, CleanActivity.class)
                                .putExtra(GlobalVariable.FROM, GlobalVariable.PHOTO));
                    }
                }, AdUtils.ClickType.MAIN_CLICK);
            }
        });
    }

    private void getAllFolers() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_index_date_taken;

        String absolutePathOfImage = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = activity.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");


        while (cursor.moveToNext()) {
            Log.e(TAG, "cursor.moveToNext()");
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            column_index_date_taken = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);

            absolutePathOfImage = cursor.getString(column_index_data);
//            Log.e(TAG, "Date Taken--------------> " + cursor.getLong(column_index_date_taken));
//            Log.e(TAG, "Folder Name--------------> " + cursor.getString(column_index_folder_name));
//            Log.e(TAG, "Data--------------> " + cursor.getString(column_index_data));

            for (int i = 0; i < all_images.size(); i++) {
                Log.e(TAG, "IN LOOP");
                String folderName = all_images.get(i).getStr_folder();
                if (folderName != null) {
                    Log.e(TAG, "folderName != null");
                    if (all_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                        Log.e(TAG, "boolean_folder");
                        boolean_folder = true;
                        int_position = i;
                        break;
                    } else {
//                        Log.e(TAG, "boolean_folder NO");
                        boolean_folder = false;
                    }
                }

            }


            if (boolean_folder) {
                Log.e(TAG, "IS FOLDER");
                ArrayList<String> al_path = new ArrayList<>(all_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                all_images.get(int_position).setAl_imagepath(al_path);
                all_images.get(int_position).setDateTaken("" + int_position);
            } else {
                Log.e(TAG, "NOT FOLDER");
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images(cursor.getString(column_index_folder_name), "" + int_position, al_path);
                all_images.add(obj_model);

            }
        }

        cursor.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        AdShow.getInstance(activity).ShowNativeAd(binding.nativeMedium.nativeAdLayout, AdUtils.NativeType.NATIVE_MEDIUM);

    }
}
