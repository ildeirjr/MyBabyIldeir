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

/**
 * Created by Ildeir on 08/06/2018.
 */

public class BabySingleton {

    public static BabySingleton singleton = null;

    private static Baby baby;

    public static BabySingleton getInstance(){
        if(singleton == null){
            singleton = new BabySingleton();
        }
        return singleton;
    }

    private BabySingleton(){
        baby = new Baby("",0,0,0,'-',false);
    }

    public void saveBaby(Context context){
        FileOutputStream fos;
        try{
            fos = context.openFileOutput("baby.tmp",Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(baby);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBaby(Context context){
        FileInputStream fis;
        try{
            fis = context.openFileInput("baby.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            baby = (Baby) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Baby getBaby() {
        return baby;
    }

    public void setBaby(Baby baby) {
        BabySingleton.baby = baby;
    }
}
