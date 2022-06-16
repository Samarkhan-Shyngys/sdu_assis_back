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

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.*;

@Service
public class AssistantService {
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
    private CourseTeacherRepository teacherRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;


    public void addStepper(Long id, Map<String, Object> map, MultipartFile file) throws IOException {
        Random rand = new Random();
        Assistant assistant = new Assistant();
        if(assistantRepository.existsById(id)){
            assistant = assistantRepository.getOne(id);
        }
        JSONObject jsonObject = new JSONObject(map);
        JSONArray jobs = new JSONArray(jsonObject.get("job").toString());
        JSONArray certificates = new JSONArray(jsonObject.get("certificate").toString());
        assistant.setUserId(id);
        if(map.get("firstname")!=null){
            assistant.setFirstname(map.get("firstname").toString());
        }
        if(map.get("lastname")!=null){
            assistant.setLastname(map.get("lastname").toString());
        }
        if(map.get("firstname")!=null){
            assistant.setFirstname(map.get("firstname").toString());
        }
        if(map.get("language")!=null){
            assistant.setLanguage(map.get("language").toString());
        }
        if(map.get("profession")!=null){
            assistant.setProfession(map.get("profession").toString());

        }
        if(map.get("about")!=null){
            assistant.setAboutYou(map.get("about").toString());
        }
        if(map.get("faculty")!=null){
            assistant.setFaculty(map.get("faculty").toString());
        }
        if(map.get("phone")!=null){
            assistant.setPhone(map.get("phone").toString());
        }
        if(map.get("video")!=null){
            assistant.setVideo(map.get("video").toString());
        }
        assistant.setAccess(false);
        if(file != null) {
            File assIm = new File(avaPath);
            if (!assIm.exists()) {
                assIm.mkdir();
            }
            int name = rand.nextInt(10000);
            file.transferTo(new File(assIm + "/" + name + "." + file.getContentType().split("/")[1]));
            assistant.setPhotoPath(name + "." + file.getContentType().split("/")[1]);
        }
        assistantRepository.save(assistant);
        for(int i=0; i< jobs.length(); i++){
            JSONObject job = jobs.getJSONObject(i);
            JobMdl jobMdl = new JobMdl();
            if (job.get("organisation")!=null){
                jobMdl.setOrganisation(job.get("organisation").toString());
            }
            if (job.get("position")!=null){
                jobMdl.setPosition(job.get("position").toString());
            }
            if (job.get("startWorkYear")!=null){
                jobMdl.setStartWorkYear(Integer.parseInt(job.get("startWorkYear").toString()));
            }
            if (job.get("startWorkMonth")!=null){
                jobMdl.setStartWorkMonth(job.get("startWorkMonth").toString());
            }
            if (job.get("endDate")!=null && !job.get("endDate").toString().equals("")){
                jobMdl.setEndDate((Boolean) job.get("endDate"));
                if(!(Boolean) job.get("endDate")){
                    if (job.get("endWorkMonth")!=null){
                        jobMdl.setEndWorkMonth(job.get("endWorkMonth").toString());
                    }
                    if (job.get("endWorkYear")!=null){
                        jobMdl.setStartWorkYear(Integer.parseInt(job.get("endWorkYear").toString()));

                    }
                }
            }
            jobMdl.setAssId(assistant.getId());
            jobRepository.save(jobMdl);
        }
        for(int j=0; j<certificates.length(); j++){
            JSONObject certificateJson = certificates.getJSONObject(j);
            CertificateMdl certificate = new CertificateMdl();
            if(certificateJson.get("name")!=null){
                certificate.setCerName(certificateJson.get("name").toString());
            }
            if(certificateJson.get("desc")!=null){
                certificate.setCerDec(certificateJson.get("desc").toString());
            }
            if(certificateJson.get("photo")!=null){
                int name = rand.nextInt(10000);
                certificate.setName(String.valueOf(name));

                String fileName = getFileFromString(certificateJson.get("photo").toString(), certificatePath, String.valueOf(name));
                certificate.setPhotoPath(fileName);
            }
            certificate.setAssId(assistant.getId());
            certificateRepository.save(certificate);

        }

    }
    public void editCourse(Long id, Map<String, Object> map, MultipartFile file) throws IOException {
        if(map.get("courseName")!=null && !map.get("courseName").toString().equals("")){
            if(teacherRepository.existsByCourseName((map.get("courseName").toString()))){
                Random rand = new Random();
                CourseTeacher course = teacherRepository.getOne(id);
//                if(teacherRepository.existsByAssistentId(id)){
//                    course = teacherRepository.findByAssistentId(id);
//                }
                course.setCourseName(map.get("courseName").toString());
                course.setCourseInfo(map.get("about").toString());
                course.setCourseTime(map.get("dates").toString());
                if (map.get("format").toString().equals("onn")) {
                    course.setFormat(0);
                }
                if (map.get("format").toString().equals("off")) {
                    course.setFormat(1);
                }


                if(file != null) {
                    File courseIm = new File(coursePath);
                    if (!courseIm.exists()) {
                        courseIm.mkdir();
                    }
                    int name = rand.nextInt(10000);
                    file.transferTo(new File(courseIm + "/" + name + "." + file.getContentType().split("/")[1]));
                    course.setPhotoPath( name + "." + file.getContentType().split("/")[1]);
                }
                teacherRepository.save(course);
            }
        }
    }

    public void addCourse(Long id, Map<String, Object> map, MultipartFile file) throws IOException {
        if(map.get("courseName")!=null && !map.get("courseName").toString().equals("")){
            if(!teacherRepository.existsByCourseName((map.get("courseName").toString()))){
                Random rand = new Random();
                CourseTeacher course = new CourseTeacher();
//                if(teacherRepository.existsByAssistentId(id)){
//                    course = teacherRepository.findByAssistentId(id);
//                }
                course.setCourseName(map.get("courseName").toString());
                course.setCourseInfo(map.get("about").toString());
                course.setCourseTime(map.get("dates").toString());
                course.setFormat(Integer.parseInt(map.get("format").toString()));
                Assistant assistant = assistantRepository.findByUserId(id);
                course.setAssistentId(assistant.getId());
                if(file != null) {
                    File courseIm = new File(coursePath);
                    if (!courseIm.exists()) {
                        courseIm.mkdir();
                    }
                    int name = rand.nextInt(10000);
                    file.transferTo(new File(courseIm + "/" + name + "." + file.getContentType().split("/")[1]));
                    course.setPhotoPath( name + "." + file.getContentType().split("/")[1]);
                }
                teacherRepository.save(course);
            }
        }

    }

    public String getFileFromString(String imgBase64,String dir, String cerName){

        String[] strings = imgBase64.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);

        File courseIm = new File( dir);
        if (!courseIm.exists()) {
            courseIm.mkdir();
        }
        String path = dir + "/"+cerName +"."+ extension;
        File file = new File(path);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cerName +"."+ extension;

    }
    public AssistantCourseDto getCourse(Long id) {
        AssistantCourseDto courseDto = new AssistantCourseDto();
        CourseTeacher course = teacherRepository.findByAssistentId(id);
        courseDto.setCourseName(course.getCourseName());
        courseDto.setFormat(course.getFormat().toString());
        courseDto.setAbout(course.getCourseInfo());
        JSONArray array = new JSONArray(course.getCourseTime());

        List<JSONObject> jsonArray = new ArrayList<>();
        for (int i=0; i<array.length(); i++){
            jsonArray.add(array.getJSONObject(i));
        }

        System.out.println(jsonArray);
        courseDto.setDates(jsonArray);
        courseDto.setPhotoPath(course.getPhotoPath());

        return courseDto;
    }
    public AssistantCourseDto getCourseById(Long id){
        AssistantCourseDto courseDto = new AssistantCourseDto();
        CourseTeacher course = teacherRepository.getOne(id);
        courseDto.setCourseName(course.getCourseName());
        courseDto.setFormat("onn");
        courseDto.setAbout(course.getCourseInfo());
        JSONArray array = new JSONArray(course.getCourseTime());

        List<JSONObject> jsonArray = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (int i=0; i<array.length(); i++){
            jsonArray.add(array.getJSONObject(i));
        }

        System.out.println(jsonArray);
        courseDto.setDates(jsonArray);
        courseDto.setPhotoPath("/course/"+course.getPhotoPath());

        return courseDto;
    }

    public void editAssistantProfile(MultipartFile file, Map<String, Object> map) {
        System.out.println(map);


    }

    public AssistantProfileDto getProfile(Long id) {
        AssistantProfileDto assistant = new AssistantProfileDto();
        if(assistantRepository.existsByUserId(id)){
            Assistant assistant1 = assistantRepository.findByUserId(id);
            assistant.setFirstname(assistant1.getFirstname());
            assistant.setLastname(assistant1.getLastname());
            assistant.setFaculty(assistant1.getFaculty());
            assistant.setProfession(assistant1.getProfession());
            assistant.setPhone(assistant1.getPhone());
            assistant.setAbout(assistant1.getAboutYou());
            assistant.setImage("/ava/" + assistant1.getPhotoPath());
        }
        return assistant;
    }

    public List<LibraryDto> getAllBooks(Long id) {
        List<Library> books = libraryRepository.findAllByUserId(id);
        List<LibraryDto> list = new ArrayList<>();
        for(Library book: books){
            LibraryDto library = new LibraryDto();
            library.setId(book.getId());
            library.setAuthor(book.getAuthor());
            library.setTitle(book.getBookName());
            library.setUrl("/book/" + book.getImagePath());
            list.add(library);
        }

        return list;
    }
    public List<CourseDto> getAllWithOutCourses(){
        List<CourseTeacher> list = teacherRepository.findAll();

        List<CourseDto> courseList = new ArrayList<>();
        for(CourseTeacher course: list){
            Assistant assistant = assistantRepository.getOne(course.getAssistentId());
            CourseDto c = new CourseDto();
            c.setCourseId(course.getId());
            c.setAssistant(assistant.getFirstname() + " " + assistant.getLastname());
            c.setCourseName(course.getCourseName());
            c.setPathImage("/course/" + course.getPhotoPath());
            c.setAssImage("/ava/" + assistant.getPhotoPath());
            c.setPoint(course.getPoint()==null?0:course.getPoint());
            c.setRating(course.getRating()==null?0:course.getRating());
            c.setStudentCount(courseStudentRepository.countAllByCourseId(course.getId()));
            courseList.add(c);
        }
        return courseList;
    }
    public List<CourseDto> getAllcourses(Long id){
        Assistant assistant = assistantRepository.findByUserId(id);
        List<CourseTeacher> list = teacherRepository.findAllByAssistentId(assistant.getId());

        List<CourseDto> courseList = new ArrayList<>();
        for(CourseTeacher course: list){
            CourseDto c = new CourseDto();
            c.setCourseId(course.getId());
            c.setAssistant(assistant.getFirstname() + " " + assistant.getLastname());
            c.setCourseName(course.getCourseName());
            c.setPathImage("/course/" + course.getPhotoPath());
            c.setPoint(course.getPoint()==null?0:course.getPoint());
            c.setRating(course.getRating()==null?0:course.getRating());
            c.setAssImage("/ava/" + assistant.getPhotoPath());
            c.setStudentCount(courseStudentRepository.countAllByCourseId(course.getId()));
            courseList.add(c);
        }
        return courseList;
    }

    public void addBook(MultipartFile image, Map<String, Object> map, MultipartFile file) throws IOException {
        Library book = new Library();
        if(map.get("id").toString().length()>0 || map.get("id")!=null){
            book.setUserId(Long.parseLong(map.get("id").toString()));
        }
        Long id = Long.parseLong(map.get("id").toString());
        Random rand = new Random();
        if(map.get("bookName").toString().length()>0 || map.get("bookName")!=null){
            book.setBookName(map.get("bookName").toString());
        }
        if(map.get("author").toString().length()>0 || map.get("author")!=null){
            book.setAuthor(map.get("author").toString());
        }
        if(image != null) {
            File bookIm = new File(bookPath);
            if (!bookIm.exists()) {
                bookIm.mkdir();
            }
            int name =rand.nextInt(10000);
            image.transferTo(new File(bookIm + "/" + name + "." + image.getContentType().split("/")[1]));
            book.setImagePath(name + "." + image.getContentType().split("/")[1]);
        }
        if(file != null) {
            File fileIm = new File(bookPath);
            if (!fileIm.exists()) {
                fileIm.mkdir();
            }
            int name =rand.nextInt(10000);
            file.transferTo(new File(fileIm + "/" + name + "." + file.getContentType().split("/")[1]));
            book.setBookPath( name + "." + file.getContentType().split("/")[1]);
        }
        libraryRepository.save(book);

    }

    public WorkDto getWorks(Long id) {
        WorkDto workDto = new WorkDto();
        List<JobDto> jobs = new ArrayList<>();
        Assistant assistant = assistantRepository.findByUserId(id);
        for(JobMdl job: jobRepository.findAllByAssId(assistant.getId())){
            JobDto jobDto = new JobDto();
            jobDto.setOrganisation(job.getOrganisation());
            jobDto.setPosition(job.getPosition());
            jobDto.setStartWorkYear(job.getStartWorkYear().toString());
            jobDto.setStartWorkMonth(job.getStartWorkMonth());
            if(job.getEndWorkYear()!=null){
                jobDto.setEndWorkYear(job.getEndWorkYear().toString());
            }

            jobDto.setEndWorkMonth(job.getEndWorkMonth());
            jobs.add(jobDto);
        }

        workDto.setJobs(jobs);
        return workDto;
    }

    public List<CourseDto> getAllStudents(Long id) {
        Assistant assistant = assistantRepository.findByUserId(id);
        List<CourseTeacher> list = teacherRepository.findAllByAssistentId(assistant.getId());

        List<CourseDto> courseList = new ArrayList<>();
        for(CourseTeacher course: list){
            for(CourseStudent student: courseStudentRepository.findAllByCourseId(course.getId())){
                CourseDto c = new CourseDto();
                c.setCourseId(course.getId());
                c.setPathImage("/ava/" +studentRepository.findByUserId(student.getStudentId()).getPhotoPath() );
                c.setCourseName(course.getCourseName());
                c.setAssistant(studentRepository.findByUserId(student.getStudentId()).getFirstname() + " " + studentRepository.findByUserId(student.getStudentId()).getFirstname() );
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
                c.setDates(timeDtos);
                courseList.add(c);
            }

        }
        return courseList;

    }


}
