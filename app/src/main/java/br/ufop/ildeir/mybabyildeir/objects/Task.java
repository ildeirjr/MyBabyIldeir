package br.ufop.ildeir.mybabyildeir.objects;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Ildeir on 09/06/2018.
 */

public class Task implements Serializable{

    public static final int MAMADA = 0;
    public static final int MAMADEIRAS = 1;
    public static final int FRALDA_SUJA = 2;
    public static final int TEMPO_DORMINDO = 3;
    public static final int MEDICAMENTOS = 4;
    public static final int OUTROS = 5;

    private int taskType;
    private Calendar time;
    private String duration;
    private String qtd;
    private String annotations;
    private String medicationName;

    public Task(int taskType, Calendar time, String duration, String qtd, String annotations, String medicationName) {
        this.taskType = taskType;
        this.time = time;
        this.duration = duration;
        this.qtd = qtd;
        this.annotations = annotations;
        this.medicationName = medicationName;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
}
