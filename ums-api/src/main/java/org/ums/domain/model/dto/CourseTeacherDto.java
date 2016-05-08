package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by ikh on 5/7/2016.
 */
public class CourseTeacherDto {
    private String teacher_id;
    private String teacher_name;
    private String section;

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
