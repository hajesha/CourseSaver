package com.example.hajes.coursesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                                              addText(courseS);
                                              EditText building = findViewById(R.id.building);
                                              String buildingS = building.getText().toString();
                                              addText(buildingS);
                                              getText(v);
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
        //getText(view);
    }

    //This method will delete file
    public void deleteFile(){
        getApplicationContext().deleteFile("schedule.txt");
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

    public void getText(View view)
    {
        StringBuilder text = new StringBuilder();
        setContentView(R.layout.activity_display);
        String mLine;
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

}
