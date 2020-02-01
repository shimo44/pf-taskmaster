/**
 * """
 * Author: Carl Jones III
 * Owner:
 * License: N/A
 * Description: Life task tracking mobile application
 * """
 */
package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final List<String> list = new ArrayList<>();
    int[] taskColors;
    int[] textColors;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.userLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openUserLogin();
            }
        });

        final ListView listView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();

        int maxItems = 100;
        taskColors = new int[maxItems];
        textColors = new int[maxItems];

        for (int i = 0; i < maxItems; i++){
            int currentColor = i%3;

            if (currentColor == 0){
                taskColors[i] = Color.LTGRAY;
                textColors[i] = Color.BLACK;
            } else {
                taskColors[i] = Color.DKGRAY;
                textColors[i] = Color.WHITE;
            }
        }
        readInfo();
        adapter.setData(list, taskColors, textColors);
        listView.setAdapter(adapter);


        final Button newTaskButton = findViewById(R.id.newTaskButton);
        newTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final EditText taskInput = new EditText(MainActivity.this);
                taskInput.setSingleLine();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Start tracking a new task")
                        .setMessage("What task do you need to complete?")
                        .setView(taskInput)
                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskInput.getText().toString());
                                adapter.setData(list, taskColors, textColors);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        final Button deleteAllTasksButton = findViewById(R.id.deleteAllTasksButton);
        deleteAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete All Tasks?")
                        .setMessage("Are you sure you want to delete all your tasks?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                list.clear();
                                adapter.setData(list, taskColors, textColors);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Stop tracking this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                list.remove(position);
                                adapter.setData(list, taskColors, textColors);
                                saveInfo();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void openUserLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    class TextAdapter extends BaseAdapter{
        List<String> notesList = new ArrayList<>();
        int[] notesStatusColors;
        int[] notesTextColors;

        void setData(List<String> dataList, int [] dataBackgroundColors, int [] dataTextColors ){
            notesList.clear();
            notesList.addAll(dataList);

            notesStatusColors = new int[list.size()];
            notesTextColors = new int[list.size()];

            for (int i=0; i < list.size(); i++){
                notesStatusColors[i] = dataBackgroundColors[i];
                notesTextColors[i] = dataTextColors[i];
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent ){

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater)
                        MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item, parent, false);
            }

            final TextView textView = convertView.findViewById(R.id.task);

            textView.setBackgroundColor(taskColors[position]);
            textView.setTextColor(textColors[position]);
            textView.setText(notesList.get(position));

            return convertView;
        }
    }

    private void saveInfo(){
        try{
            File file = new File(this.getFilesDir(), "saved tasks");

            FileOutputStream fileOutput = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutput));

            for (int i = 0; i < list.size(); i++){
                bw.write(list.get(i));
                bw.newLine();
            }

            bw.close();
            fileOutput.close();
        } catch(Exception e){
            e.printStackTrace();;
        }
    }

    private void readInfo(){
        File file = new File(this.getFilesDir(), "saved tasks");
        if (!file.exists()){
            return;
        }

        try {
            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();

            while (line != null){
                list.add(line);
                line = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
