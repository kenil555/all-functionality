package com.hassle.video.players.Dialogs;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hassle.video.players.R;
import com.hassle.video.players.databinding.BottomCreateplaylistDialogBinding;
import com.hassle.video.players.interfaces.onDialogClicked;

import java.io.IOException;
import java.io.InputStream;

public class CreatePlaylistDialog extends BottomSheetDialogFragment {
    Activity activity;
    onDialogClicked onDialogClicked;
    BottomSheetDialog bottomSheetDialog;
    String tittle;
    BottomCreateplaylistDialogBinding binding;
    String photoUri;
    ActivityResultLauncher<Intent> galleryResult;
    public CreatePlaylistDialog(Activity activity, String tittle, com.hassle.video.players.interfaces.onDialogClicked onDialogClicked) {
        this.activity = activity;
        this.onDialogClicked = onDialogClicked;
        this.tittle = tittle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme);
        binding = BottomCreateplaylistDialogBinding.inflate(LayoutInflater.from(activity));
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        galleryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    binding.imageView.setVisibility(View.GONE);
                    binding.ivPhoto.setVisibility(View.VISIBLE);
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        photoUri = selectedImageUri.toString();
                        try {
                            InputStream inputStream = activity.getContentResolver().openInputStream(selectedImageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.ivPhoto.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        binding.vGrpChooserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryResult.launch(intent);

            }
        });


        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.edSearch.getText().toString().isEmpty() && !binding.edSearch.getText().toString().equals("")) {
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("CREATE", binding.edSearch.getText().toString() + "-" + photoUri);
                } else {
                    Toast.makeText(activity, "Please enter playlistname", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return bottomSheetDialog;
    }



}
