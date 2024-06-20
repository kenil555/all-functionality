package com.hassle.video.players.Dialogs;

import android.app.Dialog;
import android.content.Context;

import com.hassle.video.players.R;


public class LoadingDialog extends Dialog {
    private Context mContext;

    public LoadingDialog(Context context) {
        super(context, 2131820868);
        this.mContext = context;
        requestWindowFeature(1);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_dialog);
    }
}
