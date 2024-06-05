package com.example.androidstudioe12;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Database.DBHandler;
import models.Course;

public class AddCourseActivity extends AppCompatActivity {
    private EditText inputId, inputName, inputDescription;
    private Button buttonSave;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputDescription = findViewById(R.id.input_description);
        buttonSave = findViewById(R.id.button_save);

        dbHandler = new DBHandler(this);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Get input data
                String id = inputId.getText().toString();
                String name = inputName.getText().toString();
                String description =
                        inputDescription.getText().toString();
// Add data
                Course course = new Course(id, name, description);
                dbHandler.addCourse(course);
                finish();
            }
        });
    }
}