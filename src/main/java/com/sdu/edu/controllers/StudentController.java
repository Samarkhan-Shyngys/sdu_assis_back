package com.sdu.edu.controllers;

import com.sdu.edu.pojo.MessageResponse;
import com.sdu.edu.pojo.StudentProfileDto;
import com.sdu.edu.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> mainPage(@PathVariable(value = "id") Long id){
        StudentProfileDto student = service.getProfile(id);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/edit/profile")
    public ResponseEntity<?> editStudentProfile(@RequestParam(value = "file", required = false) MultipartFile file, StudentProfileDto stu){
//        System.out.println(file.isEmpty()?"ssss":"asdasda");
        try{
            service.editStudentProfile(file, stu);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Материал успешно добавлено!"));
    }
}
