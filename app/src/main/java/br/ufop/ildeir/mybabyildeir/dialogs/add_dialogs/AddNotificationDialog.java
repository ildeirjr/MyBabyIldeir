package br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs;

import android.app.Activity;
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

public class AddNotificationDialog {

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


    public AddNotificationDialog(Context context) {
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

        tvDate.setText(dateFormat.format(calendar.getTime()));
        tvTime.setText(timeFormat.format(calendar.getTime()));

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
                Notification notificationItem = new Notification(calendar, etName.getText().toString(), etAnnotations.getText().toString(), times);
                notificationItem.setNotification(context);
                NotificationSingleton.getInstance().getNotifications().add(notificationItem);
                //NotificationSingleton.getInstance().saveNotifications(context);
                listView.setAdapter(new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(),context));
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
