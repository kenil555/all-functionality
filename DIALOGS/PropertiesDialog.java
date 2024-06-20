package com.hassle.video.players.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hassle.video.players.R;
import com.hassle.video.players.Utills.FileUtils;
import com.hassle.video.players.databinding.PropertiesDialogBinding;
import com.hassle.video.players.interfaces.onDialogClicked;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class PropertiesDialog extends Dialog {
    Dialog dialog;
    Activity activity;
    String videoname;
    onDialogClicked onDialogClicked;
    PropertiesDialogBinding binding;
    int dotIndex;
    String rename;
    String extension;

    public PropertiesDialog(Activity activity, String videoname, onDialogClicked onDialogClicked) {
        super(activity);
        this.activity = activity;
        this.videoname = videoname;
        this.onDialogClicked = onDialogClicked;

        Log.e("TAG", "value----->:  " + videoname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PropertiesDialogBinding.inflate(LayoutInflater.from(activity));
        setContentView(binding.getRoot());
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setCancelable(true);
        String[] stringPart = videoname.split("\\*");

        binding.tvFileName.setText(stringPart[0]);
        binding.tvDurationName.setText(FileUtils.makeReadable(Long.parseLong(stringPart[1])));
        binding.tvFileSizeName.setText(FileUtils.toHumanReadable(Long.parseLong(stringPart[2])));
        binding.tvDateName.setText(stringPart[3]);
        binding.tvLocationName.setText(stringPart[4]);
        binding.tvRenameOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClicked.onPerformClick("","");
            }

        });
    }


}
