package com.rajgmail.r_battery_status_using_broadcast_receiver;

import java.io.IOException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    //broadcast class is used as a nested class
    private BroadcastReceiver mbcr=new BroadcastReceiver()
    {
        //onReceive method will receive updates
        public void onReceive(Context c, Intent i)
        {
            //initially level has 0 value
            //after getting update from broadcast receiver
            //it will change and give battery status
            int level=i.getIntExtra("level", 0);
            //initialize all objects
            ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
            TextView tv=(TextView)findViewById(R.id.textView1);
            //set level of progress bar
            pb.setProgress(level);
            //display level on text view
            tv.setText("Batterylevel:"+Integer.toString(level)+"%");
            //start a song when the battery level touches 100%
            if(level==100)
            {
                try
                {
                    AssetFileDescriptor afd=getAssets().openFd("small.mp4");
                    MediaPlayer mp=new MediaPlayer();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                }
                catch(IOException e){}
            }
        }
    };
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(mbcr,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}