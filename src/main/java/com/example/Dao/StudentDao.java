package com.example.Dao;

import com.example.Entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> getAllStudents();

    Student getStudentById(int id);

    void removeStudentById(int id);

    void updateStudent(Student student);

    void insertStudent(Student student);
}
