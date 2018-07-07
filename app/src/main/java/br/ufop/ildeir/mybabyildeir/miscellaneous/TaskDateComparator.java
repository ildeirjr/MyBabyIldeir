package br.ufop.ildeir.mybabyildeir.miscellaneous;

import java.util.Comparator;

import br.ufop.ildeir.mybabyildeir.objects.Task;

/**
 * Created by Ildeir on 10/06/2018.
 */

public class TaskDateComparator implements Comparator<Task>{
    @Override
    public int compare(Task task, Task t1) {
        return t1.getTime().compareTo(task.getTime());
    }
}
