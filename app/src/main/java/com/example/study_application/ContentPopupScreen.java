package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ContentPopupScreen extends AppCompatActivity {
    public static final String EXTRA_STRING_ID = "package com.example.study_application";

    private Intent toTaskScreen;
    private Intent getInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_poppup_screen);

        ReadAndWrite readAndWrite = new ReadAndWrite(ContentPopupScreen.this);

        //reads file data and saves it into arrays
        String[][] textNameData = readAndWrite.readTaskNameData("TaskNames.txt", true);
        String[][] textBodyData = readAndWrite.readTaskNameData("TaskSpecifications.txt", false);

        //finds and links the different objects within the screen
        Button startTask = findViewById(R.id.StartTask);
        TextView taskNames = findViewById(R.id.taskName);
        TextView taskSpecification = findViewById(R.id.taskSpecifications);
        TextView taskCompletion = findViewById(R.id.taskCompletion);
        TextView taskTimes = findViewById(R.id.taskTime);

        //creates a link with the next screen
        toTaskScreen = new Intent(this, TaskScreen.class);

        //gets sent data from last screen
        getInformation = getIntent();
        String number = getInformation.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER);


        //changes the initial text and puts in the text from the data in the arrays
        for(int i = 1; i < textNameData.length; i++){
            if (textNameData[i][0].equals(number)){
                taskNames.setText(textNameData[i][1]);
                taskSpecification.setText(textBodyData[i][1]);
                taskCompletion.setText(textNameData[i][2]);
                taskTimes.setText(textNameData[i][3]);
            }
        }

        //checks to see if the Start Task button has been pressed
        startTask.setOnClickListener(v -> sendData());

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void sendData() {
        toTaskScreen.putExtra(EXTRA_STRING_ID, getInformation.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER));
        startActivity(toTaskScreen);
    }
}