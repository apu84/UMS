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

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.result.gradesheet.CarryRegistrationResult;
import org.ums.result.gradesheet.GradesheetModel;
import org.ums.result.gradesheet.MutableGradesheetModel;
import org.ums.util.UmsUtils;

@Component
public class GradeSheetBuilder implements Builder<GradesheetModel, MutableGradesheetModel> {
  private final Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();

  @Override
  public void build(JsonObjectBuilder pBuilder, GradesheetModel pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("resultProcessed", pReadOnly.isResultProcessed());
    pBuilder.add("id", pReadOnly.getStudentId());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("program", pReadOnly.getProgramName());
    pBuilder.add("department", pReadOnly.getDepartmentName());
    pBuilder.add("enrollmentSemester", pReadOnly.getEnrollmentSemesterName());
    pBuilder.add("semesterName", pReadOnly.getSemesterName());
    pBuilder.add("year", pReadOnly.getYear());
    pBuilder.add("semester", pReadOnly.getAcademicSemester());
    pBuilder.add("semesterCrHr", pReadOnly.getSemesterCrHr());
    pBuilder.add("cumulativeCrHr", pReadOnly.getCumulativeCrHr());
    pBuilder.add("gpa", pReadOnly.getGpa());
    pBuilder.add("cgpa", pReadOnly.getCGpa());

    List<UGRegistrationResult> regularTheoryCourses = pReadOnly.getRegularCourses().getTheoryCourses();
    List<UGRegistrationResult> regularSessionalCourses = pReadOnly.getRegularCourses().getSessionalCourses();

    List<UGRegistrationResult> clearanceTheoryCourses = pReadOnly.getClearanceCourses().getTheoryCourses();
    List<UGRegistrationResult> clearanceSessionalCourses = pReadOnly.getClearanceCourses().getSessionalCourses();

    List<UGRegistrationResult> improvementTheoryCourses = pReadOnly.getImprovementCourses().getTheoryCourses();
    List<UGRegistrationResult> improvementSessionalCourses = pReadOnly.getImprovementCourses().getSessionalCourses();

    List<CarryRegistrationResult> carryTheoryCourses = pReadOnly.getCarryCourses().getTheoryCourses();
    List<CarryRegistrationResult> carrySessionalCourses = pReadOnly.getCarryCourses().getSessionalCourses();

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
      carryBuilder.add(getCarryJson(carryTheoryCourse));
    });

    carrySessionalCourses.forEach((carrySessionalCourse) -> {
      carryBuilder.add(getCarryJson(carrySessionalCourse));
    });

    pBuilder.add("carry", carryBuilder.build());

    JsonObjectBuilder remarksBuilder = Json.createObjectBuilder();
    remarksBuilder.add("comment", pReadOnly.getRemarks().getComment());
    if(pReadOnly.getRemarks().getCarryCourses() != null && pReadOnly.getRemarks().getCarryCourses().length > 0) {
      JsonArrayBuilder carryArray = Json.createArrayBuilder();
      Arrays.stream(pReadOnly.getRemarks().getCarryCourses()).forEach((course) -> carryArray.add(course));
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

  private JsonObjectBuilder getCarryJson(CarryRegistrationResult carryCourse) {
    JsonObjectBuilder courseBuilder = Json.createObjectBuilder();
    Course course = carryCourse.getCourse();
    courseBuilder.add("no", course.getNo());
    courseBuilder.add("title", course.getTitle());
    courseBuilder.add("crhr", course.getCrHr());
    courseBuilder.add("letterGrade", carryCourse.getGradeLetter());
    courseBuilder.add("gradePoint", GPA_MAP.get(carryCourse.getGradeLetter()));
    courseBuilder.add("regularYear", carryCourse.getRegularYear());
    courseBuilder.add("regularSemester", carryCourse.getRegularSemester());
    return courseBuilder;
  }

  private List<UGRegistrationResult> findCourse(List<UGRegistrationResult> pFindIn, String pCourseId) {
    return pFindIn.stream().filter(pResult -> pResult.getCourseId().equalsIgnoreCase(pCourseId))
        .collect(Collectors.toList());
  }

  @Override
  public void build(MutableGradesheetModel pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
