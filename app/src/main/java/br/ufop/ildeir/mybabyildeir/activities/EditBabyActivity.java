package br.ufop.ildeir.mybabyildeir.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.miscellaneous.DateDialog;
import br.ufop.ildeir.mybabyildeir.objects.Baby;
import br.ufop.ildeir.mybabyildeir.singletons.BabySingleton;
import br.ufop.ildeir.mybabyildeir.singletons.TaskSingleton;

public class EditBabyActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etBirthday;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private DateDialog dateDialog;
    int day,month,year;
    Baby baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);

        etName = findViewById(R.id.editNameBaby);
        etBirthday = findViewById(R.id.editBirthdayBaby);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonMale = findViewById(R.id.radioBtnMale);
        radioButtonFemale = findViewById(R.id.radioBtnFemale);

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog = new DateDialog(view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dateDialog.show(ft,"DatePicker");
            }
        });

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        etBirthday.setText(day + "/" + month + "/" + year);

        baby = BabySingleton.getInstance().getBaby();

        etName.setText(baby.getName());
        etBirthday.setText(baby.getDay() + "/" + baby.getMonth() + "/" + baby.getYear());
        if(baby.getSex() == 'm'){
            radioButtonMale.toggle();
        } else {
            radioButtonFemale.toggle();
        }

    }

    public void addBaby(View view) {

        if(radioGroup.getCheckedRadioButtonId() == R.id.radioBtnMale){
            baby.setSex('m');
        }else baby.setSex('f');

        if(dateDialog != null){
            day = dateDialog.getDay();
            month = dateDialog.getMonth();
            year = dateDialog.getYear();
        }

        baby.setDay(day);
        baby.setMonth(month);
        baby.setYear(year);
        baby.setName(etName.getText().toString());
        baby.setSeted(true);
        BabySingleton.getInstance().saveBaby(this);
        TaskSingleton.getInstance().clearTasks();
        TaskSingleton.getInstance().saveTasks(this);

        startActivity(new Intent(this,HomeActivity.class));
        finish();

    }

}
