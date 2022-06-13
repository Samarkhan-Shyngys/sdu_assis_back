package com.sdu.edu.service;

import com.sdu.edu.models.*;
import com.sdu.edu.pojo.AdminContentDto;
import com.sdu.edu.pojo.AdminDto;
import com.sdu.edu.pojo.LanguageDto;
import com.sdu.edu.pojo.admin.ApplyDto;
import com.sdu.edu.pojo.admin.CertificateDto;
import com.sdu.edu.pojo.admin.ExperienceDto;
import com.sdu.edu.pojo.admin.PersonalDto;
import com.sdu.edu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private CertificateRepository certificateRepository;


    public AdminDto getContents(){
        AdminDto adminDto = new AdminDto();
        List<User> users = userRepository.findAll();
        List<AdminContentDto> assistants = new ArrayList<>();
        List<AdminContentDto> stuToAss = new ArrayList<>();
        List<AdminContentDto> students = new ArrayList<>();
        Role studentRole = roleRepository.getOne(3L);
        Role assistantRole = roleRepository.getOne(2L);
        for(User user: users){
            if(!user.isDeleted()){

                if(user.getRoles().contains(studentRole)){
                    AdminContentDto adminContentDto = new AdminContentDto();
                    adminContentDto.setId(user.getId());
                    String s = user.getEmail().split("@")[0];
                    adminContentDto.setEmailID(s);
                    if(studentRepository.existsByUserId(user.getId())){
                        Student student = studentRepository.findByUserId(user.getId());
                        adminContentDto.setFaculty(student.getFaculty());
                        adminContentDto.setProfession(student.getProfession());
                        adminContentDto.setName(student.getLastname() + " "+student.getFirstname());
                        adminContentDto.setPhone(student.getPhone());
                        if(assistantRepository.existsByUserId(user.getId())){
                            if(!assistantRepository.findByUserId(user.getId()).isAccess()){
                                AdminContentDto adminContentDto2 = new AdminContentDto();
                                adminContentDto2.setId(user.getId());
                                adminContentDto2.setEmailID(user.getEmail());
                                Assistant assistant = assistantRepository.findByUserId(user.getId());
                                adminContentDto2.setName(assistant.getLastname() + " " + assistant.getFirstname());
                                adminContentDto2.setIcon(assistant.getPhotoPath());
                                stuToAss.add(adminContentDto2);
                                System.out.println("issss");
                            }

                        }
                    }
                    students.add(adminContentDto);

                    adminDto.setApplied(stuToAss);


                }
                adminDto.setStudents(students);
                if(assistantRepository.existsByUserId(user.getId())){
                    if(assistantRepository.findByUserId(user.getId()).isAccess()){
                        AdminContentDto adminContentDto = new AdminContentDto();
                        adminContentDto.setId(user.getId());
                        String s = user.getEmail().split("@")[0];
                        adminContentDto.setEmailID(s);
                        Assistant assistant = assistantRepository.findByUserId(user.getId());
                        adminContentDto.setFaculty(assistant.getFaculty());
                        adminContentDto.setName(assistant.getLastname() + " " + assistant.getFirstname());
                        adminContentDto.setProfession(assistant.getProfession());
                        adminContentDto.setPhone(assistant.getPhone());
                        assistants.add(adminContentDto);
                    }


                }
                adminDto.setAssistents(assistants);

            }





        }
        return adminDto;
    }

    public void deleteUser(String email) {
        if(userRepository.existsByEmail(email + "@stu.sdu.edu.kz")){
            User user = userRepository.findByEmail(email + "@stu.sdu.edu.kz");
            user.setDeleted(true);
            userRepository.save(user);

        }

    }
    public ApplyDto getCertificate(Long id) {
        ApplyDto applyDto = new ApplyDto();
        List<CertificateDto> list = new ArrayList<>();

        for(CertificateMdl cer: certificateRepository.findAllByAssId(id)){
            CertificateDto certificate = new CertificateDto();
            certificate.setId(cer.getId());
            certificate.setCompany(cer.getCerDec());
            certificate.setTitle(cer.getCerName());
            certificate.setImage(cer.getPhotoPath());
            list.add(certificate);
        }
        applyDto.setItems(list);

        return applyDto;
    }

    public ApplyDto getExperience(Long id) {
        ApplyDto applyDto = new ApplyDto();
        List<ExperienceDto> list = new ArrayList<>();
        for(JobMdl job: jobRepository.findAllByAssId(id)){
            ExperienceDto ex = new ExperienceDto();
            ex.setId(job.getId());
            String startYear = job.getStartWorkYear()==null?"":job.getStartWorkYear().toString();
            String startMonth = job.getStartWorkMonth()==null?"":" ("+job.getStartWorkMonth()+")";
            String endyear =  job.getEndWorkYear()==null?"":"- " + job.getEndWorkYear().toString();
            String endMonth = job.getEndWorkMonth()==null?"":" (" + job.getEndWorkMonth()+")";
            ex.setDuration(startYear + startMonth + endyear + endMonth);
            ex.setAbout(job.getOrganisation());
            ex.setWork(job.getPosition());
            list.add(ex);

        }
        applyDto.setItems(list);


        return applyDto;
    }

    public PersonalDto getPersonal(Long id) {
        int year = LocalDate.now().getYear();

        PersonalDto p = new PersonalDto();
        Assistant assistant = assistantRepository.getOne(id);
        String email = userRepository.getOne(assistant.getUserId()).getEmail();
        int last = Integer.parseInt(email.split("@")[0].substring(0,2));
        p.setFio(assistant.getFirstname() + " " + assistant.getLastname());
        p.setPhone(assistant.getPhone());
        p.setImagePath(assistant.getPhotoPath());
        p.setAbout(assistant.getAboutYou());
        p.setFaculty(assistant.getFaculty());
        p.setProf(assistant.getProfession());
        p.setCourseId(String.valueOf(year-2000-last));
        p.setEmailId(email);
        List<LanguageDto> list = new ArrayList<>();
        for(Language lan: languageRepository.findAllByAssisId(id)){
            LanguageDto l = new LanguageDto();
            l.setLanguage(lan.getLanName());
            l.setLevel(lan.getLanLevel());
            list.add(l);
        }
        p.setLangue(list);


        return p;
    }
    public PersonalDto getVideo(Long id) {

        PersonalDto p = new PersonalDto();
        Assistant assistant = assistantRepository.getOne(id);

        p.setVideo(assistant.getVideo());


        return p;
    }


    public void setApply(Map<String, Object> map) {
        if(map.get("func")!=null && map.get("id")!=null){
            boolean apply = Boolean.parseBoolean(map.get("func").toString());
            Long id = Long.parseLong(map.get("id").toString());
            Assistant assistant= assistantRepository.getOne(id);
            if(apply){

                User user = userRepository.getOne(assistant.getUserId());
                if(studentRepository.existsByUserId(user.getId())){
                    studentRepository.delete(studentRepository.findByUserId(user.getId()));
                }


                Role role = roleRepository.findByName(ERole.ROLE_STUDENT).get();
                user.getRoles().remove(role);
                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository
                        .findByName(ERole.ROLE_ASSISTENT)
                        .orElseThrow(() -> new RuntimeException("Error, Role Student is not found"));
                roles.add(userRole);
                user.setRoles(roles);
                assistant.setAccess(true);
                assistantRepository.save(assistant);
            }
            else {
                System.out.println("deleted " + id);
            }

        }
    }
}
