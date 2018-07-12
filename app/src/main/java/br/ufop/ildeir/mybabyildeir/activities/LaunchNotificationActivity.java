package br.ufop.ildeir.mybabyildeir.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.ufop.ildeir.mybabyildeir.R;

public class LaunchNotificationActivity extends AppCompatActivity {

    public static NotificationCompat.Builder mBuilder;
    public static NotificationManager mNotificationManager;
    public static String GROUP = "GROUP_MYBABYILDEIR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_launch_notification);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String id;
        String name;
        String description;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getString("channel_id");
            name = bundle.getString("channel_name");
            description = bundle.getString("channel_description");
        } else {
            id = "channel_id";
            name = "channel_name";
            description = "channel_description";
        }

        //Setup a notification channel

        int importance = NotificationManager.IMPORTANCE_LOW;

        if(Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,100});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        //Intent that is gonna be executed when the users clicks on the notification
        Intent it = new Intent(this, NotificationsActivity.class);
        PendingIntent p = PendingIntent.getActivity(this,Integer.parseInt(id),it,0);

        //Creates the notification
        inboxStyle.setBigContentTitle("Lembretes");
        inboxStyle.addLine("Notificação");
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(name)
                .setContentText(description)
                .setOngoing(false)
                .setContentIntent(p)
                .setChannelId(id)
                .setAutoCancel(true)
                .setGroup(GROUP)
                .setGroupSummary(true)
                .setStyle(inboxStyle);
        mNotificationManager.notify("App name",Integer.parseInt(id), mBuilder.build());
        finish();

    }
}
