package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(this,
                AppDatabase.class, "myDatabase").allowMainThreadQueries().build();
        PasswordDao passwordDao = db.passwordDao();
//        for(int i = 0; i < 3; i++){
//            Password password = new Password();
//            password.name = "name" + Integer.toString(i);
//            password.password = "pass" + Integer.toString(i);
//
//            passwordDao.insert(password);
//        }
        List<Password> passwords = passwordDao.getAll();
        for(int i = 0; i < passwords.size(); i++){
            Log.i("Name: ", (passwords.get(i)).name);
            Log.i("Password: ", passwords.get(i).password);

        }
//        Password passwordToBeDeleted = passwords.get(0);
//        passwordDao.DEL(passwordToBeDeleted);

        Password passwordToBeUpdated = new Password();
        passwordToBeUpdated = passwords.get(0);
        passwordToBeUpdated.name = "NewName";
        passwordToBeUpdated.password = "NewPass";

        passwordDao.Update(passwordToBeUpdated);
        List<Password> newPasswords = passwordDao.getAll();
        for(int i = 0; i < newPasswords.size(); i++){
            Log.i("Name: ", (newPasswords.get(i)).name);
            Log.i("Password: ", newPasswords.get(i).password);

        }
    }
}