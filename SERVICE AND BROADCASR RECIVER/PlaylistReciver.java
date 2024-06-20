package com.hassle.video.players.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hassle.video.players.Utills.GlobalVariable;
import com.hassle.video.players.Utills.MyApplicationApp;
import com.iten.tenoku.utils.MyApplication;

public class PlaylistReciver extends BroadcastReceiver {
    private static final String TAG = "PlaylistReciver";
    public GlobalVariable.PlayType playType;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " );
        Intent service = new Intent(context,PlaylistService.class);
        playType = (GlobalVariable.PlayType) intent.getSerializableExtra(GlobalVariable.FROM);
        Log.e(TAG, "onReceive: getAction -------> "+intent.getAction() );
        Log.e(TAG, "onReceive: playType -------> "+playType );
        if(intent.getAction() != null) {
            switch (intent.getAction()) {
                case MyApplication.PLAY:
                    Log.e(TAG, "onReceive: PLAY " );
                    service.putExtra(GlobalVariable.MYACTION,intent.getAction());
                    service.putExtra(GlobalVariable.FROM,playType);
                    context.startService(service);
                    break;
                case MyApplication.PAUSE:
                    Log.e(TAG, "onReceive: PAUSE" );
                    service.putExtra(GlobalVariable.MYACTION,intent.getAction());
                    service.putExtra(GlobalVariable.FROM,playType);
                    context.startService(service);
                    break;
                case MyApplication.RESUME:
                    Log.e(TAG, "onReceive:RESUME " );
                    service.putExtra(GlobalVariable.MYACTION,intent.getAction());
                    service.putExtra(GlobalVariable.FROM,playType);
                    context.startService(service);
                    break;
                case MyApplication.NEXT:
                    Log.e(TAG, "onReceive:NEXT " );
                    service.putExtra(GlobalVariable.MYACTION,intent.getAction());
                    service.putExtra(GlobalVariable.FROM,playType);
                    context.startService(service);
                    break;
                case MyApplication.PREVIOUS:
                    Log.e(TAG, "onReceive: PREVIOUS" );
                    service.putExtra(GlobalVariable.MYACTION,intent.getAction());
                    service.putExtra(GlobalVariable.FROM,playType);
                    context.startService(service);
                    break;
                case MyApplication.EXIT:
                    MyApplicationApp.autoCloseApplication();
                    break;
            }
        }
    }
}
