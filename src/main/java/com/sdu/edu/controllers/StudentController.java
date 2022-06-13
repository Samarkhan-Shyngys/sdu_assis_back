package com.sdu.edu.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sdu.edu.pojo.*;
import com.sdu.edu.service.StudentService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

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

    @PostMapping("/add/course/{id}")
    public ResponseEntity<?> editStudentProfile(@PathVariable(value = "id")  Long id, @RequestParam Map<String, Object> map){
//        System.out.println(file.isEmpty()?"ssss":"asdasda");
        System.out.println(map+" "+ id);
        service.addCourse(map, id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PostMapping("/like/book/{id}")
    public ResponseEntity<?> likeBook(@PathVariable(value = "id")  Long id, @RequestParam Map<String, Object> map){
//        System.out.println(file.isEmpty()?"ssss":"asdasda");
        System.out.println(map+" "+ id);
        service.likeBook(map, id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PostMapping("/like/course/{id}")
    public ResponseEntity<?> likeCourse(@PathVariable(value = "id")  Long id, @RequestParam Map<String, Object> map){
//        System.out.println(file.isEmpty()?"ssss":"asdasda");
        System.out.println(map+" "+ id);
        service.likeCourse(map, id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
//    @GetMapping("/get/course/{id}")
//    public ResponseEntity<?> getStudentCourse(@PathVariable(value = "id") Long id) throws JsonProcessingException {
//        AssistantCourseDto courseDto = service.getCourse(id);
//        System.out.println(courseDto);
//
//        JSONObject jsonObject = new JSONObject( courseDto);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);
//    }
    @GetMapping("/get/allCourses/{id}")
    public ResponseEntity<?> getAllCourses(@PathVariable(value = "id") Long id){
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setCourses(service.getAllcourses(id));
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }
    @GetMapping("/get/likedCourse/{id}")
    public ResponseEntity<?> getLikedAllCourses(@PathVariable(value = "id") Long id){
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setCourses(service.getLikedAllcourses(id));
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }
    @GetMapping("/get/books/{id}")
    public ResponseEntity<?> getAllbooks(@PathVariable(value = "id") Long id){
        MainDto main = new MainDto();
        main.setLibrary(service.getAllBooks(id));

        return new ResponseEntity<>(main, HttpStatus.OK);
    }


}
