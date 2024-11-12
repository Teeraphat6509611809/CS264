package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reg")
@CrossOrigin

public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/createStudent")
    public void createStudent(@RequestBody Student student) {

        try {
            studentRepository.createStudent(student);

        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @GetMapping("/getStudent")
    public List<Student> getStudentById(@RequestParam (name = "studentID" , required = true) String studentID){
            return studentRepository.getStudentById(studentID);
    }

    @GetMapping("/updateStudent")
    public boolean updateStudentNameById(@RequestParam (name = "studentID" , required = true) String studentId,
                                         @RequestParam (name = "studentName" , required = true) String studentName){
        return studentRepository.updateStudentNameById(studentId,studentName);

    }
    @DeleteMapping("/deleteStudent")
    public boolean deleteStudentById(@RequestParam (name = "studentID") String studentId){
        return studentRepository.deleteStudentById(studentId);
    }
}
