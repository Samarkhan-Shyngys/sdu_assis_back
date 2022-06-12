package com.sdu.edu.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdu.edu.pojo.*;
import com.sdu.edu.service.AssistantService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    @GetMapping("/")
    public ResponseEntity<?> mainPage(){
        StepperDto stepper = new StepperDto();
        stepper.setStatus(1);
        return new ResponseEntity<>(stepper, HttpStatus.OK);
    }
    @PostMapping("/stepper/{id}")
    public ResponseEntity<?> sendData(@PathVariable(value = "id") Long id,
                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                      @RequestParam(required = true) Map<String, Object> map) throws IOException {
        assistantService.addStepper(id, map, file);
        System.out.println(map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/add/course/{id}")
    public ResponseEntity<?> addCourse(@PathVariable(value = "id") Long id,
                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam(required = true)Map<String, Object> map) throws IOException {
        System.out.println(map);
        assistantService.addCourse(id, map, file);

        return null;
    }
    @PostMapping("/add/book")
    public ResponseEntity<?> addBook(@RequestParam(value = "image", required = false) MultipartFile image,
                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam(required = true)Map<String, Object> map) throws IOException {
        System.out.println(map);
        assistantService.addBook(image, map, file);

        return null;
    }
    @GetMapping("/get/course/{id}")
    public ResponseEntity<?> mainPage(@PathVariable(value = "id") Long id) throws JsonProcessingException {
        AssistantCourseDto courseDto = assistantService.getCourse(id);
        System.out.println(courseDto);

        JSONObject jsonObject = new JSONObject( courseDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);
    }
    @GetMapping("/get/assistant/course/{id}")
    public ResponseEntity<?> getAssistantCourse(@PathVariable(value = "id") Long id) throws JsonProcessingException {
        AssistantCourseDto courseDto = assistantService.getCourseById(id);


        JSONObject jsonObject = new JSONObject( courseDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable(value = "id") Long id){
        AssistantProfileDto assistant = assistantService.getProfile(id);

        return new ResponseEntity<>(assistant, HttpStatus.OK);
    }
    @GetMapping("/get/work/{id}")
    public ResponseEntity<?> getWorks(@PathVariable(value = "id") Long id){
        WorkDto work = assistantService.getWorks(id);

        return new ResponseEntity<>(work, HttpStatus.OK);
    }
    @GetMapping("/get/books/{id}")
    public ResponseEntity<?> getAllbooks(@PathVariable(value = "id") Long id){
        MainDto main = new MainDto();
        main.setLibrary(assistantService.getAllBooks(id));

        return new ResponseEntity<>(main, HttpStatus.OK);
    }
    @GetMapping("/get/allCourses/{id}")
    public ResponseEntity<?> getAllCourses(@PathVariable(value = "id") Long id){
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setCourses(assistantService.getAllcourses(id));
        return new ResponseEntity<>(coursesDto, HttpStatus.OK);
    }

    @PostMapping("/edit/profile")
    public ResponseEntity<?> editStudentProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                                @RequestParam(required = true) Map<String, Object> map){
        assistantService.editAssistantProfile(file, map);
        return ResponseEntity.ok(new MessageResponse("Материал успешно добавлено!"));
    }
    @PostMapping("/edit/work/{id}")
    public ResponseEntity<?> editWorkExperience(@PathVariable(value = "id") Long id,
                                                @RequestParam(required = true)Map<String, Object> map){
        System.out.println(map);
        return ResponseEntity.ok(new MessageResponse("Материал успешно добавлено!"));
    }

}
