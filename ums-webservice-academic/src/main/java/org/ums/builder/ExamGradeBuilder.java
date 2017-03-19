package org.ums.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;
import org.ums.formatter.DateFormat;

/**
 * Created by ikh on 4/30/2016.
 */
@Component
public class ExamGradeBuilder implements Builder<ExamGrade, MutableExamGrade> {

  @Autowired
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, ExamGrade pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId());

    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }

    if(pReadOnly.getProgramShortName() != null) {
      pBuilder.add("programShortName", pReadOnly.getProgramShortName().replace("B.Sc in ", ""));
    }

    if(pReadOnly.getCourseId() != null) {
      pBuilder.add("courseId", pReadOnly.getCourseId());
    }

    if(pReadOnly.getCourseNo() != null) {
      pBuilder.add("courseNo", pReadOnly.getCourseNo());
    }

    if(pReadOnly.getCourseTitle() != null) {
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    }

    if(pReadOnly.getCourseCreditHour() != null) {
      pBuilder.add("courseCreditHour", pReadOnly.getCourseCreditHour());
    }

    if(pReadOnly.getTotalStudents() != null) {
      pBuilder.add("totalStudents", pReadOnly.getTotalStudents());
    }

    if(pReadOnly.getLastSubmissionDatePrep() != null) {
      pBuilder.add("lastSubmissionDatePrep", mDateFormat.format(pReadOnly.getLastSubmissionDatePrep()));
    }

    if(pReadOnly.getLastSubmissionDateScr() != null)
      pBuilder.add("lastSubmissionDateScr", mDateFormat.format(pReadOnly.getLastSubmissionDateScr()));

    if(pReadOnly.getLastSubmissionDateHead() != null)
      pBuilder.add("lastSubmissionDateHead", mDateFormat.format(pReadOnly.getLastSubmissionDateHead()));

  }

  @Override
  public void build(MutableExamGrade pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Integer.parseInt(pJsonObject.get("id").toString()));
    }

    if(pJsonObject.getString("lastSubmissionDatePrep") != null) {
      Date date = mDateFormat.parse(pJsonObject.getString("lastSubmissionDatePrep").replace("-", "/"));
      // Set date to last hour and last minute of the dayatch
      Calendar calendar = getCalendar(date);
      pMutable.setLastSubmissionDatePrep(calendar.getTime());
    }

    if(pJsonObject.getString("lastSubmissionDateScr") != null) {
      Date date = mDateFormat.parse(pJsonObject.getString("lastSubmissionDateScr").replace("-", "/"));
      Calendar calendar = getCalendar(date);
      pMutable.setLastSubmissionDateScr(calendar.getTime());
    }

    if(pJsonObject.getString("lastSubmissionDateHead") != null) {
      Date date = mDateFormat.parse(pJsonObject.getString("lastSubmissionDateHead").replace("-", "/"));
      Calendar calendar = getCalendar(date);
      pMutable.setLastSubmissionDateHead(calendar.getTime());
    }

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    // pMutable.setExamTypeId(pJsonObject.getInt("examType"));
    pMutable.setExamType(ExamType.get(pJsonObject.getInt("examType")));
    if(pJsonObject.getString("courseId") != null) {
      pMutable.setCourseId(pJsonObject.getString("courseId"));
    }
  }

  private Calendar getCalendar(Date pDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(pDate);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.HOUR, 23);
    return calendar;
  }

  public void build(MarksSubmissionStatusDto partInfoDto, JsonObject pJsonObject) {
    JsonObject course = pJsonObject.getJsonObject("courseInfo");
    partInfoDto.setCourseId(course.getString("course_id"));
    partInfoDto.setSemesterId(course.getInt("semester_id"));
    partInfoDto.setExamType(ExamType.get(course.getInt("exam_typeId")));
    partInfoDto.setTotal_part(course.getInt("total_part"));
    partInfoDto.setPart_a_total(course.getInt("part_a_total"));
    partInfoDto.setPart_b_total(course.getInt("part_b_total"));
    partInfoDto.setCourseType(CourseType.get(course.getInt("course_typeId")));
  }

  public List<StudentGradeDto> build(JsonObject pJsonObject) {

    JsonArray entries = pJsonObject.getJsonArray("gradeList");
    JsonObject courseInfo = pJsonObject.getJsonObject("courseInfo");

    List<StudentGradeDto> gradeList = new ArrayList<>(entries.size());
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      StudentGradeDto grade = new StudentGradeDto();
      grade.setStudentId(jsonObject.getString("studentId"));
      if(courseInfo.getInt("course_typeId") == 1) { // For only theory courses
        grade.setQuiz((jsonObject.getString("quiz") == null || jsonObject.getString("quiz").equalsIgnoreCase("")) ? -1
            : Double.parseDouble(jsonObject.getString("quiz")));
        grade.setClassPerformance((jsonObject.getString("classPerformance") == null || jsonObject.getString(
            "classPerformance").equalsIgnoreCase("")) ? -1 : Double.parseDouble(jsonObject
            .getString("classPerformance")));
        grade
            .setPartA((jsonObject.getString("partA") == null || jsonObject.getString("partA").equalsIgnoreCase("")) ? -1
                : Double.parseDouble(jsonObject.getString("partA")));
        grade.setPartAAddiInfo(jsonObject.getString("partAAddiInfo"));
        if(courseInfo.getInt("total_part") == 2) {
          grade
              .setPartB((jsonObject.getString("partB") == null || jsonObject.getString("partB").equalsIgnoreCase("")) ? -1
                  : Double.parseDouble(jsonObject.getString("partB")));
          grade.setPartBAddiInfo(jsonObject.getString("partBAddiInfo"));
        }
      }
      grade.setTotal((jsonObject.getString("total") == null || jsonObject.getString("total").equalsIgnoreCase("")) ? -1
          : Double.parseDouble(jsonObject.getString("total")));
      grade.setGradeLetter((jsonObject.getString("gradeLetter") == null || jsonObject.getString("gradeLetter")
          .equalsIgnoreCase("")) ? "" : jsonObject.getString("gradeLetter"));
      grade.setStatusId(jsonObject.getInt("statusId"));
      grade.setStatus(StudentMarksSubmissionStatus.values()[jsonObject.getInt("statusId")]);

      grade.setRegType(Integer.parseInt(jsonObject.getString("regType")));
      // grade.setrec(RecheckStatus.values()[jsonObject.getInt("status")]);
      // grade.setStatus(StudentMarksSubmissionStatus.values()[jsonObject.getInt("status")]);

      gradeList.add(grade);
    }
    return gradeList;
  }

  public ArrayList<List<StudentGradeDto>> buildForRecheckApproveGrade(String action, String actor,
      JsonObject pJsonObject) {

    ArrayList<List<StudentGradeDto>> recheckApproveList = new ArrayList<>();
    JsonArray recheckEntries = pJsonObject.getJsonArray("recheckList");
    JsonArray approveEntries = pJsonObject.getJsonArray("approveList");

    List<StudentGradeDto> recheckList = new ArrayList<>(recheckEntries.size());
    List<StudentGradeDto> approveList = new ArrayList<>(approveEntries.size());

    if(action.equalsIgnoreCase("save")) {
      for(int i = 0; i < recheckEntries.size(); i++) {
        JsonObject jsonObject = recheckEntries.getJsonObject(i);
        StudentGradeDto grade = new StudentGradeDto();
        grade.setRecheckStatus(RecheckStatus.RECHECK_TRUE);
        grade.setStudentId(jsonObject.getString("studentId"));
        grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
        recheckList.add(grade);
      }
      for(int i = 0; i < approveEntries.size(); i++) {
        JsonObject jsonObject = approveEntries.getJsonObject(i);
        StudentGradeDto grade = new StudentGradeDto();
        grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
        grade.setStatus(getMarksSubmissionStatus(actor, action, "approve"));
        grade.setStudentId(jsonObject.getString("studentId"));
        grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
        approveList.add(grade);
      }
    }
    else if(action.equalsIgnoreCase("recheck") || action.equalsIgnoreCase("recheck_request_submit")) {
      for(int i = 0; i < recheckEntries.size(); i++) {
        JsonObject jsonObject = recheckEntries.getJsonObject(i);
        StudentGradeDto grade = new StudentGradeDto();
        grade.setRecheckStatus(RecheckStatus.RECHECK_TRUE);
        grade.setStatus(getMarksSubmissionStatus(actor, action, "recheck"));
        grade.setStudentId(jsonObject.getString("studentId"));
        if(action.equalsIgnoreCase("recheck"))
          grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
        else if(action.equalsIgnoreCase("recheck_request_submit"))
          grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor, action));
        recheckList.add(grade);
      }
      for(int i = 0; i < approveEntries.size(); i++) {
        JsonObject jsonObject = approveEntries.getJsonObject(i);
        StudentGradeDto grade = new StudentGradeDto();
        grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
        grade.setStatus(getMarksSubmissionStatus(actor, action, "approve"));
        grade.setStudentId(jsonObject.getString("studentId"));
        grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
        approveList.add(grade);
      }
    }
    else if(action.equalsIgnoreCase("approve")) {
      for(int i = 0; i < approveEntries.size(); i++) {
        JsonObject jsonObject = approveEntries.getJsonObject(i);
        StudentGradeDto grade = new StudentGradeDto();
        grade.setRecheckStatus(RecheckStatus.RECHECK_FALSE);
        grade.setStatus(getMarksSubmissionStatus(actor, action, "approve"));
        grade.setStudentId(jsonObject.getString("studentId"));
        grade.setPreviousStatusString(getPrevMarksSubmissionStatus(actor));
        approveList.add(grade);
      }
    }
    recheckApproveList.add(recheckList);
    recheckApproveList.add(approveList);
    return recheckApproveList;
  }

  private StudentMarksSubmissionStatus getMarksSubmissionStatus(String actor, String action, String gradeType) {
    if(actor.equals("scrutinizer")) {
      if(action.equals("save") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.SCRUTINIZE;
      else if(action.equals("recheck") && gradeType.equals("recheck"))
        return StudentMarksSubmissionStatus.NONE;
      else if(action.equals("recheck") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.SCRUTINIZE;
      else if(action.equals("approve") && gradeType.equals("recheck"))
        return StudentMarksSubmissionStatus.SCRUTINIZED;
      else if(action.equals("approve") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.SCRUTINIZED;
    }
    if(actor.equals("head")) {
      if(action.equals("save") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.APPROVE;
      else if(action.equals("recheck") && gradeType.equals("recheck"))
        return StudentMarksSubmissionStatus.NONE;
      else if(action.equals("recheck") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.APPROVE;
      else if(action.equals("approve") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.APPROVED;
    }
    if(actor.equals("coe")) {
      if(action.equals("save") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.ACCEPT;
      else if(action.equals("recheck") && gradeType.equals("recheck"))
        return StudentMarksSubmissionStatus.NONE;
      else if(action.equals("recheck") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.ACCEPT;
      else if(action.equals("approve") && gradeType.equals("approve"))
        return StudentMarksSubmissionStatus.ACCEPTED;
      else if(action.equals("recheck_request_submit"))
        return StudentMarksSubmissionStatus.ACCEPTED;

    }
    return null;
  }

  private String getPrevMarksSubmissionStatus(String actor) {
    if(actor.equals("scrutinizer"))
      return StudentMarksSubmissionStatus.SUBMITTED.getId() + "," + StudentMarksSubmissionStatus.SCRUTINIZE.getId();
    if(actor.equals("head"))
      return StudentMarksSubmissionStatus.SCRUTINIZED.getId() + "," + StudentMarksSubmissionStatus.APPROVE.getId();
    if(actor.equals("coe"))
      return StudentMarksSubmissionStatus.APPROVED.getId() + "," + StudentMarksSubmissionStatus.ACCEPT.getId();
    if(actor.equals("vc"))
      return StudentMarksSubmissionStatus.ACCEPTED.getId() + "";
    return null;
  }

  private String getPrevMarksSubmissionStatus(String actor, String action) {
    if(actor.equals("coe") && action.equalsIgnoreCase("recheck_request_submit"))
      return String.valueOf(StudentMarksSubmissionStatus.ACCEPTED.getId());

    return null;
  }

}
