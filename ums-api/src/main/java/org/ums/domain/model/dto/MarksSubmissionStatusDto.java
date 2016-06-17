package org.ums.domain.model.dto;

import com.google.gson.Gson;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;

import java.util.List;

/**
 * Created by ikh on 5/1/2016.
 */
public class MarksSubmissionStatusDto {
    private String courseId;
    private String courseNo;
    private String courseTitle;
    private int semesterId;
    private String semesterName;
    private float cRhR;
    private String deptSchoolName;
    private int examType;
    private String examTypeName;
    private int total_part;
    private int part_a_total;
    private int part_b_total;
    private int statusId;
    private CourseMarksSubmissionStatus status;
    private String statusName;
    private String action;
    private CourseType courseType;

    private String preparerId;
    private String preparerName;
    private String scrutinizerId;
    private String scrutinizerName;
    private int year;
    private int semester;
    private String offeredTo;
    private List<CourseTeacherDto> courseTeacherList;


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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getPreparerId() {
        return preparerId;
    }

    public void setPreparerId(String preparerId) {
        this.preparerId = preparerId;
    }

    public String getPreparerName() {
        return preparerName;
    }

    public void setPreparerName(String preparerName) {
        this.preparerName = preparerName;
    }

    public String getScrutinizerId() {
        return scrutinizerId;
    }

    public void setScrutinizerId(String scrutinizerId) {
        this.scrutinizerId = scrutinizerId;
    }

    public String getScrutinizerName() {
        return scrutinizerName;
    }

    public void setScrutinizerName(String scrutinizerName) {
        this.scrutinizerName = scrutinizerName;
    }

    public List<CourseTeacherDto> getCourseTeacherList() {
        return courseTeacherList;
    }

    public void setCourseTeacherList(List<CourseTeacherDto> courseTeacherList) {
        this.courseTeacherList = courseTeacherList;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getOfferedTo() {
        return offeredTo;
    }

    public void setOfferedTo(String offeredTo) {
        this.offeredTo = offeredTo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public CourseMarksSubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(CourseMarksSubmissionStatus status) {
        this.status = status;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public float getcRhR() {
        return cRhR;
    }

    public void setcRhR(float cRhR) {
        this.cRhR = cRhR;
    }

    public String getDeptSchoolName() {
        return deptSchoolName;
    }

    public void setDeptSchoolName(String deptSchoolName) {
        this.deptSchoolName = deptSchoolName;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
