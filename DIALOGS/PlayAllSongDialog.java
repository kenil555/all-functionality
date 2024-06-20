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
import com.hassle.video.players.Utills.GlobalVariable;
import com.hassle.video.players.databinding.BottomPlayallBinding;

public class PlayAllSongDialog extends BottomSheetDialogFragment {
    Activity activity;
    BottomSheetDialog bottomSheetDialog;

    BottomPlayallBinding binding;
    com.hassle.video.players.interfaces.onDialogClicked onDialogClicked;
    String tittle;
    boolean isShowRemove;

    public PlayAllSongDialog(Activity activity, String tittle, boolean isShowRemove, com.hassle.video.players.interfaces.onDialogClicked onDialogClicked) {
        this.activity = activity;
        this.onDialogClicked = onDialogClicked;
        this.tittle = tittle;
        this.isShowRemove = isShowRemove;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme);
        binding = BottomPlayallBinding.inflate(LayoutInflater.from(activity));
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (isShowRemove) {
            binding.constraintLayout9.setVisibility(View.VISIBLE);

        } else {
            binding.constraintLayout9.setVisibility(View.GONE);
        }
        binding.tvTittleSong.setText(tittle);
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        binding.viewplayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick(GlobalVariable.PLAYALL, "");
            }
        });
        binding.viewSuffleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick(GlobalVariable.SUFFLEALL, "");
            }
        });
        binding.viewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick(GlobalVariable.REMOVE, "");
            }
        });
        return bottomSheetDialog;
    }
}
