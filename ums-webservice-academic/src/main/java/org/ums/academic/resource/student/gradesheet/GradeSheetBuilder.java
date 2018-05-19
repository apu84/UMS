package org.ums.academic.resource.student.gradesheet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.CourseType;
import org.ums.manager.StudentRecordManager;
import org.ums.result.gradesheet.GradesheetModel;
import org.ums.result.gradesheet.MutableGradesheetModel;
import org.ums.util.UmsUtils;

@Component
public class GradeSheetBuilder implements Builder<GradesheetModel, MutableGradesheetModel> {
  private final Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();
  @Autowired
  private StudentRecordManager mStudentRecordManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, GradesheetModel pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    Student student = pReadOnly.getStudent();
    pBuilder.add("id", student.getId());
    pBuilder.add("name", student.getFullName());
    pBuilder.add("program", student.getProgram().getLongName());
    pBuilder.add("department", student.getDepartment().getLongName());
    pBuilder.add("enrollmentSemester", student.getSemester().getName());
    StudentRecord studentRecord = pReadOnly.getStudentRecord();
    pBuilder.add("semesterName", studentRecord.getSemester().getName());
    pBuilder.add("year", studentRecord.getYear());
    pBuilder.add("semester", studentRecord.getAcademicSemester());
    pBuilder.add("semesterCrHr", studentRecord.getCompletedCrHr());
    pBuilder.add("cumulativeCrHr", studentRecord.getTotalCompletedCrHr());
    pBuilder.add("gpa", studentRecord.getGPA().toString().length() > 5 ?
        studentRecord.getGPA().toString().substring(0, 5) : studentRecord.getGPA().toString());
    pBuilder.add("cgpa", studentRecord.getCGPA().toString().length() > 5 ?
        studentRecord.getCGPA().toString().substring(0, 5) : studentRecord.getCGPA().toString());

    List<UGRegistrationResult> regularTheoryCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.THEORY, CourseRegType.REGULAR);
    List<UGRegistrationResult> regularSessionalCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.SESSIONAL, CourseRegType.REGULAR);

    List<UGRegistrationResult> clearanceTheoryCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.THEORY, CourseRegType.CLEARANCE);
    List<UGRegistrationResult> clearanceSessionalCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.SESSIONAL, CourseRegType.CLEARANCE);

    List<UGRegistrationResult> improvementTheoryCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.THEORY, CourseRegType.IMPROVEMENT);
    List<UGRegistrationResult> improvementSessionalCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.SESSIONAL, CourseRegType.IMPROVEMENT);

    List<UGRegistrationResult> carryTheoryCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.THEORY, CourseRegType.CARRY);
    List<UGRegistrationResult> carrySessionalCourses =
        getCourses(pReadOnly.getSemesterResults(), CourseType.SESSIONAL, CourseRegType.CARRY);

    JsonArrayBuilder regularBuilder = Json.createArrayBuilder();

    regularTheoryCourses.forEach((regularTheoryCourse) -> {
      regularBuilder.add(getJson(regularTheoryCourse, clearanceTheoryCourses, improvementTheoryCourses));
    });

    regularSessionalCourses.forEach((regularSessionalCourse) -> {
      regularBuilder.add(getJson(regularSessionalCourse, clearanceSessionalCourses, improvementSessionalCourses));
    });

    pBuilder.add("regular", regularBuilder.build());

    JsonArrayBuilder carryBuilder = Json.createArrayBuilder();

    carryTheoryCourses.forEach((carryTheoryCourse) -> {
      carryBuilder.add(getCarryJson(carryTheoryCourse, pReadOnly.getAllResults()));
    });

    carrySessionalCourses.forEach((carrySessionalCourse) -> {
      carryBuilder.add(getCarryJson(carrySessionalCourse, pReadOnly.getAllResults()));
    });

    pBuilder.add("carry", carryBuilder.build());
    JsonObjectBuilder remarksBuilder = Json.createObjectBuilder();
    String remarks = studentRecord.getTabulationSheetRemarks();
    String comment;
    String[] carry = null;
    if(remarks.contains("with")) {
      comment = remarks.substring(0, remarks.indexOf("with")).trim();
      carry = remarks.substring(remarks.indexOf("with")).replace("with carry over in", "").split(",");
    } else {
      comment = remarks.trim();
    }
    remarksBuilder.add("comment", comment);
    if(carry != null && carry.length > 0) {
      JsonArrayBuilder carryArray = Json.createArrayBuilder();
      Arrays.stream(carry).forEach((course) -> carryArray.add(course.trim()));
      remarksBuilder.add("carry", carryArray.build());
    }
    pBuilder.add("remarks", remarksBuilder.build());
  }

  private JsonObjectBuilder getJson(UGRegistrationResult regularCourse, List<UGRegistrationResult> clearanceCourses,
      List<UGRegistrationResult> improvementCourses) {
    JsonObjectBuilder courseBuilder = Json.createObjectBuilder();
    Course course = regularCourse.getCourse();
    courseBuilder.add("no", course.getNo());
    courseBuilder.add("title", course.getTitle());
    courseBuilder.add("crhr", course.getCrHr());
    courseBuilder.add("letterGrade", regularCourse.getGradeLetter());
    courseBuilder.add("gradePoint", GPA_MAP.get(regularCourse.getGradeLetter()));
    List<UGRegistrationResult> clearance = findCourse(clearanceCourses, course.getId());
    List<UGRegistrationResult> improvement = findCourse(improvementCourses, course.getId());
    if(clearance.size() > 0) {
      courseBuilder.add("clearance", clearanceBuilder(clearance.get(0)).build());
    }

    if(improvement.size() > 0) {
      courseBuilder.add("clearance", clearanceBuilder(improvement.get(0)).build());
    }
    return courseBuilder;
  }

  private JsonObjectBuilder clearanceBuilder(UGRegistrationResult pResult) {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    objectBuilder.add("letterGrade", pResult.getGradeLetter());
    objectBuilder.add("gradePoint", GPA_MAP.get(pResult.getGradeLetter()));
    return objectBuilder;
  }

  private JsonObjectBuilder getCarryJson(UGRegistrationResult carryCourse, List<UGRegistrationResult> allCourse) {
    JsonObjectBuilder courseBuilder = Json.createObjectBuilder();
    Course course = carryCourse.getCourse();
    courseBuilder.add("no", course.getNo());
    courseBuilder.add("title", course.getTitle());
    courseBuilder.add("crhr", course.getCrHr());
    courseBuilder.add("letterGrade", carryCourse.getGradeLetter());
    courseBuilder.add("gradePoint", GPA_MAP.get(carryCourse.getGradeLetter()));

    List<UGRegistrationResult> regularCourse =
        allCourse.stream().filter((pResult) -> pResult.getCourseId().equalsIgnoreCase(carryCourse.getCourseId())
            && pResult.getType() == CourseRegType.REGULAR).collect(Collectors.toList());
    if(regularCourse.size() > 0) {
      StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(regularCourse.get(0).getStudentId(),
          regularCourse.get(0).getSemesterId());
      courseBuilder.add("regularYear", studentRecord.getYear());
      courseBuilder.add("regularSemester", studentRecord.getAcademicSemester());
    }
    return courseBuilder;
  }

  private List<UGRegistrationResult> getCourses(final List<UGRegistrationResult> pResults, CourseType pCourseType,
      CourseRegType pCourseRegType) {
    return pResults.stream()
        .filter((result -> result.getCourse().getCourseType() == pCourseType && result.getType() == pCourseRegType))
        .collect(Collectors.toList());
  }

  private List<UGRegistrationResult> findCourse(List<UGRegistrationResult> pFindIn, String pCourseId) {
    return pFindIn.stream().filter(pResult -> pResult.getCourseId().equalsIgnoreCase(pCourseId))
        .collect(Collectors.toList());
  }

  @Override
  public void build(MutableGradesheetModel pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
