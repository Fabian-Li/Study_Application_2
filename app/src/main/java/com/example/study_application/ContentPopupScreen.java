package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        int actualNumber = Integer.parseInt(number);

        //changes the initial text and puts in the text from the data in the arrays
        taskNames.setText(textNameData[actualNumber][1].replace("_"," "));
        taskSpecification.setText(textBodyData[actualNumber][1].replace("_"," "));
        taskCompletion.setText(textNameData[actualNumber][2].replace("_"," "));
        taskTimes.setText(textNameData[actualNumber][3]);

        //checks to see if the Start Task button has been pressed
        startTask.setOnClickListener(v -> sendData());

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void sendData() {
        toTaskScreen.putExtra(EXTRA_STRING_ID, getInformation.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER));
        startActivity(toTaskScreen);
    }
}