package com.hassle.video.players.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hassle.video.players.R;
import com.hassle.video.players.databinding.BottomVideomoreDialogBinding;
import com.hassle.video.players.interfaces.onDialogClicked;

public class VideoMoreDialog extends BottomSheetDialogFragment {
    Activity activity;
    onDialogClicked onDialogClicked;
    BottomSheetDialog bottomSheetDialog;
    String tittle;


    public VideoMoreDialog(Activity activity,String tittle, com.hassle.video.players.interfaces.onDialogClicked onDialogClicked) {
        this.activity = activity;
        this.tittle=tittle;
        this.onDialogClicked = onDialogClicked;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme);
        BottomVideomoreDialogBinding binding = BottomVideomoreDialogBinding.inflate(LayoutInflater.from(activity));
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.tvTittleSong.setText(tittle);
        binding.vGrpRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick(binding.tvRename.getText().toString(),"");
            }
        });
        binding.vGrpShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick(binding.tvShare.getText().toString(),"");
            }
        });
        binding.vGrpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClicked.onPerformClick(binding.tvDelete.getText().toString(),"");
            }
        });
        binding.vGrpProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClicked.onPerformClick(binding.tvProperties.getText().toString(),"");
            }
        });
        return bottomSheetDialog;
    }
}
