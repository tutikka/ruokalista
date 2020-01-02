package com.tt.ruokalista.dto;

import java.util.Map;

public class Courses {

    private Meta meta;

    private Map<String, CourseItem> courses;

    public Courses() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Map<String, CourseItem> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, CourseItem> courses) {
        this.courses = courses;
    }

}
