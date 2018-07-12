package br.ufop.ildeir.mybabyildeir.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.adapters.NotificationAdapter;
import br.ufop.ildeir.mybabyildeir.adapters.TaskAdapter;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddFraldaDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddMamadaDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddMamadeiraDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddMedicamentoDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddOutrosDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.add_dialogs.AddTempoDormindoDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditFraldaDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditMamadaDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditMamadeiraDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditMedicamentoDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditOutrosDialog;
import br.ufop.ildeir.mybabyildeir.dialogs.edit_dialogs.EditTempoDormindoDialog;
import br.ufop.ildeir.mybabyildeir.objects.Task;
import br.ufop.ildeir.mybabyildeir.singletons.BabySingleton;
import br.ufop.ildeir.mybabyildeir.singletons.NotificationSingleton;
import br.ufop.ildeir.mybabyildeir.singletons.TaskSingleton;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private TextView babyName;
    private TextView babyBirthday;
    private ImageView babySex;

    private TaskAdapter taskAdapter;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private TextView tvList;
    private AlertDialog alertDialog;

    private ImageButton clearTasks;
    private FloatingActionButton fab;

    private MediaPlayer mediaPlayer;
    private boolean isPlayingSound = false;

    private CheckBox typeCheckBox;
    private CheckBox dateCheckBox;
    private Spinner spinner;
    private TextView filterDate;
    private EditText etDate;

    private static final String[] TASK_NAMES = {"Mamada", "Mamadeiras", "Fralda suja", "Tempo dormindo", "Medicamentos", "Outros"};

    private boolean isFabSeted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TaskSingleton.getInstance().loadTasks(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navView);
        babyName = navigationView.getHeaderView(0).findViewById(R.id.textBabyName);
        babyBirthday = navigationView.getHeaderView(0).findViewById(R.id.textBabyBirthday);
        babySex = navigationView.getHeaderView(0).findViewById(R.id.imgBabySex);

        babyName.setText(BabySingleton.getInstance().getBaby().getName());
        babyBirthday.setText("Nasceu em " + BabySingleton.getInstance().getBaby().getDay() + "/" + BabySingleton.getInstance().getBaby().getMonth()
        + "/" + BabySingleton.getInstance().getBaby().getYear());

        if(BabySingleton.getInstance().getBaby().getSex() == 'm'){
            babySex.setImageResource(R.drawable.male);
        }else babySex.setImageResource(R.drawable.female);

        taskAdapter = new TaskAdapter(TaskSingleton.getInstance().getTasks(),this);
        listView = findViewById(R.id.tasksListView);
        listView.setEmptyView(findViewById(R.id.tvEmptyListView));
        listView.setAdapter(taskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (TaskSingleton.getInstance().getTasks().get(i).getTaskType()){
                    case Task.MAMADA:
                        EditMamadaDialog editMamadaDialog = new EditMamadaDialog(view.getContext(),i);
                        editMamadaDialog.setDialog(listView).show();
                        break;
                    case Task.MAMADEIRAS:
                        EditMamadeiraDialog editMamadeiraDialog = new EditMamadeiraDialog(view.getContext(),i);
                        editMamadeiraDialog.setDialog(listView).show();
                        break;
                    case Task.FRALDA_SUJA:
                        EditFraldaDialog editFraldaDialog = new EditFraldaDialog(view.getContext(),i);
                        editFraldaDialog.setDialog(listView).show();
                        break;
                    case Task.TEMPO_DORMINDO:
                        EditTempoDormindoDialog editTempoDormindoDialog = new EditTempoDormindoDialog(view.getContext(),i);
                        editTempoDormindoDialog.setDialog(listView).show();
                        break;
                    case Task.MEDICAMENTOS:
                        EditMedicamentoDialog editMedicamentoDialog = new EditMedicamentoDialog(view.getContext(),i);
                        editMedicamentoDialog.setDialog(listView).show();
                        break;
                    case Task.OUTROS:
                        EditOutrosDialog editOutrosDialog = new EditOutrosDialog(view.getContext(),i);
                        editOutrosDialog.setDialog(listView).show();
                        break;
                }

            }
        });

        tvList = findViewById(R.id.textList);
        tvList.setText("Atividades de " + BabySingleton.getInstance().getBaby().getName());

        clearTasks = findViewById(R.id.btnClearTasks);

        fab = findViewById(R.id.filterBtn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.toggleSound:
                if(isPlayingSound){
                    item.setIcon(android.R.drawable.ic_media_play);
                    mediaPlayer.stop();
                    isPlayingSound = false;
                } else {
                    item.setIcon(android.R.drawable.ic_media_pause);
                    mediaPlayer = MediaPlayer.create(this, R.raw.som_utero);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    isPlayingSound = true;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickMamada(View view) {
        AddMamadaDialog mamadaDialog = new AddMamadaDialog(this);
        mamadaDialog.setDialog(listView).show();
    }

    public void onClickMamadeira(View view) {
        AddMamadeiraDialog addMamadeiraDialog = new AddMamadeiraDialog(this);
        addMamadeiraDialog.setDialog(listView).show();
    }

    public void onClickFralda(View view) {
        AddFraldaDialog addFraldaDialog = new AddFraldaDialog(this);
        addFraldaDialog.setDialog(listView).show();
    }

    public void onClickSono(View view) {
        AddTempoDormindoDialog addTempoDormindoDialog = new AddTempoDormindoDialog(this);
        addTempoDormindoDialog.setDialog(listView).show();
    }

    public void onClickMedicamento(View view) {
        AddMedicamentoDialog addMedicamentoDialog = new AddMedicamentoDialog(this);
        addMedicamentoDialog.setDialog(listView).show();
    }

    public void onClickOutros(View view) {
        AddOutrosDialog addOutrosDialog = new AddOutrosDialog(this);
        addOutrosDialog.setDialog(listView).show();
    }

    public void editBaby(MenuItem item){
        startActivity(new Intent(this,EditBabyActivity.class));
    }

    public void deleteBaby(MenuItem item){
        BabySingleton.getInstance().getBaby().setSeted(false);
        BabySingleton.getInstance().saveBaby(this);
        startActivity(new Intent(this,AddBabyActivity.class));
        finish();
    }

    public void notificationOptionSelected(MenuItem item){
        startActivity(new Intent(this, NotificationsActivity.class));
    }



    public void clearAllTasks(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Deseja excluir todas as atividades?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                TaskSingleton.getInstance().clearTasks();
                TaskSingleton.getInstance().saveTasks(HomeActivity.this);
                listView.setAdapter(taskAdapter);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }

    public void openFilterDialog(View view) {
        if (!isFabSeted) {
            LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.filter_layout, null);

            taskAdapter.setFilterTypeActive(false);
            taskAdapter.setFilterDateActive(false);

            listView.setEmptyView(findViewById(R.id.tvEmptyFilter));

            typeCheckBox = view.findViewById(R.id.typeCheckBox);
            dateCheckBox = view.findViewById(R.id.dateCheckBox);
            spinner = view.findViewById(R.id.spinner);
            filterDate = view.findViewById(R.id.filterDate);
            etDate = view.findViewById(R.id.filterDate);

            filterDate.setText(simpleDateFormat.format(calendar.getTime()));

            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            calendar.set(Calendar.YEAR, i);
                            calendar.set(Calendar.MONTH, i1);
                            calendar.set(Calendar.DAY_OF_MONTH, i2);
                            filterDate.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });

            initSpinner();

            typeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        taskAdapter.setFilterTypeActive(true);
                    } else {
                        taskAdapter.setFilterTypeActive(false);
                    }
                }
            });

            dateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        taskAdapter.setFilterDateActive(true);
                    } else {
                        taskAdapter.setFilterDateActive(false);
                    }
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Filtrar atividades");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (typeCheckBox.isChecked() || dateCheckBox.isChecked()) {
                        taskAdapter.filter(String.valueOf(spinner.getSelectedItemPosition()), simpleDateFormat.format(calendar.getTime()));
                        //listView.setAdapter(new NotificationAdapter(NotificationSingleton.getInstance().getNotifications(), getApplicationContext()));
                        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_red)));
                        fab.setImageResource(R.drawable.ic_close);
                        isFabSeted = true;
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();
        } else {
            taskAdapter = new TaskAdapter(TaskSingleton.getInstance().getTasks(), this);
            listView.setAdapter(taskAdapter);
            listView.setEmptyView(findViewById(R.id.tvEmptyListView));
            fab.setImageResource(R.drawable.ic_filter);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            isFabSeted = false;
        }
    }

    public void initSpinner(){
        ArrayAdapter<String> adapterSpinner1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, TASK_NAMES);
        spinner.setAdapter(adapterSpinner1);
    }

}
