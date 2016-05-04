package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by ikh on 5/1/2016.
 */
public class MarksSubmissionStatusDto {
    private String courseId;
    private int semesterId;
    private int examType;
    private int total_part;
    private int part_a_total;
    private int part_b_total;
    private int statusId;
    private String statusName;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public int getTotal_part() {
        return total_part;
    }

    public void setTotal_part(int total_part) {
        this.total_part = total_part;
    }

    public int getPart_a_total() {
        return part_a_total;
    }

    public void setPart_a_total(int part_a_total) {
        this.part_a_total = part_a_total;
    }

    public int getPart_b_total() {
        return part_b_total;
    }

    public void setPart_b_total(int part_b_total) {
        this.part_b_total = part_b_total;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
