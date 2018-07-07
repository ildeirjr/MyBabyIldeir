package br.ufop.ildeir.mybabyildeir.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.ufop.ildeir.mybabyildeir.R;
import br.ufop.ildeir.mybabyildeir.objects.Baby;
import br.ufop.ildeir.mybabyildeir.singletons.BabySingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BabySingleton.getInstance().loadBaby(this);

        setContentView(R.layout.activity_main);



        if(!BabySingleton.getInstance().getBaby().isSeted()){
            startActivity(new Intent(this,AddBabyActivity.class));
            finish();
        }else{
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }

    }
}
