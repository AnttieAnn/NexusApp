package com.example.nexusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.text.NumberFormat;

public class QuestionInput extends AppCompatActivity {
    public int convert(String text){
        int num;

        try{
            num = Integer.parseInt(text);
        } catch(NumberFormatException e){
            System.out.println("error");
            num = 0;
        }

        return num;
    }
    public String FindDifficulty(int InputID){
        String FilePath;

        if (InputID >110000 && InputID < 120000){
            //Question FIle is Nintendo Easy
            FilePath = "/raw/NinEasy.csv";
            return FilePath;
        }
        else if(InputID >120000 && InputID < 130000) {
            //Question File is Nintendo Normal
            FilePath = "/raw/NinNormal.csv";
            return FilePath;
        }
        else if (InputID >130000 && InputID < 140000){
            //Question File is Nintendo Hard
            FilePath = "NinHard.csv";
            return FilePath;
        } else {
            FilePath = "Error";
            return FilePath;
        }
    }

    Button Submit;

    Button MainMenu;
    EditText QID;

    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_input);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Submit = findViewById(R.id.SubmitButton);
        QID = (EditText) findViewById(R.id.InputQID);
        Title = (TextView) findViewById(R.id.InputTitle);


        Submit.setOnClickListener(new View.OnClickListener() { //Submit button responding
            @Override

            public void onClick (View view){
                String QuestionID = QID.getText().toString(); // Input will be a string first
                int IntQID = convert(QuestionID); //Turns the input into a Number, to Search the File.
                String QuestionFile = FindDifficulty(IntQID);

                if (QuestionFile.equals("Error")){
                    Title.setText("Wrong ID, please Re-enter");
                    return;
                }

                // Determine raw resource ID
                int rawResId = 0;

                switch (QuestionFile) {
                    case "/raw/NinEasy.csv":
                        rawResId = R.raw.nineasy;
                        break;
                    case "/raw/NinNormal.csv":
                        rawResId = R.raw.ninnormal;
                        break;
                    case "NinHard.csv":
                        rawResId = R.raw.ninhard;
                        break;
                }


                CSVLoader loader = new CSVLoader(QuestionInput.this, rawResId);
                QA result = loader.getQAById(QuestionID);


                if (result != null) //Input ID will check the Question ID
                {
                    Title.setText(result.toString());
                } else { //Error Response
                    Title.setText("Wrong ID, please Re-enter");
                }
            }
        });

        MainMenu = findViewById(R.id.MainMenubutton);
        //Main Menue button respond
        MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionInput.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}