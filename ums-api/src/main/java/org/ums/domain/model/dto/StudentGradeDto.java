package org.ums.domain.model.dto;

import com.google.gson.Gson;
import org.ums.enums.CourseRegType;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;

/**
 * Created by ikh on 4/29/2016.
 */
public class StudentGradeDto {
    private String studentId;
    private String studentName;
    private Double quiz;
    private Double classPerformance;
    private Double partA;
    private Double partB;
    private Double partTotal;
    private Double total;
    private String gradeLetter;
    private Double gradePoint;
    private StudentMarksSubmissionStatus status;
    private int statusId;
    private String statusName;

    private RecheckStatus recheckStatus;
    private int recheckStatusId;
    private int regType;

    //Don't know whether we need them or not
    private String previousStatusString; //This will hold the candidates previous Status String which will be used in an in query

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

    public Double getQuiz() {
        return quiz;
    }

    public void setQuiz(Double quiz) {
        this.quiz = quiz;
    }

    public Double getClassPerformance() {
        return classPerformance;
    }

    public void setClassPerformance(Double classPerformance) {
        this.classPerformance = classPerformance;
    }

    public Double getPartA() {
        return partA;
    }

    public void setPartA(Double partA) {
        this.partA = partA;
    }

    public Double getPartB() {
        return partB;
    }

    public void setPartB(Double partB) {
        this.partB = partB;
    }

    public Double getPartTotal() {
        return partTotal;
    }

    public void setPartTotal(Double partTotal) {
        this.partTotal = partTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public StudentMarksSubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(StudentMarksSubmissionStatus status) {
        this.status = status;
    }

    public RecheckStatus getRecheckStatus() {
        return recheckStatus;
    }

    public void setRecheckStatus(RecheckStatus recheckStatus) {
        this.recheckStatus = recheckStatus;
    }

    public String getPreviousStatusString() {
        return previousStatusString;
    }

    public void setPreviousStatusString(String previousStatusString) {
        this.previousStatusString = previousStatusString;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getRecheckStatusId() {
        return recheckStatusId;
    }

    public CourseRegType getRegType() {
        return CourseRegType.get(regType);
    }

    public void setRegType(int regType) {
        this.regType = regType;
    }

    public void setRecheckStatusId(int recheckStatusId) {
        this.recheckStatusId = recheckStatusId;
    }
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
