package br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.adapters.TaskAdapter;
import br.ufop.ildeir.mybabyildeir.miscellaneous.TaskDateComparator;
import br.ufop.ildeir.mybabyildeir.objects.Task;
import br.ufop.ildeir.mybabyildeir.singletons.TaskSingleton;

/**
 * Created by Ildeir on 10/06/2018.
 */

public class AddTempoDormindoDialog {

    private AlertDialog alertDialog;
    private Context context;
    private View view;

    private TextView tvDate;
    private TextView tvTime;
    private EditText etDuration;
    private EditText etAnnotations;

    private Calendar calendar = Calendar.getInstance();
    private Calendar time;

    public AddTempoDormindoDialog(Context context) {
        this.context = context;
        time = calendar;
    }

    public AlertDialog setDialog(final ListView listView) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.tempo_dormindo_layout, null);

        tvDate = view.findViewById(R.id.sonoStartDate);
        tvTime = view.findViewById(R.id.sonoStartTime);
        etDuration = view.findViewById(R.id.sonoDuration);
        etAnnotations = view.findViewById(R.id.sonoAnnotations);

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        tvDate.setText(dateFormat.format(time.getTime()));
        tvTime.setText(timeFormat.format(time.getTime()));

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        time.set(Calendar.YEAR, i);
                        time.set(Calendar.MONTH, i1);
                        time.set(Calendar.DAY_OF_MONTH, i2);
                        tvDate.setText(dateFormat.format(time.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time.set(Calendar.HOUR_OF_DAY, i);
                        time.set(Calendar.MINUTE, i1);
                        tvTime.setText(timeFormat.format(time.getTime()));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tempo dormindo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                TaskSingleton.getInstance().getTasks().add(new Task(Task.TEMPO_DORMINDO, time, etDuration.getText().toString(), "", etAnnotations.getText().toString(),""));
                Collections.sort(TaskSingleton.getInstance().getTasks(),new TaskDateComparator());
                TaskSingleton.getInstance().saveTasks(context);
                listView.setAdapter(new TaskAdapter(TaskSingleton.getInstance().getTasks(), context));
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setOwnerActivity((Activity) context);
        return alertDialog;

    }
}
