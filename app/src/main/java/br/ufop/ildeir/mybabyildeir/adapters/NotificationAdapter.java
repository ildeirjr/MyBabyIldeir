package br.ufop.ildeir.mybabyildeir.adapters;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.objects.Notification;

/**
 * Created by Ildeir on 07/07/2018.
 */

public class NotificationAdapter extends BaseAdapter {

    private ArrayList<Notification> notifications;
    private Context context;
    private SimpleDateFormat simpleDateFormat;
    private AlarmManager alarmManager;

    public NotificationAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int i) {
        return notifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Notification notification = notifications.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.notification_layout,null);

        TextView tvName = v.findViewById(R.id.notificationName);
        tvName.setText(notification.getName());

        TextView tvDate = v.findViewById(R.id.notificationDate);
        tvDate.setText(simpleDateFormat.format(notification.getCalendar().getTime()));

        final Switch aSwitch = v.findViewById(R.id.notificationSwitch);
        if(notification.isActivated()){
            aSwitch.setChecked(true);
            Log.e("teste","SETOU TRUE");
        } else {
            aSwitch.setChecked(false);
            Log.e("teste","SETOU FALSE");
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    notification.setNotification(context);
                    notification.setActivated(true);
                } else {
                    alarmManager.cancel(notification.getPendingIntent());
                    notification.setActivated(false);
                }
            }
        });

        return v;
    }
}
