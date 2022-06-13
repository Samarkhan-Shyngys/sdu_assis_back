package com.sdu.edu.service;

import com.sdu.edu.models.*;
import com.sdu.edu.pojo.*;
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
import java.util.Map;

@Service
public class StudentService {



    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.user-image}")
    private String avaPath;

    @Value("${file.upload-course}")
    private String coursePath;

    @Value("${file.upload-book}")
    private String bookPath;

    @Value("${file.upload-certificate}")
    private String certificatePath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;

    @Autowired
    private CourseTeacherRepository teacherRepository;

    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private StudentBookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;

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
                File deviceFile = new File(avaPath);
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
                File deviceFile = new File(avaPath);
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
            student.setImage("/ava/" + stu.getPhotoPath());
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

    public void addCourse(Map<String, Object> map, Long id) {
        if(map.get("userId")!=null &&  !map.get("userId").toString().equals("")){
            Long studentId = Long.parseLong(map.get("userId").toString());
            CourseStudent courseStudent = new CourseStudent();
            if(courseStudentRepository.existsByCourseIdAndStudentId(id, studentId)){
                courseStudent = courseStudentRepository.findCourseStudentByCourseIdAndStudentId(id, studentId);
            }
            courseStudent.setCourseId(id);
            courseStudent.setStudentId(studentId);
            courseStudentRepository.save(courseStudent);

        }

    }

    public AssistantCourseDto getCourse(Long id) {
        AssistantCourseDto courseDto = new AssistantCourseDto();
        CourseTeacher course = teacherRepository.findByAssistentId(id);
        courseDto.setCourseName(course.getCourseName());
        courseDto.setFormat(course.getFormat().toString());
        courseDto.setAbout(course.getCourseInfo());
        JSONArray array = new JSONArray(course.getCourseTime());

        List<JSONObject> jsonArray = new ArrayList<>();

        System.out.println(jsonArray);
        courseDto.setDates(jsonArray);
        courseDto.setPhotoPath(course.getPhotoPath());

        return courseDto;
    }
    public List<CourseDto> getLikedAllcourses(Long id) {
        Student student = studentRepository.findByUserId(id);
        List<CourseDto> courseList = new ArrayList<>();
        for (StudentCourse studentCourse: studentCourseRepository.findAllByStudentId(student.getId())){
            if(studentCourse.isLiked()){
                CourseTeacher course = teacherRepository.findById(studentCourse.getCourseId()).get();
                Assistant assistant = assistantRepository.getOne(course.getAssistentId());
                CourseDto c = new CourseDto();
                c.setCourseId(course.getId());
                c.setAssistant(assistant.getFirstname() + " " + assistant.getLastname());
                c.setCourseName(course.getCourseName());
                c.setPathImage("/course/" + course.getPhotoPath());
                JSONArray array = new JSONArray(course.getCourseTime());

                c.setAssImage("/ava/" + assistant.getPhotoPath());
                c.setPoint(course.getPoint()==null?0:course.getPoint());
                c.setRating(course.getRating()==null?0:course.getRating());
                c.setLiked(studentCourse.isLiked());
                c.setStudentCount(courseStudentRepository.countAllByCourseId(course.getId()));
                System.out.println(array);
                courseList.add(c);
            }

        }
        return courseList;
    }

    public List<CourseDto> getAllcourses(Long id) {
        List<CourseDto> courseList = new ArrayList<>();
        for (CourseStudent courseStudent: courseStudentRepository.findAllByStudentId(id)){
            CourseTeacher course = teacherRepository.findById(courseStudent.getCourseId()).get();
            Assistant assistant = assistantRepository.getOne(course.getAssistentId());
            CourseDto c = new CourseDto();
            c.setCourseId(course.getId());
            c.setAssistant(assistant.getFirstname() + " " + assistant.getLastname());
            c.setCourseName(course.getCourseName());
            c.setPathImage("/course/" + course.getPhotoPath());
            JSONArray array = new JSONArray(course.getCourseTime());

            List<TableTimeDto> timeDtos = new ArrayList<>();
            for (int i=0; i<array.length(); i++){
                TableTimeDto tableTimeDto = new TableTimeDto();
                String dates = array.getJSONObject(i).get("time").toString();
               String t1 = dates.split("-")[0];
               String t2 = dates.split("-")[1];
               tableTimeDto.setDayStr(t1);
               tableTimeDto.setHourStr(t2+":00");
               timeDtos.add(tableTimeDto);

            }
            c.setAssImage("/ava/" + assistant.getPhotoPath());
            c.setDates(timeDtos);
            System.out.println(array);
            courseList.add(c);
        }
        return courseList;
    }

    public void likeBook(Map<String, Object> map, Long id) {
        if(map.get("liked")!=null && !map.get("liked").toString().equals("")){
            boolean liked = Boolean.parseBoolean(map.get("liked").toString());
            StudentBook studentBook = new StudentBook();
            Long bookId = Long.parseLong(map.get("id").toString());
            if(bookRepository.existsByBookIdAndStudentId(bookId, id)){
                studentBook = bookRepository.findByBookIdAndStudentId(bookId,id);
            }
            studentBook.setStudentId(id);
            studentBook.setBookId(bookId);
            studentBook.setLiked(liked);
            bookRepository.save(studentBook);
        }
    }
    public void likeCourse(Map<String, Object> map, Long id) {
        if(map.get("liked")!=null && !map.get("liked").toString().equals("")){
            boolean liked = Boolean.parseBoolean(map.get("liked").toString());
            StudentCourse studentCourse = new StudentCourse();
            Long courseId = Long.parseLong(map.get("id").toString());
            if(studentCourseRepository.existsByCourseIdAndStudentId(courseId, id)){
                studentCourse = studentCourseRepository.findByCourseIdAndStudentId(courseId,id);
            }
            studentCourse.setStudentId(id);
            studentCourse.setCourseId(courseId);
            studentCourse.setLiked(liked);
            studentCourseRepository.save(studentCourse);
        }
    }
    public List<LibraryDto> getAllBooks(Long id) {

        List<StudentBook> studentBooks = bookRepository.findAllByStudentId(id);
         List<LibraryDto> list = new ArrayList<>();
        for(StudentBook studentBook: studentBooks){
            if(studentBook.isLiked()){
                Library book = libraryRepository.getOne(studentBook.getBookId());
                LibraryDto library = new LibraryDto();
                library.setId(book.getId());
                library.setAuthor(book.getAuthor());
                library.setTitle(book.getBookName());
                library.setUrl("/file/" + book.getImagePath());
                library.setLiked(studentBook.isLiked()?true:false);
                list.add(library);
            }

        }

        return list;
    }



}
