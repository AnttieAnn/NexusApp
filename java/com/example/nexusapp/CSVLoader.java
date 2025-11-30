package com.example.nexusapp;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CSVLoader {

    private HashMap<String, QA> map = new HashMap<>();

    public CSVLoader(Context context, int rawResourceId) {
        loadCSV(context, rawResourceId);
    }

    private void loadCSV(Context context, int rawResourceId) {
        InputStream is = context.getResources().openRawResource(rawResourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;

        try {
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true; // skip header
                    continue;
                }

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length >= 3) {
                    String question = parts[0].replace("\"", "").trim();
                    String answer = parts[1].replace("\"", "").trim();
                    String id = parts[2].trim();
                    map.put(id, new QA(question, answer));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QA getQAById(String id) {
        return map.get(id); // returns null if not found
    }

}
