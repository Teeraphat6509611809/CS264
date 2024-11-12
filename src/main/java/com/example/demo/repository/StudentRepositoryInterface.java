package com.example.demo.repository;

import com.example.demo.model.Student;

import java.util.List;

public interface StudentRepositoryInterface {
    public void createStudent(Student student);
    public List<Student> getStudentById(String studentId);
    public boolean updateStudentNameById(String studentId, String studentName);
    public boolean deleteStudentById(String studentId);

}
