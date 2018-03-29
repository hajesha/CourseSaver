package com.example.hajes.coursesaver;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //This method will add a course to the existing class schedule or create a new schedule
    public void addCourse(View view){
        setContentView(R.layout.activity_addcourse);
        //deleteFile();
        if (!(new File(getApplicationContext().getFilesDir(), "schedule.txt").exists()))
        {
            try {
            FileOutputStream outputStream;
            outputStream = openFileOutput("schedule.txt", Context.MODE_PRIVATE);
            outputStream.write("My Schedule\n".getBytes());
            outputStream.close();
            }
            catch (Exception e){}
        }
        try {

            FileInputStream fis = getApplicationContext().openFileInput("schedule.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            Button button = (Button) findViewById(R.id.button4);
            button.setOnClickListener(new View.OnClickListener() {

                                          public void onClick(View v) {

                                              EditText course = findViewById(R.id.course);
                                              String courseS = course.getText().toString();
                                              addText(courseS.toUpperCase());
                                              EditText building = findViewById(R.id.building);
                                              String buildingS = building.getText().toString();
                                              addText(buildingS.toUpperCase());
                                              //getText(v);
                                              String textMessage = "Course: "+ courseS + " has been added"  ;
                                              Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_LONG).show();
                                          }
                                      });
            bufferedReader.close();

        } catch (Exception e) {
            addText("EEK\n");
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getText(view);s
    }

    //This method will delete file
    public void deleteFile(View view){
        getApplicationContext().deleteFile("schedule.txt");
        String textMessage = "Your file has been deleted " ;
        Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_LONG).show();

    }

    //This method will add the desired message to the file and append with a new line
    public void addText ( String message)
    {
        try {
            FileOutputStream outputStream = openFileOutput("schedule.txt", Context.MODE_APPEND);

            outputStream.write(message.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method will add the desired message to the file and append with a new line
    public void deleteCourse ( View view)
    {
        setContentView(R.layout.activity_deletecourse);
        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String mLine="";
                String text ="";

                EditText course = findViewById(R.id.editText);
                String message = course.getText().toString().toUpperCase();

                if (findHelper(message) != null) {
                    try {

                        FileInputStream fis = getApplicationContext().openFileInput("schedule.txt");
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader bufferedReader = new BufferedReader(isr);


                        while ((mLine = bufferedReader.readLine()) != null) {
                            if ( mLine.equals(message))
                            {
                                bufferedReader.readLine();
                                continue;
                            }
                            text = text+ mLine + '\n';
                        }
                        bufferedReader.close();
                        FileOutputStream outputStream;
                        outputStream = openFileOutput("schedule.txt", Context.MODE_PRIVATE);
                        outputStream.write(text.getBytes());
                        outputStream.close();
                        String textMessage = "Course: "+ message + " has been deleted"  ;
                        Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_LONG).show();

            } catch (Exception e) {e.printStackTrace();}

                }}});
    }

    public void getText(View view)
    {
        StringBuilder text = new StringBuilder();
        setContentView(R.layout.activity_display);
        String mLine;

        if (!(new File(getApplicationContext().getFilesDir(), "schedule.txt").exists())) {
            try {
                FileOutputStream outputStream;
                outputStream = openFileOutput("schedule.txt", Context.MODE_PRIVATE);
                outputStream.write("My Schedule\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
            }
        }

        try {

            FileInputStream fis = getApplicationContext().openFileInput("schedule.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);



            while ((mLine = bufferedReader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
            bufferedReader.close();
        } catch (Exception e) {
            text.append("lost");
            e.printStackTrace();
        }

        TextView output= findViewById(R.id.textView);
        output.setText(text);
    }
    public void backMain(View view)
    {
        setContentView(R.layout.activity_main);
    }

    static String[] course;

    public String[] listCourses(){
        File schedule =new File(getApplicationContext().getFilesDir(), "schedule.txt");
        String mLine;
        ArrayList<String> courseAL = new ArrayList<String>();
        if (!(schedule.exists()))
            return null;
        else
        {
            FileInputStream fis;
            try {
                fis = getApplicationContext().openFileInput("schedule.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                final BufferedReader bufferedReader = new BufferedReader(isr);

                if ((bufferedReader.readLine()) == null)
                {return null;}//No classes have been added

                else {
                    while ((mLine = bufferedReader.readLine()) != null)
                    {
                        courseAL.add(mLine);
                        bufferedReader.readLine();
                    }
                }
            }catch (Exception e){return null;}
        }

        course = courseAL.toArray(new String[0]);

        return course;
    }

    public void findCourse ( View view ) {

        setContentView(R.layout.activity_findcourse);

        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EditText course = findViewById(R.id.course);
                String searchText = course.getText().toString().toUpperCase();
                String result = findHelper(searchText);
                TextView output = findViewById(R.id.textView2);
                if ( result != null) {
                    output.setText(result);

                }
                else {
                    output.setText("error");

                }
            }
        });


    }

    public String findHelper (String searchText)
    {
        String mLine;
        setContentView(R.layout.activity_findcourse);
        File schedule =new File(getApplicationContext().getFilesDir(), "schedule.txt");

        if (!(schedule.exists()))
            return null;
        else
        {
            FileInputStream fis;
            try {
                fis = getApplicationContext().openFileInput("schedule.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                final BufferedReader bufferedReader = new BufferedReader(isr);

                if ((bufferedReader.readLine()) == null)
                {return null;}//No classes have been added

                else {
                    while ((mLine = bufferedReader.readLine()) != null)
                        if (searchText.equals(mLine))
                            return (bufferedReader.readLine());
                    return null;
                    }
                }catch (Exception e){return null;}
            }
    }


    }

