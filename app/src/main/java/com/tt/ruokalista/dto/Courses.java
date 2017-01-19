package com.tt.ruokalista.dto;

import java.util.List;

public class Courses {

    private Meta meta;

    private List<CourseItem> courses;

    public Courses() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CourseItem> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseItem> courses) {
        this.courses = courses;
    }

}
