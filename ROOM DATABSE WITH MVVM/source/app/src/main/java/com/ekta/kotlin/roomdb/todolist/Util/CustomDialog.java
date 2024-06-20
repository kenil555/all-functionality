//package com.ekta.kotlin.roomdb.todolist.Util;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//import com.ep.video.player.videoplayer.R;
//import com.ep.video.player.videoplayer.Utills.DialogType;
//import com.ep.video.player.videoplayer.databinding.FragmentCustomeBinding;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class CustomDialog extends DialogFragment {
//    DialogCallBack dialogCallBack;
//    FragmentCustomeBinding binding;
//    DialogType dialogType;
//
//    String title,message;
//    boolean showCancel = false;
//    boolean cancelLabel = false;
//    boolean showButton = true;
//    public interface DialogCallBack {
//        public void onSuccess();
//
//        public void onWarning();
//
//        void onDismiss();
//
//        public void onComplete(Dialog dialog);
//    }
//
//    public CustomDialog(String title, String message, DialogType dialogType, boolean showCancel, boolean cancelLabel, boolean showButton, DialogCallBack callback) {
//        this.title = title;
//        this.message = message;
//        this.dialogType = dialogType;
//        this.showCancel = showCancel;
//        this.cancelLabel = cancelLabel;
//        this.showButton = showButton;
//        this.dialogCallBack = callback;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        binding = FragmentCustomeBinding.inflate(getLayoutInflater());
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getDialog().getWindow().setAttributes(getLayoutParams(getDialog()));
//        setCancelable(cancelLabel);
//
//        binding.titleTv.setText(title);
//        binding.messageTv.setText(message);
//        switch (dialogType)
//        {
//            case SUCCESS:
//                binding.titleTv.setBackgroundColor(getContext().getColor(R.color.success));
//                binding.animationView.setAnimation(R.raw.success);
//                binding.okBtn.setText(getString(R.string.done));
//                break;
//            case ERROR:
//                binding.titleTv.setBackgroundColor(getContext().getColor(R.color.error));
//                binding.animationView.setAnimation(R.raw.error);
//                binding.okBtn.setText(getString(R.string.poster_try_again));
//
//                break;
//            case WARNING:
//                binding.titleTv.setBackgroundColor(getContext().getColor(R.color.warning));
//                binding.animationView.setAnimation(R.raw.warning);
//                binding.okBtn.setText(getString(R.string.submit));
//                break;
//        }
//        if (!showButton){
//            binding.cencelBtn.setVisibility(View.GONE);
//            binding.okBtn.setVisibility(View.GONE);
//        }
//
//        if (!showCancel || !cancelLabel){
//            binding.cencelBtn.setVisibility(View.GONE);
//            setCancelable(false);
//        }
//
//        binding.okBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(showButton)
//                {
//                    dialogCallBack.onSuccess();
//                    dismiss();
//                }else {
//                dismiss();
//                }
//            }
//        });
//        binding.cencelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        if(showButton)
//        {
//
//        }else {
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    dialogCallBack.onComplete(getDialog());
//                }
//            },2000);
//        }
//
//    }
//
//
//    @Override
//    public void onDismiss(@NonNull DialogInterface dialog) {
//        super.onDismiss(dialog);
//        dialogCallBack.onDismiss();
//    }
//
//    private WindowManager.LayoutParams getLayoutParams(Dialog dialog) {
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        if (dialog.getWindow() != null) {
//            layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        }
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        return layoutParams;
//    }
//}
