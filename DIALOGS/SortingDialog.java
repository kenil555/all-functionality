package com.hassle.video.players.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hassle.video.players.R;
import com.hassle.video.players.databinding.BottomSortingDialogBinding;
import com.hassle.video.players.databinding.BottomVideomoreDialogBinding;
import com.hassle.video.players.interfaces.onDialogClicked;

public class SortingDialog extends BottomSheetDialogFragment {
    Activity activity;
    onDialogClicked onDialogClicked;
    BottomSheetDialog bottomSheetDialog;
    String tittle;

    public SortingDialog(Activity activity, String tittle, com.hassle.video.players.interfaces.onDialogClicked onDialogClicked) {
        this.activity = activity;
        this.tittle=tittle;
        this.onDialogClicked = onDialogClicked;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme);
        BottomSortingDialogBinding binding = BottomSortingDialogBinding.inflate(LayoutInflater.from(activity));
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(!tittle.isEmpty() && tittle.equals("SONG"))
        {
            binding.vGrpFileLarge.setVisibility(View.VISIBLE);
            binding.vGrpFileSmall.setVisibility(View.VISIBLE);
            binding.vGrpDurationLarge.setVisibility(View.VISIBLE);
            binding.vGrpDurationSmall.setVisibility(View.VISIBLE);
        }
        binding.checkBoxNewsetFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(true);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","NEW");
                }
            }
        });

        binding.checkBoxOldersFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(true);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","OLD");
                }
            }
        });
        binding.checkBoxAtoZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(true);
                    binding.checkBoxZtoA.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","ATOZ");
                }
            }
        });
        binding.checkBoxZtoA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(true);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","ZTOA");
                }
            }
        });

        binding.vGrpNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(true);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","NEW");
            }
        });
        binding.vGrpOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(true);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","OLD");
            }
        });
        binding.vGrpATOZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(true);
                binding.checkBoxZtoA.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","ATOZ");
            }
        });
        binding.vGrpZTOA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(true);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","ZTOA");
            }
        });
        binding.vGrpFileLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);

                binding.checkBoxFileLarge.setChecked(true);
                binding.checkBoxFileSmall.setChecked(false);
                binding.checkBoxDurationLarge.setChecked(false);
                binding.checkBoxDurationSmall.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","FLARGE");
            }
        });
        binding.checkBoxFileLarge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);

                    binding.checkBoxFileLarge.setChecked(true);
                    binding.checkBoxFileSmall.setChecked(false);
                    binding.checkBoxDurationLarge.setChecked(false);
                    binding.checkBoxDurationSmall.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","FLARGE");
                }
            }
        });

        binding.vGrpFileSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);

                binding.checkBoxFileLarge.setChecked(false);
                binding.checkBoxFileSmall.setChecked(true);
                binding.checkBoxDurationLarge.setChecked(false);
                binding.checkBoxDurationSmall.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","FSMALL");
            }
        });
        binding.checkBoxFileSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);

                    binding.checkBoxFileLarge.setChecked(false);
                    binding.checkBoxFileSmall.setChecked(true);
                    binding.checkBoxDurationLarge.setChecked(false);
                    binding.checkBoxDurationSmall.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","FSMALL");
                }
            }
        });

        binding.vGrpDurationLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);

                binding.checkBoxFileLarge.setChecked(false);
                binding.checkBoxFileSmall.setChecked(false);
                binding.checkBoxDurationLarge.setChecked(true);
                binding.checkBoxDurationSmall.setChecked(false);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","DLARGE");
            }
        });
        binding.checkBoxDurationLarge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);

                    binding.checkBoxFileLarge.setChecked(false);
                    binding.checkBoxFileSmall.setChecked(false);
                    binding.checkBoxDurationLarge.setChecked(true);
                    binding.checkBoxDurationSmall.setChecked(false);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","DLARGE");
                }
            }
        });
        binding.vGrpDurationSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkBoxNewsetFirst.setChecked(false);
                binding.checkBoxOldersFirst.setChecked(false);
                binding.checkBoxAtoZ.setChecked(false);
                binding.checkBoxZtoA.setChecked(false);

                binding.checkBoxFileLarge.setChecked(false);
                binding.checkBoxFileSmall.setChecked(false);
                binding.checkBoxDurationLarge.setChecked(false);
                binding.checkBoxDurationSmall.setChecked(true);
                bottomSheetDialog.dismiss();
                onDialogClicked.onPerformClick("TRUE","DSMALL");
            }
        });

        binding.checkBoxDurationSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.checkBoxNewsetFirst.setChecked(false);
                    binding.checkBoxOldersFirst.setChecked(false);
                    binding.checkBoxAtoZ.setChecked(false);
                    binding.checkBoxZtoA.setChecked(false);

                    binding.checkBoxFileLarge.setChecked(false);
                    binding.checkBoxFileSmall.setChecked(false);
                    binding.checkBoxDurationLarge.setChecked(false);
                    binding.checkBoxDurationSmall.setChecked(true);
                    bottomSheetDialog.dismiss();
                    onDialogClicked.onPerformClick("TRUE","DSMALL");
                }
            }
        });
        return bottomSheetDialog;
    }
}
