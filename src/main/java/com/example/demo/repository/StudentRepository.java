package com.example.demo.repository;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public class StudentRepository implements StudentRepositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createStudent(Student student) {
        String sql = "INSERT INTO Students (date,cause, studentTitle, studentFirstName, studentLastName, studentID, studentYear, studyField, advisor, moo, tumbol, amphur, province, postalCode, mobilePhone, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                student.getDate(),
                student.getCause(),
                student.getStudentTitle(),
                student.getStudentFirstName(),
                student.getStudentLastName(),
                student.getStudentID(),
                student.getStudentYear(),
                student.getStudyField(),
                student.getAdvisor(),
                student.getMoo(),
                student.getTumbol(),
                student.getAmphur(),
                student.getProvince(),
                student.getPostalCode(),
                student.getMobilePhone(),
                student.getPhone());
        for(Subject s : student.getAddSubjectList()){
            String spl_1 =  "INSERT INTO Subjects (StudentID,SubjectCode,SubjectName,SubjectSection,SubjectDate,SubjectCredit,SubjectTeacher,SubjectTeacherCheck,Type)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(spl_1,student.getStudentID(),s.getSubjectCode(),s.getSubjectName(),s.getSubjectSection(),s.getSubjectDate(),s.getSubjectCredit(),s.getSubjectTeacher(),s.getSubjectTeacherCheck(),"Register");
        }
        for(Subject s : student.getDropSubjectList()){
            String spl_1 =  "INSERT INTO Subjects (StudentID,SubjectCode,SubjectName,SubjectSection,SubjectDate,SubjectCredit,SubjectTeacher,SubjectTeacherCheck,Type)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(spl_1,student.getStudentID(),s.getSubjectCode(),s.getSubjectName(),s.getSubjectSection(),s.getSubjectDate(),s.getSubjectCredit(),s.getSubjectTeacher(),s.getSubjectTeacherCheck(),"Withdraw");
        }
    }

    @Override
    public List<Student> getStudentById(String studentId){
        String sql = "SELECT * FROM Students WHERE Students.StudentID = ?";
        List<Student> st;
        st = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Student.class),studentId);
        String sql_2 = "SELECT * FROM Subjects WHERE StudentID = ? AND Type = ?";
        for(Student s : st){
            List<Subject> subj = jdbcTemplate.query(sql_2, new BeanPropertyRowMapper<>(Subject.class), s.getStudentID(),"Register");
            s.setAddSubjectList(subj.toArray(new Subject[0]));
        }
        for(Student s : st){
            List<Subject> subj = jdbcTemplate.query(sql_2, new BeanPropertyRowMapper<>(Subject.class), s.getStudentID(),"Withdraw");
            s.setDropSubjectList(subj.toArray(new Subject[0]));
        }
        return st;
    }
    @Override
    public boolean updateStudentNameById(String studentId, String studentName){
        String sql = "UPDATE Students SET studentFirstName = ? WHERE studentID = ?";
        jdbcTemplate.update(sql,studentName,studentId);
        return true;
    }
    @Override
    public boolean deleteStudentById(String studentId){
        String delSubByID = "DELETE FROM subjects WHERE StudentID = ?";
        String delStuByID = "DELETE FROM students WHERE StudentID = ?";
        jdbcTemplate.update(delSubByID,studentId);
        jdbcTemplate.update(delStuByID,studentId);
        return true;
    }
}
