package br.ufop.ildeir.mybabyildeir.activities;

import android.app.AlarmManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.adapters.NotificationAdapter;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddNotificationDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditNotificationDialog;
import br.ufop.ildeir.mybabyildeir.objects.Notification;
import br.ufop.ildeir.mybabyildeir.singletons.NotificationSingleton;

public class NotificationsActivity extends AppCompatActivity {

    private ListView listView;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        NotificationSingleton.getInstance().loadNotifications(this);

        getSupportActionBar().setTitle("Lembretes");

        listView = findViewById(R.id.notificationListView);
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(),this);
        listView.setAdapter(notificationAdapter);
        listView.setEmptyView(findViewById(R.id.tvEmptyNotificationListView));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditNotificationDialog editNotificationDialog = new EditNotificationDialog(view.getContext(),i);
                editNotificationDialog.setDialog(listView).show();
            }
        });

        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notifications_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionAddNotification:
                AddNotificationDialog addNotificationDialog = new AddNotificationDialog(this);
                addNotificationDialog.setDialog(listView).show();
                return true;
            case R.id.optionRemoveNotifications:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Aviso");
                alertDialog.setMessage("Deseja remover todos os lembretes?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0 ; j<NotificationSingleton.getInstance().getNotifications().size() ; j++){
                            alarmManager.cancel(NotificationSingleton.getInstance().getNotifications().get(j).getPendingIntent());
                        }
                        NotificationSingleton.getInstance().getNotifications().clear();
                        listView.setAdapter(new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(),getApplicationContext()));
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.create();
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        Log.e("teste","SALVAR");
        NotificationSingleton.getInstance().saveNotifications(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        NotificationSingleton.getInstance().loadNotifications(this);
        super.onStart();
    }
}
