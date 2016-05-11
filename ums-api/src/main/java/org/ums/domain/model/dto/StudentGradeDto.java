package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by ikh on 4/29/2016.
 */
public class StudentGradeDto {
    private String studentId;
    private String studentName;
    private Float quiz;
    private Float classPerformance;
    private Float partA;
    private Float partB;
    private Float partTotal;
    private Float total;
    private String gradeLetter;
    private Float gradePoint;
    private int status;
    private String statusName;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Float getQuiz() {
        return quiz;
    }

    public void setQuiz(Float quiz) {
        this.quiz = quiz;
    }

    public Float getClassPerformance() {
        return classPerformance;
    }

    public void setClassPerformance(Float classPerformance) {
        this.classPerformance = classPerformance;
    }

    public Float getPartA() {
        return partA;
    }

    public void setPartA(Float partA) {
        this.partA = partA;
    }

    public Float getPartB() {
        return partB;
    }

    public void setPartB(Float partB) {
        this.partB = partB;
    }

    public Float getPartTotal() {
        return partTotal;
    }

    public void setPartTotal(Float partTotal) {
        this.partTotal = partTotal;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public Float getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Float gradePoint) {
        this.gradePoint = gradePoint;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
