package com.example.response;


import com.example.Entity.Student;

import java.util.List;

public class GetStudentResponse {
    private List<Student> students;

    public GetStudentResponse() {
    }

    public GetStudentResponse(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
