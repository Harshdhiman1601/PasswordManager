package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Original array
        int[] originalArray = {1, 2, 3, 4, 5};

        // Convert array to list
        List<Integer> newArray = convertArrayToList(originalArray);

        // Display the new list
        Password password = findViewById(R.id.);
        Password.setText("New Array: " + newArray);
    }

    private List<Integer> convertArrayToList(int[] array) {
        // Method 1: Using a loop
        List<Integer> newList = new ArrayList<>();
        for (int value : array) {
            newList.add(value);
        }

        // Method 2: Using ArrayList constructor
        // List<Integer> newList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        return newList;
        }
    }
}