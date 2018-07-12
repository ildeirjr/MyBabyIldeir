package br.ufop.ildeir.mybabyildeir.singletons;

import android.app.AlarmManager;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import br.ufop.ildeir.mybabyildeir.objects.Notification;

/**
 * Created by Ildeir on 07/07/2018.
 */

public class NotificationSingleton {

    public static NotificationSingleton singleton = null;

    private static ArrayList<Notification> notifications;

    public static NotificationSingleton getInstance(){
        if(singleton == null){
            singleton = new NotificationSingleton();
        }
        return singleton;
    }

    private NotificationSingleton(){
        notifications = new ArrayList<>();
    }

    public void saveNotifications(Context context){
        FileOutputStream fos;
        try{
            fos = context.openFileOutput("notifications.tmp",Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notifications);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNotifications(Context context){
        FileInputStream fis;
        try{
            fis = context.openFileInput("notifications.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            notifications = (ArrayList<Notification>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        NotificationSingleton.notifications = notifications;
    }

    public void removeNotification(AlarmManager alarmManager, int index){
        alarmManager.cancel(notifications.get(index).getPendingIntent());
        notifications.remove(index);
    }
}
