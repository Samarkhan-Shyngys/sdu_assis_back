package com.sdu.edu.controllers;

import com.sdu.edu.pojo.AdminDto;
import com.sdu.edu.pojo.StepperDto;
import com.sdu.edu.pojo.admin.ApplyDto;
import com.sdu.edu.pojo.admin.PersonalDto;
import com.sdu.edu.service.AdminService;
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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/contents")
    public ResponseEntity<?> mainPage(){
        AdminDto adminDto = adminService.getContents();
        JSONObject jsonObject = new JSONObject( adminDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);

    }
    @PostMapping("/apply/assistant")
    public ResponseEntity<?> addBook(@RequestParam(required = true) Map<String, Object> map) throws IOException {
        System.out.println(map);
        adminService.setApply(map);
        JSONObject jsonObject = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);
    }
    @GetMapping("/apply/experience/{id}")
    public ResponseEntity<?> getExperience(@PathVariable(value = "id") Long id){
        ApplyDto applyDto  = adminService.getExperience(id);
        JSONObject jsonObject = new JSONObject( applyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);

    }
    @GetMapping("/apply/certificate/{id}")
    public ResponseEntity<?> getCertificate(@PathVariable(value = "id") Long id){
        ApplyDto applyDto  = adminService.getCertificate(id);
        JSONObject jsonObject = new JSONObject( applyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);

    }
    @GetMapping("/apply/personal/{id}")
    public ResponseEntity<?> getPersonal(@PathVariable(value = "id") Long id){
        PersonalDto personalDto  = adminService.getPersonal(id);
        JSONObject jsonObject = new JSONObject( personalDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);

    }
    @GetMapping("/apply/video/{id}")
    public ResponseEntity<?> getVideo(@PathVariable(value = "id") Long id){
        PersonalDto personalDto  = adminService.getVideo(id);
        JSONObject jsonObject = new JSONObject( personalDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(jsonObject.toString(), headers, HttpStatus.OK);

    }
    @GetMapping("/delete/{userEmail}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userEmail") String email){
        adminService.deleteUser(email);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
