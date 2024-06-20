package com.hassle.video.players.Services;

import static android.app.PendingIntent.FLAG_MUTABLE;
import static com.hassle.video.players.Activitys.MusicActivity.PlayerActivity.binding;
import static com.hassle.video.players.Activitys.MusicActivity.PlayerActivity.songServices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.hassle.video.players.Activitys.MusicActivity.PlayerActivity;
import com.hassle.video.players.Fragments.SongFragment;
import com.hassle.video.players.R;
import com.iten.tenoku.utils.MyApplication;
import com.hassle.video.players.Utills.Utility;
import com.hassle.video.players.interfaces.AudioPLayingCallBack;

import java.io.IOException;

public class MusicService extends Service implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener {
    public MediaPlayer mediaPlayer = new MediaPlayer();
    public static Runnable runnable;
    private final IBinder iBinder = new MyBinder();
    private MediaSessionCompat mediaSessionCompat;
    public static AudioManager audioManager;
    AudioPLayingCallBack audioPLayingCallBack;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaSessionCompat = new MediaSessionCompat(this, "MY MUSIC");
        return iBinder;
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String action = intent.getStringExtra("MyAction");
//        switch (action)
//        {
//            case MyApplication.PLAY:
//                audioPLayingCallBack.playClicked();
//                break;
//            case MyApplication.PREVIOUS:
//                audioPLayingCallBack.prevClicked();
//                break;
//            case MyApplication.NEXT:
//                audioPLayingCallBack.nextClicked();
//                break;
//            case MyApplication.EXIT:
//                audioPLayingCallBack.exitClicked();
//                break;
//        }
        return START_STICKY;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public void setUpSeekBar() {
        runnable = new Runnable() {
            @Override
            public void run() {
                binding.tvCurrTime.setText(Utility.timeConversion((long) songServices.mediaPlayer.getCurrentPosition()));
                binding.seekBarPlayer.setProgress(mediaPlayer.getCurrentPosition());
                new Handler(Looper.getMainLooper()).postDelayed(runnable, 200);
            }
        };
        new Handler(Looper.getMainLooper()).postDelayed(runnable, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(int playPauseBtn) {
        Intent intent = new Intent(this, PlayerActivity.class);
        PendingIntent playerPendingIntent = PendingIntent.getActivity(this, 0, intent,
                FLAG_MUTABLE);

        Intent prevIntent = new Intent(getBaseContext(), NotificationReceiver.class).setAction(MyApplication.PREVIOUS);
        PendingIntent prevPendindIntent = PendingIntent
                .getBroadcast(getBaseContext(), 0, prevIntent, FLAG_MUTABLE);

        Intent nextIntent = new Intent(this, NotificationReceiver.class).setAction(MyApplication.NEXT);
        PendingIntent nextPendindIntent = PendingIntent
                .getBroadcast(this, 0, nextIntent, FLAG_MUTABLE);

        Intent playIntent = new Intent(this, NotificationReceiver.class).setAction(MyApplication.PLAY);
        PendingIntent playPendindIntent = PendingIntent
                .getBroadcast(this, 0, playIntent, FLAG_MUTABLE);

        Intent exitIntent = new Intent(this, NotificationReceiver.class).setAction(MyApplication.EXIT);
        PendingIntent exitPendindIntent = PendingIntent
                .getBroadcast(this, 0, exitIntent, FLAG_MUTABLE);


        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentIntent(playerPendingIntent)
                .setContentTitle(SongFragment.songlist.get(PlayerActivity.poisition).getDisplayname())
                .setContentText(SongFragment.songlist.get(PlayerActivity.poisition).getArtist())
                .setSmallIcon(R.drawable.music_icon)
                .setLargeIcon(setImageNotification())
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.previous, MyApplication.PREVIOUS, prevPendindIntent)
                .addAction(playPauseBtn, MyApplication.PLAY, playPendindIntent)
                .addAction(R.drawable.next, MyApplication.NEXT, nextPendindIntent)
                .addAction(R.drawable.exit, MyApplication.EXIT, exitPendindIntent)
                .build();

        try {
            startForeground(101, notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void playMusic() {
        if (songServices.mediaPlayer == null)
            songServices.mediaPlayer = new MediaPlayer();

        songServices.mediaPlayer.reset();
        try {
            songServices.mediaPlayer.setDataSource(SongFragment.songlist.get(PlayerActivity.poisition).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            songServices.mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            songServices.showNotification(R.drawable.playy);
        }
        binding.tvCurrTime.setText(Utility.timeConversion((long) songServices.mediaPlayer.getCurrentPosition()));
        binding.tvTotalTime.setText(Utility.timeConversion((long) songServices.mediaPlayer.getDuration()));
        binding.seekBarPlayer.setProgress(0);
        binding.seekBarPlayer.setMax(songServices.mediaPlayer.getDuration());
        PlayerActivity.nowPlayingSongId = SongFragment.songlist.get(PlayerActivity.poisition).getId();
    }

    private Bitmap setImageNotification() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(SongFragment.songlist.get(PlayerActivity.poisition).getData());
        byte[] image = retriever.getEmbeddedPicture();
        Bitmap img = null;
        if (image != null) {
            img = BitmapFactory.decodeByteArray(image, 0, image.length);
        } else
            img = BitmapFactory.decodeResource(getResources(), R.drawable.music_icon);

        return img;
    }

    public void setCallback(AudioPLayingCallBack audioPLayingCallBack) {
        this.audioPLayingCallBack = audioPLayingCallBack;
    }


}
