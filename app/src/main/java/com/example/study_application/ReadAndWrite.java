package com.example.study_application;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReadAndWrite extends AppCompatActivity {

    private ContextWrapper contextWrapper;

    private final Context context;

    public ReadAndWrite(Context context) {
        this.context = context;
    }

    public String[][] readTaskNameData(String file, Boolean TextOrBody) {
        String fileData = readFile(file);
        String[] DataString = fileData.split("\n");

        String StringArray = Arrays.toString(DataString);
        String[] StringArrays = StringArray.split(",");

        if (!TextOrBody) {
            String[][] textBodyData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String IdSpecifications = values[0];
                String TaskSpecification = values[1];

                String[] valueSpecificationData = new String[]{IdSpecifications, TaskSpecification};

                textBodyData[i] = valueSpecificationData;
            }
            return textBodyData;
        } else {
            String[][] textNameData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String IdName = values[0];
                String taskName = values[1];
                String taskCompletion = values[2];
                String timeRequired = values[3];

                String[] valueNameData = new String[]{IdName, taskName, taskCompletion, timeRequired};

                textNameData[i] = valueNameData;
            }
            return textNameData;
        }
    }

    //code already explained
    public String readFile(String file) {
        contextWrapper = new ContextWrapper(context);
        String text = "";
        try {
            FileInputStream fis = contextWrapper.openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error reading file", Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    //code already explained
    public void write(String file, String textData) {
        contextWrapper = new ContextWrapper(context);
        try {
            FileOutputStream fos = contextWrapper.openFileOutput(file, MODE_APPEND);
            fos.write(textData.getBytes());
            fos.close();
            Toast.makeText(context, "saving file successful", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceLines(String oldFileLine, String newFileLine, String fileName) {
        contextWrapper = new ContextWrapper(context);
        String[] fileDataLines = readFile(fileName).split("\n");

        try {
            FileOutputStream fos = contextWrapper.openFileOutput(fileName, MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fileContent = Arrays.asList(fileDataLines);
        //finds specific line inside txt file then replaces the file and puts back
        // into the list which then the list is pasted back inside the txt file
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldFileLine)) {
                fileContent.set(i, newFileLine);
                break;
            }
        }

        for (int i = 0; i < fileContent.size(); i++) {
            write(fileName, fileContent.get(i));
            if (!(newFileLine.equals(""))){
                write(fileName, "\n");
            }
        }
    }
}
