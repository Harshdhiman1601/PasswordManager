package com.example.passwordmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ListView l;
    String[] tutorials
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "I SRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        l = findViewById(R.id.passwordList);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tutorials);
        l.setAdapter(arr);


    }
}
