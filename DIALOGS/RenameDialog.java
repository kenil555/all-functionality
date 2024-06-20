package com.hassle.video.players.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hassle.video.players.R;
import com.hassle.video.players.databinding.RenameDialogBinding;
import com.hassle.video.players.interfaces.onDialogClicked;

public class RenameDialog extends Dialog {
    Dialog dialog;
    Activity activity;
    String videoname;
    onDialogClicked onDialogClicked;
    RenameDialogBinding binding;
    int dotIndex;
    String rename;
    String extension;

    public RenameDialog(Activity activity, String videoname, onDialogClicked onDialogClicked) {
        super(activity);
        this.activity = activity;
        this.videoname = videoname;
        this.onDialogClicked = onDialogClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RenameDialogBinding.inflate(LayoutInflater.from(activity));
        setContentView(binding.getRoot());
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setCancelable(true);
        binding.edText.requestFocus();
        dotIndex = videoname.indexOf(".");
        rename = videoname.substring(0, dotIndex);
        extension = videoname.substring(dotIndex);
        binding.edText.setText(rename);
        binding.tvRenameOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDialogClicked.onPerformClick("RENAME", binding.edText.getText().toString() + extension);
            }
        });

        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClicked.onPerformClick("CANCEL", "");
            }
        });

    }


}
