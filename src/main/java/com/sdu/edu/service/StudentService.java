package com.sdu.edu.service;

import com.sdu.edu.models.*;
import com.sdu.edu.pojo.ApplyCourseDto;
import com.sdu.edu.pojo.StudentProfileDto;
import com.sdu.edu.pojo.Timetable;
import com.sdu.edu.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseTeacherRepository teacherRepository;

    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CertificateRepository certificateRepository;

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
        if(studentRepository.existsByUserId(id)){
            Student stu = studentRepository.findByUserId(id);
            student.setFirstname(stu.getFirstname());
            student.setLastname(stu.getLastname());
            student.setFaculty(stu.getFaculty());
            student.setProfession(stu.getProfession());
            student.setPhone(stu.getPhone());
        }
        return student;
    }

    public ApplyCourseDto getApplyCourse(Long id){
        CourseTeacher courseTeacher = teacherRepository.getOne(id);
        Assistant assistant = assistantRepository.getOne(courseTeacher.getAssistentId());
        List<JobMdl> jobList = jobRepository.findAllByAssId(assistant.getId());
        List<CertificateMdl> certificateList = certificateRepository.findAllByAssId(assistant.getId());
        ApplyCourseDto applyCourseDto = new ApplyCourseDto();
        applyCourseDto.setAboutCourse(courseTeacher.getCourseInfo());
        applyCourseDto.setCourseName(courseTeacher.getCourseName());
        applyCourseDto.setCourseCount(teacherRepository.findAllByAssistentId(assistant.getId()).size());
        applyCourseDto.setAboutAssistant(assistant.getAboutYou());
        applyCourseDto.setAssistantName(assistant.getFirstname() + " " + assistant.getLastname());
        applyCourseDto.setImagePath("/course/" + courseTeacher.getPhotoPath());
        applyCourseDto.setPoint(courseTeacher.getPoint()==null?0:courseTeacher.getPoint());
        applyCourseDto.setRating(courseTeacher.getRating()==null?0:courseTeacher.getRating());
        applyCourseDto.setStudentCount(0);
        applyCourseDto.setCourseFormat(courseTeacher.getFormat()==0?"онлайн":"оффлайн");
        applyCourseDto.setWorkList(jobList);
        applyCourseDto.setCertificateList(certificateList);




        return applyCourseDto;
    }

    public Timetable getTimeTables(Long id) {
        List<String> str1 = new ArrayList<>();
        List<String> str2 = new ArrayList<>();
        List<String> str3 = new ArrayList<>();
        List<String> str4 = new ArrayList<>();
        List<String> str5 = new ArrayList<>();
        List<String> str6 = new ArrayList<>();
        List<String> str7 = new ArrayList<>();

        Timetable timetables = new Timetable();
        CourseTeacher course = teacherRepository.getOne(id);
        JSONArray array = new JSONArray(course.getCourseTime());

        List<JSONObject> jsonArray = new ArrayList<>();

        for (int i=0; i<array.length(); i++){
            JSONObject jsonObject = array.getJSONObject(i);
            String timeString = jsonObject.get("time").toString();
            String dayStr = timeString.split("-")[0];
            String hourStr = timeString.split("-")[1];
            if(dayStr.equals("Monday")){
                str1.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Tuesday")){
                str2.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Wednesday")){
                str3.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Thursday")){
                str4.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Friday")){
                str5.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Saturday")){
                str6.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }
            if(dayStr.equals("Sunday")){
                str7.add(hourStr+ ":00-" + (Integer.parseInt(hourStr)+1)+":00");
            }

        }
        timetables.setMonday(str1);
        timetables.setTuesday(str2);
        timetables.setWednesday(str3);
        timetables.setThursday(str4);
        timetables.setFriday(str5);
        timetables.setSaturday(str6);
        timetables.setSunday(str7);

        System.out.println(timetables);
        return timetables;

    }
}
