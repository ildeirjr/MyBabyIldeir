package br.ufop.ildeir.mybabyildeir.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.objects.Task;

/**
 * Created by Ildeir on 09/06/2018.
 */

public class TaskAdapter extends BaseAdapter{


    private ArrayList<Task> items;
    private ArrayList<Task> tasks;
    private Context context;
    private boolean filterTypeActive;
    private boolean filterDateActive;

    public TaskAdapter(ArrayList<Task> tasks, Context context) {
        items = tasks;
        this.tasks = tasks;
        this.context = context;
        filterTypeActive = false;
        filterDateActive = false;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Task task = tasks.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.task_layout,null);

        ImageView imageView = v.findViewById(R.id.taskImg);
        if(task.getTaskType() == Task.MAMADA){
            imageView.setImageResource(R.drawable.mamada);
        }else if(task.getTaskType() == Task.MAMADEIRAS){
            imageView.setImageResource(R.drawable.mamadeira);
        }else if(task.getTaskType() == Task.FRALDA_SUJA){
            imageView.setImageResource(R.drawable.fralda);
        }else if(task.getTaskType() == Task.TEMPO_DORMINDO){
            imageView.setImageResource(R.drawable.sono);
        }else if(task.getTaskType() == Task.MEDICAMENTOS){
            imageView.setImageResource(R.drawable.medicamento);
        }else if(task.getTaskType() == Task.OUTROS){
            imageView.setImageResource(R.drawable.outros);
        }

        TextView tvTaskName = v.findViewById(R.id.taskName);
        if(task.getTaskType() == Task.MAMADA){
            tvTaskName.setText("Mamada");
        }else if(task.getTaskType() == Task.MAMADEIRAS){
            tvTaskName.setText("Mamadeira");
        }else if(task.getTaskType() == Task.FRALDA_SUJA){
            tvTaskName.setText("Fralda suja");
        }else if(task.getTaskType() == Task.TEMPO_DORMINDO){
            tvTaskName.setText("Tempo dormindo");
        }else if(task.getTaskType() == Task.MEDICAMENTOS){
            tvTaskName.setText("Medicamento");
        }else if(task.getTaskType() == Task.OUTROS){
            tvTaskName.setText("Outro");
        }


        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar c = task.getTime();
        TextView tvTaskDate = v.findViewById(R.id.taskDate);
        tvTaskDate.setText(dateFormat.format(c.getTime()) + " - " + timeFormat.format(c.getTime()));

        return v;
    }

    public Filter getTypeFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                int index = Integer.valueOf(charSequence.toString());
                FilterResults filterResults = new FilterResults();
                if(filterTypeActive) {
                    ArrayList<Task> filteredTasks = new ArrayList<>();
                    for (Task t : items) {
                        if (t.getTaskType() == index) {
                            filteredTasks.add(t);
                        }
                    }
                    filterResults.count = filteredTasks.size();
                    filterResults.values = filteredTasks;
                }else{
                    filterResults.count = items.size();
                    filterResults.values = items;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tasks = (ArrayList<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public Filter getDateFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = simpleDateFormat.parse(charSequence.toString());
                    calendar.setTime(date);
                    FilterResults filterResults = new FilterResults();
                    if(filterDateActive) {
                        ArrayList<Task> filteredTasks = new ArrayList<>();
                        for (Task t : items) {
                            if (t.getTime().get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                                t.getTime().get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                                t.getTime().get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                                filteredTasks.add(t);
                            }
                        }
                        filterResults.count = filteredTasks.size();
                        filterResults.values = filteredTasks;
                    }else{
                        filterResults.count = items.size();
                        filterResults.values = items;
                    }
                    return filterResults;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tasks = (ArrayList<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public boolean isFilterTypeActive() {
        return filterTypeActive;
    }

    public void setFilterTypeActive(boolean filterTypeActive) {
        this.filterTypeActive = filterTypeActive;
    }

    public boolean isFilterDateActive() {
        return filterDateActive;
    }

    public void setFilterDateActive(boolean filterDateActive) {
        this.filterDateActive = filterDateActive;
    }
}
