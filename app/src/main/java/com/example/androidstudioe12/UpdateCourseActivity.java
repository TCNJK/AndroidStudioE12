package com.example.androidstudioe12;

import android.content.Intent;
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

public class UpdateCourseActivity extends AppCompatActivity {
    private EditText inputId, inputName, inputDescription;
    private Button buttonUpdate, buttonDelete;
    private DBHandler dbHandler;
    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputDescription = findViewById(R.id.input_description);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);

        dbHandler = new DBHandler(this);
        Intent intent = getIntent();
        String courseId = intent.getStringExtra("course_id");

        course = dbHandler.getCourse(courseId);
        if (course != null) {
            inputId.setText(course.getId());
            inputName.setText(course.getName());
            inputDescription.setText(course.getDescription());
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.setName(inputName.getText().toString());
                course.setDescription(inputDescription.getText().toString());
                dbHandler.updateCourse(course);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteCourse(course);
                finish();
            }
        });
    }
}