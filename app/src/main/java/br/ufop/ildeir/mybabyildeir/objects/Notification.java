package br.ufop.ildeir.mybabyildeir.objects;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import br.ufop.ildeir.mybabyildeir.activities.LaunchNotificationActivity;

/**
 * Created by Ildeir on 07/07/2018.
 */

public class Notification implements Serializable {

    private Calendar calendar;
    private String name;
    private String description;
    private transient PendingIntent pendingIntent;
    private int frequency;
    private boolean activated;

    public Notification(Calendar calendar, String name, String description, int frequency) {
        this.calendar = calendar;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.activated = true;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setNotification(Context context){
        Toast toast = new Toast(context);
        Intent it = new Intent(context,LaunchNotificationActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("channel_id", String.valueOf(1 + (int) (Math.random() * 1000)));
        bundle.putString("channel_name",name);
        bundle.putString("channel_description",description);
        it.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context,Integer.parseInt(bundle.getString("channel_id")), it,0);
        long time = calendar.getTimeInMillis();
        long timeDifference = time - System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (timeDifference > 0) {
            if (frequency == 0) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000 * 60 * 60 * 24 / frequency, pendingIntent);
            }
            toast.makeText(context, "O lembrete foi definido para daqui a " + timeDifference / 1000 + " segundos", Toast.LENGTH_SHORT).show();
        } else {
            toast.makeText(context, "Horário inválido!", Toast.LENGTH_SHORT).show();
        }

    }

}
