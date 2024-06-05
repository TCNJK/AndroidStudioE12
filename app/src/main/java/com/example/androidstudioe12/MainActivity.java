package com.example.androidstudioe12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapter.CourseAdapter;
import Database.DBHandler;
import models.Course;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private RecyclerView recyclerView;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        courseList = dbHandler.getAllCourses();
        recyclerView = findViewById(R.id.recycler_view);
        buttonAdd = findViewById(R.id.button_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this,
                        UpdateCourseActivity.class);
                intent.putExtra("course_id",
                        courseList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onEditClick(int position) {
                Intent intent = new Intent(MainActivity.this,
                        UpdateCourseActivity.class);
                intent.putExtra("course_id",
                        courseList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                dbHandler.deleteCourse(courseList.get(position));
                courseList.remove(position);
                courseAdapter.notifyItemRemoved(position);
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        courseList.clear();
        courseList.addAll(dbHandler.getAllCourses());
        courseAdapter.notifyDataSetChanged();
    }
}