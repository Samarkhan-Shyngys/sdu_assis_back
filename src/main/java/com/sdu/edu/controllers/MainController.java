package com.sdu.edu.controllers;

import com.sdu.edu.pojo.ApplyCourseDto;
import com.sdu.edu.pojo.CoursesDto;
import com.sdu.edu.pojo.MainDto;
import com.sdu.edu.pojo.Timetable;
import com.sdu.edu.service.AssistantService;
import com.sdu.edu.service.LibraryService;
import com.sdu.edu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {
    @Autowired
    LibraryService libraryService;

    @Autowired
    private AssistantService assistantService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public ResponseEntity<?> mainPage(){
        MainDto main = new MainDto();
        main.setLibrary(libraryService.getAllBooks());
        return new ResponseEntity<>(main, HttpStatus.OK);
    }
    @GetMapping("/get/allCourses")
    public ResponseEntity<?> getAllWitOutCourses(){
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setCourses(assistantService.getAllWithOutCourses());
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }
    @GetMapping("/get/course/{id}")
    public ResponseEntity<?> getApplyCourse(@PathVariable(value = "id") Long id){
        ApplyCourseDto applyCourseDto = studentService.getApplyCourse(id);
        return new ResponseEntity<>(applyCourseDto, HttpStatus.OK);
    }
    @GetMapping("/get/times/{id}")
    public ResponseEntity<?> getTimeTable(@PathVariable(value = "id") Long id){
        Timetable timetables = studentService.getTimeTables(id);
        return new ResponseEntity<>(timetables, HttpStatus.OK);
    }
}
