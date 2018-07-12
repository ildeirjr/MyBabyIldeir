package br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.adapters.NotificationAdapter;
import br.ufop.ildeir.mybabyildeir.objects.Notification;
import br.ufop.ildeir.mybabyildeir.singletons.NotificationSingleton;

/**
 * Created by Ildeir on 07/07/2018.
 */

public class EditNotificationDialog {

    private int notificationIndex;
    private Notification notification;

    private AlertDialog alertDialog;
    private Context context;
    private View view;

    private EditText etName;
    private EditText etAnnotations;
    private TextView tvDate;
    private TextView tvTime;
    private Spinner spinner;

    private Calendar calendar;

    private static final String[] SPINNER_OPTIONS = {"Uma vez","Todo dia","De 12 em 12 horas","De 8 em 8 horas","De 6 em 6 horas","De 4 em 4 horas"};
    private int times = 0;

    public EditNotificationDialog(Context context, int notificationIndex) {
        this.notificationIndex = notificationIndex;
        notification = NotificationSingleton.getInstance().getNotifications().get(notificationIndex);
        this.context = context;
        calendar = Calendar.getInstance();
    }

    public AlertDialog setDialog(final ListView listView){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.notification_dialog_layout, null);

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        etName = view.findViewById(R.id.notificationNameDialog);
        etAnnotations = view.findViewById(R.id.notificationAnnotationDialog);
        tvDate = view.findViewById(R.id.notificationDateDialog);
        tvTime = view.findViewById(R.id.notificationTimeDialog);
        spinner = view.findViewById(R.id.notificationSpinner);

        calendar = notification.getCalendar();

        etName.setText(notification.getName());
        etAnnotations.setText(notification.getDescription());
        tvDate.setText(dateFormat.format(notification.getCalendar().getTime()));
        tvTime.setText(timeFormat.format(notification.getCalendar().getTime()));
        times = notification.getFrequency();

        initSpinner();

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DAY_OF_MONTH,i2);
                        tvDate.setText(dateFormat.format(calendar.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);
                        tvTime.setText(timeFormat.format(calendar.getTime()));
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Lembrete");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notification.setName(etName.getText().toString());
                notification.setDescription(etAnnotations.getText().toString());
                notification.setCalendar(calendar);
                notification.setFrequency(times);
                notification.setNotification(context);
                //NotificationSingleton.getInstance().saveNotifications(context);
                listView.setAdapter(new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(),context));
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                NotificationSingleton.getInstance().removeNotification(alarmManager, notificationIndex);
                listView.setAdapter(new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(),context));
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setOwnerActivity((Activity) context);
        return alertDialog;
    }

    public void initSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,SPINNER_OPTIONS);
        spinner.setAdapter(adapter);
        switch (notification.getFrequency()){
            case 0:
                spinner.setSelection(0);
                break;
            case 1:
                spinner.setSelection(1);
                break;
            case 2:
                spinner.setSelection(2);
                break;
            case 3:
                spinner.setSelection(3);
                break;
            case 4:
                spinner.setSelection(4);
                break;
            case 6:
                spinner.setSelection(5);
                break;
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId() == R.id.notificationSpinner){
                    String selectedOption = SPINNER_OPTIONS[i];
                    if(selectedOption.equals("Uma vez")){
                        times = 0;
                    } else if(selectedOption.equals("Todo dia")){
                        times = 1;
                    } else if(selectedOption.equals("De 12 em 12 horas")){
                        times = 2;
                    } else if(selectedOption.equals("De 8 em 8 horas")){
                        times = 3;
                    } else if(selectedOption.equals("De 6 em 6 horas")){
                        times = 4;
                    } else if(selectedOption.equals("De 4 em 4 horas")){
                        times = 6;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
