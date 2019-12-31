package com.example.Dao;

import com.example.Entity.Student;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeStudentDaoImpl implements StudentDao {

    private static Map<Integer, Student> students;

    static {
        students = new HashMap<Integer, Student>() {
            {
                put(1, new Student(1, "Said", "Computer Science"));
                put(2, new Student(2, "Alex U", "Finance"));
                put(3, new Student(3, "Anna", "Math"));
            }

        };
    }

    @Override
    public List<Student> getAllStudents(){
        return new ArrayList<>(students.values());
    }

    @Override
    public Student getStudentById(int id) {
        return students.get(id);
    }

    @Override
    public void removeStudentById(int id) {
        students.remove(id);
    }

    @Override
    public void updateStudent(Student student) {
        Student s = students.get(student.getId());
        if (s != null) {
            s.setCourse(student.getCourse());
            s.setName(student.getName());
        }
    }

    @Override
    public void insertStudent(Student student) {
        students.put(student.getId(), student);
    }
}
