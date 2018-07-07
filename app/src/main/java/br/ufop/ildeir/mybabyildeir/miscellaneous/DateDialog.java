package br.ufop.ildeir.mybabyildeir.miscellaneous;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import br.ufop.ildeir.mybabyildeir.R;

/**
 * Created by Ildeir on 08/06/2018.
 */

@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText etDate;
    private int day, month, year;
    private boolean flagDateSeted;

    public DateDialog(View v) {
        etDate = v.findViewById(R.id.editBirthdayBaby);
        flagDateSeted = false;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String data = i2 + "/" + (i1 + 1) + "/" + i;
        etDate.setText(data);
        day = i2;
        month = i1 + 1;
        year = i;
        setFlagDateSeted(true);
    }

    public EditText getEtDate() {
        return etDate;
    }

    public void setEtDate(EditText etDate) {
        this.etDate = etDate;
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

    public boolean isFlagDateSeted() {
        return flagDateSeted;
    }

    public void setFlagDateSeted(boolean flagDateSeted) {
        this.flagDateSeted = flagDateSeted;
    }
}

