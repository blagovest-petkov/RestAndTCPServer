package com.example.Service;

import com.example.Dao.StudentDao;
import com.example.Entity.Student;
import com.example.response.GetStudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public GetStudentResponse getAllStudents(){
        return new GetStudentResponse(studentDao.getAllStudents());
    }

    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    public void removeStudentById(int id) {
        studentDao.removeStudentById(id);
    }

    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    public void insertStudent(Student student) {
        studentDao.insertStudent(student);
    }
}
