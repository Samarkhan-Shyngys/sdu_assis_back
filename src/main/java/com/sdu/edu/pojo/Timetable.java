package com.sdu.edu.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Timetable {
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
    private List<String> sunday;
}
