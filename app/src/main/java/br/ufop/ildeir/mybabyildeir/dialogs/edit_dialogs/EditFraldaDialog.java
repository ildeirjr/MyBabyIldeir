package br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs;

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

public class EditFraldaDialog {

    int taskIndex;
    Task task;

    private AlertDialog alertDialog;
    private Context context;
    private View view;

    private TextView tvDate;
    private TextView tvStartTime;
    private EditText etAnnotations;

    private Calendar calendar = Calendar.getInstance();
    private Calendar startTime;

    public EditFraldaDialog(Context context, int taskIndex) {
        this.taskIndex = taskIndex;
        task = TaskSingleton.getInstance().getTasks().get(taskIndex);
        this.context = context;
        startTime = task.getTime();
    }

    public AlertDialog setDialog(final ListView listView) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.fralda_layout, null);

        tvDate = view.findViewById(R.id.fraldaDate);
        tvStartTime = view.findViewById(R.id.fraldaTime);
        etAnnotations = view.findViewById(R.id.fraldaAnnotations);

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        tvDate.setText(dateFormat.format(startTime.getTime()));
        tvStartTime.setText(timeFormat.format(startTime.getTime()));
        etAnnotations.setText(task.getAnnotations());

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startTime.set(Calendar.YEAR, i);
                        startTime.set(Calendar.MONTH, i1);
                        startTime.set(Calendar.DAY_OF_MONTH, i2);
                        tvDate.setText(dateFormat.format(startTime.getTime()));
                    }
                }, startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        startTime.set(Calendar.HOUR_OF_DAY, i);
                        startTime.set(Calendar.MINUTE, i1);
                        tvStartTime.setText(timeFormat.format(startTime.getTime()));
                    }
                }, startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Fralda suja");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                task.setTime(startTime);
                task.setAnnotations(etAnnotations.getText().toString());
                Collections.sort(TaskSingleton.getInstance().getTasks(),new TaskDateComparator());
                TaskSingleton.getInstance().saveTasks(context);
                listView.setAdapter(new TaskAdapter(TaskSingleton.getInstance().getTasks(), context));
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        builder.setNeutralButton("Exlcuir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskSingleton.getInstance().getTasks().remove(taskIndex);
                Collections.sort(TaskSingleton.getInstance().getTasks(),new TaskDateComparator());
                TaskSingleton.getInstance().saveTasks(context);
                listView.setAdapter(new TaskAdapter(TaskSingleton.getInstance().getTasks(),context));
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setOwnerActivity((Activity) context);
        return alertDialog;

    }

}
