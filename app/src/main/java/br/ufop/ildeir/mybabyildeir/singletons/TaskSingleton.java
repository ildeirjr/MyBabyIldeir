package br.ufop.ildeir.mybabyildeir.singletons;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.ufop.ildeir.mybabyildeir.objects.Baby;
import br.ufop.ildeir.mybabyildeir.objects.Task;

/**
 * Created by Ildeir on 09/06/2018.
 */

public class TaskSingleton {

    public static TaskSingleton singleton = null;

    private static ArrayList<Task> tasks;

    public static TaskSingleton getInstance(){
        if(singleton == null){
            singleton = new TaskSingleton();
        }
        return singleton;
    }

    private TaskSingleton(){
        tasks = new ArrayList<>();
    }

    public void clearTasks(){
        tasks.clear();
    }

    public void saveTasks(Context context){
        FileOutputStream fos;
        try{
            fos = context.openFileOutput("tasks.tmp",Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks(Context context){
        FileInputStream fis;
        try{
            fis = context.openFileInput("tasks.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        TaskSingleton.tasks = tasks;
    }
}
