package com.c323proj8.sammycokeley;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {

    MediaPlayer mp;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this,"test", Toast.LENGTH_SHORT).show();
        mp = MediaPlayer.create(this, R.raw.chimes);
        mp.setLooping(true);
        mp.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
