package org.ums.domain.model.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.enums.academic.GradeSubmissionColorCode;

import java.util.Date;
import java.util.List;

public class MarksSubmissionStatusDto {
  private String courseId;
  private String courseNo;
  private String courseTitle;
  private int semesterId;
  private String semesterName;
  private float cRhR;
  private String deptSchoolShortName;
  private String deptSchoolLongName;
  private int total_part;
  private int part_a_total;
  private int part_b_total;
  private int statusId;
  private CourseMarksSubmissionStatus status;
  private String statusName;
  private String action;
  private CourseType courseType;
  private ExamType examType;

  private int courseTypeId;
  private int examTypeId;
  private String courseTypeName;
  private String examTypeName;

  private Integer courseCreditHour;
  private String programShortName;
  private String programLongName;
  private String examDate;
  private Integer totalStudents;

  private String preparerId;
  private String preparerName;
  private String scrutinizerId;
  private String scrutinizerName;
  private int year;
  private int semester;
  private String offeredTo;
  private Date lastSubmissionDatePrep;
  private Date lastSubmissionDateScr;
  private Date lastSubmissionDateHead;
  private Date lastSubmissionDateCoe;
  private List<CourseTeacherDto> courseTeacherList;

  private boolean isSubmissionDateOver;
  private GradeSubmissionColorCode submissionColorCode;

  private int mId;

  public Date getLastSubmissionDateCoe() {
    return lastSubmissionDateCoe;
  }

  public void setLastSubmissionDateCoe(Date pLastSubmissionDateCoe) {
    lastSubmissionDateCoe = pLastSubmissionDateCoe;
  }

  public Date getLastSubmissionDateScr() {
    return lastSubmissionDateScr;
  }

  public void setLastSubmissionDateScr(Date pLastSubmissionDateScr) {
    lastSubmissionDateScr = pLastSubmissionDateScr;
  }

  public Date getLastSubmissionDateHead() {
    return lastSubmissionDateHead;
  }

  public void setLastSubmissionDateHead(Date pLastSubmissionDateHead) {
    lastSubmissionDateHead = pLastSubmissionDateHead;
  }

  public int getId() {
    return mId;
  }

  public void setId(int pId) {
    mId = pId;
  }

  public Integer getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(Integer pTotalStudents) {
    totalStudents = pTotalStudents;
  }

  public Integer getCourseCreditHour() {
    return courseCreditHour;
  }

  public void setCourseCreditHour(Integer pCourseCreditHour) {
    courseCreditHour = pCourseCreditHour;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String pExamDate) {
    examDate = pExamDate;
  }

  public Date getLastSubmissionDatePrep() {
    return lastSubmissionDatePrep;
  }

  public void setLastSubmissionDatePrep(Date pLastSubmissionDatePrep) {
    lastSubmissionDatePrep = pLastSubmissionDatePrep;
  }

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

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
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

  public String getDeptSchoolShortName() {
    return deptSchoolShortName;
  }

  public void setDeptSchoolShortName(String deptSchoolShortName) {
    this.deptSchoolShortName = deptSchoolShortName;
  }

  public String getDeptSchoolLongName() {
    return deptSchoolLongName;
  }

  public void setDeptSchoolLongName(String deptSchoolLongName) {
    this.deptSchoolLongName = deptSchoolLongName;
  }

  public int getCourseTypeId() {
    return courseTypeId;
  }

  public void setCourseTypeId(int courseTypeId) {
    this.courseTypeId = courseTypeId;
  }

  public int getExamTypeId() {
    return examTypeId;
  }

  public void setExamTypeId(int examTypeId) {
    this.examTypeId = examTypeId;
  }

  public String getCourseTypeName() {
    return courseTypeName;
  }

  public void setCourseTypeName(String courseTypeName) {
    this.courseTypeName = courseTypeName;
  }

  public String getExamTypeName() {
    return examTypeName;
  }

  public void setExamTypeName(String examTypeName) {
    this.examTypeName = examTypeName;
  }

  public boolean isSubmissionDateOver() {
    return isSubmissionDateOver;
  }

  public void setSubmissionDateOver(boolean isSubmissionDateOver) {
    this.isSubmissionDateOver = isSubmissionDateOver;
  }

  public GradeSubmissionColorCode getSubmissionColorCode() {
    return submissionColorCode;
  }

  public void setSubmissionColorCode(GradeSubmissionColorCode submissionColorCode) {
    this.submissionColorCode = submissionColorCode;
  }

  public String getProgramShortName() {
    return programShortName;
  }

  public void setProgramShortName(String programShortName) {
    this.programShortName = programShortName;
  }

  public String getProgramLongName() {
    return programLongName;
  }

  public void setProgramLongName(String programLongName) {
    this.programLongName = programLongName;
  }

  public String toString() {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    return gson.toJson(this);
  }

}
