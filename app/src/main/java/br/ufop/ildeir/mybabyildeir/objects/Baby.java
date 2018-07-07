package br.ufop.ildeir.mybabyildeir.objects;

import java.io.Serializable;

/**
 * Created by Ildeir on 08/06/2018.
 */

public class Baby implements Serializable{

    private String name;
    private int day, month, year;
    private char sex;
    private boolean isSeted;

    public Baby(String name, int day, int month, int year, char sex, boolean isSeted) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sex = sex;
        this.isSeted = isSeted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public boolean isSeted() {
        return isSeted;
    }

    public void setSeted(boolean seted) {
        isSeted = seted;
    }
}
