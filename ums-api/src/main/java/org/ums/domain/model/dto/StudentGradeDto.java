package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by ikh on 4/29/2016.
 */
public class StudentGradeDto {
    private String studentId;
    private String studentName;
    private float quiz;
    private float classPerformance;
    private float partA;
    private float partB;
    private float partTotal;
    private float total;
    private String gradeLetter;
    private float gradePoint;
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

    public float getQuiz() {
        return quiz;
    }

    public void setQuiz(float quiz) {
        this.quiz = quiz;
    }

    public float getClassPerformance() {
        return classPerformance;
    }

    public void setClassPerformance(float classPerformance) {
        this.classPerformance = classPerformance;
    }

    public float getPartA() {
        return partA;
    }

    public void setPartA(float partA) {
        this.partA = partA;
    }

    public float getPartB() {
        return partB;
    }

    public void setPartB(float partB) {
        this.partB = partB;
    }

    public float getPartTotal() {
        return partTotal;
    }

    public void setPartTotal(float partTotal) {
        this.partTotal = partTotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public float getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(float gradePoint) {
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
