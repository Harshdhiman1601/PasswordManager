package com.example.passwordmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    List<String> passwordNames = new ArrayList<String>();
    int selected_item_index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(MainActivity.this,
                AppDatabase.class, "password-database").allowMainThreadQueries().build();
        setContentView(R.layout.activity_main);

        PasswordDao passwordDao = db.passwordDao();
        passwordDao.removeAll();
        List<Password> passwords = passwordDao.getAll();
        for(int i = 0; i < passwords.size(); i++){
            passwordNames.add(passwords.get(i).name);
        }

        ListView passwordList = findViewById(R.id.passwordList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passwordNames);
        passwordList.setAdapter(adapter);

        passwordList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view,
                            int index, long id)
                    {
                        // Show a Toast message with the name of
                        // the city
//                        Toast
//                                .makeText(getApplicationContext(),
//                                        "Long clicked on "
//                                                + mCity[index],
//                                        Toast.LENGTH_SHORT)
//                                .show();
                        for(int i = 0; i < passwordList.getChildCount(); i++){
                            if(index == i){
                                passwordList.getChildAt(i).setBackgroundColor(Color.GRAY);
                                selected_item_index = i;
                            }
                            else{
                                passwordList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                        Button view_button = (Button) findViewById(R.id.viewButton);
                        view_button.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

        passwordList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        selected_item_index = -1;
                        for(int i = 0; i < passwordList.getChildCount(); i++){
                            passwordList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        Button view_button = (Button) findViewById(R.id.viewButton);
                        view_button.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }

    public void refresh_password_list(){
        PasswordDao passwordDao = db.passwordDao();
        List<Password> passwords = passwordDao.getAll();
        passwordNames.clear();
        for(int i = 0; i < passwords.size(); i++){
            passwordNames.add(passwords.get(i).name);
        }

        ListView passwordList = findViewById(R.id.passwordList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passwordNames);
        passwordList.setAdapter(adapter);
    }

    public void show_add_dialog(View view){
        final AlertDialog.Builder dialog_builder = new AlertDialog.Builder(MainActivity.this);
        View add_dialog_view = getLayoutInflater().inflate(R.layout.add_dialog, null);

        final EditText name_text = (EditText) add_dialog_view.findViewById(R.id.name_text);
        final EditText password_text = (EditText) add_dialog_view.findViewById(R.id.password_text);
        Button cancel_button = (Button) add_dialog_view.findViewById(R.id.cancel_button);
        Button save_button = (Button) add_dialog_view.findViewById(R.id.save_button);

        dialog_builder.setView(add_dialog_view);

        final AlertDialog add_dialog = dialog_builder.create();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_dialog.dismiss();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password password = new Password();
                password.name = name_text.getText().toString();
                password.password = password_text.getText().toString();
                PasswordDao passwordDao = db.passwordDao();
                passwordDao.insert(password);
                refresh_password_list();
                add_dialog.dismiss();
            }
        });
        add_dialog.show();
    }

    public void show_view_dialog(View view){
        final AlertDialog.Builder dialog_builder = new AlertDialog.Builder(MainActivity.this);
        View view_dialog_view = getLayoutInflater().inflate(R.layout.view_dialog, null);
        View main_view = getLayoutInflater().inflate(R.layout.activity_main, null);

        final TextView name_label = (TextView) view_dialog_view.findViewById(R.id.name_label);
        final TextView password_label = (TextView) view_dialog_view.findViewById(R.id.password_label);
        Button ok_button = (Button) view_dialog_view.findViewById(R.id.ok_button);
        PasswordDao passwordDao = db.passwordDao();
        Password password = passwordDao.get(passwordNames.get(selected_item_index));


        name_label.setText(name_label.getText().toString() + password.name);
        password_label.setText(password_label.getText().toString() + password.password);
        dialog_builder.setView(view_dialog_view);

        final AlertDialog view_dialog = dialog_builder.create();

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_dialog.dismiss();
            }
        });
        view_dialog.show();
    }
}
