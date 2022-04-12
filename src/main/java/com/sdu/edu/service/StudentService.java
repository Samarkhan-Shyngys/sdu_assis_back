package com.sdu.edu.service;

import com.sdu.edu.models.Student;
import com.sdu.edu.models.User;
import com.sdu.edu.pojo.StudentProfileDto;
import com.sdu.edu.repository.StudentRepository;
import com.sdu.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class StudentService {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void editStudentProfile(MultipartFile file, StudentProfileDto stu) throws IOException {
        User user = userRepository.findByEmail(stu.getEmail());

        if(studentRepository.existsByUserId(user.getId())){
            Student student= studentRepository.findByUserId(user.getId());
            student.setFirstname(stu.getFirstname());
            student.setLastname(stu.getLastname());
            student.setFaculty(stu.getFaculty());
            student.setProfession(stu.getProfession());
            student.setPhone(stu.getPhone());
            student.setUserId(user.getId());
            if(file != null) {
                File deviceFile = new File(uploadPath);
                if (!deviceFile.exists()) {
                    deviceFile.mkdir();
                }
                file.transferTo(new File(deviceFile + "/" + stu.getEmail().split("@")[0] + "." + file.getContentType().split("/")[1]));
                student.setPhotoPath(stu.getEmail().split("@")[0] + "." + file.getContentType().split("/")[1]);
            }

            studentRepository.save(student);
        }
        else{
            Student student = new Student();
            student.setFirstname(stu.getFirstname());
            student.setLastname(stu.getLastname());
            student.setFaculty(stu.getFaculty());
            student.setProfession(stu.getProfession());
            student.setPhone(stu.getPhone());
            student.setUserId(user.getId());

            if(file != null) {
                File deviceFile = new File(uploadPath);
                if (!deviceFile.exists()) {
                    deviceFile.mkdir();
                }
                file.transferTo(new File(deviceFile + "/" + stu.getEmail().split("@")[0] + "." + file.getContentType().split("/")[1]));
                student.setPhotoPath(stu.getEmail().split("@")[0] + "." + file.getContentType().split("/")[1]);
            }

            studentRepository.save(student);
        }


    }

    public StudentProfileDto getProfile(Long id) {
        StudentProfileDto student = new StudentProfileDto();
        Student stu = studentRepository.findByUserId(id);
        student.setFirstname(stu.getFirstname());
        student.setLastname(stu.getLastname());
        student.setFaculty(stu.getFaculty());
        student.setProfession(stu.getProfession());
        student.setPhone(stu.getPhone());
        return student;
    }
}
