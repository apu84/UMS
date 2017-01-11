package org.ums.academic.resource.helper;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.OptionalCourseApplicationBuilder;
import org.ums.domain.model.dto.OptCourseStudentDto;
import org.ums.domain.model.dto.OptSectionDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.dto.SemesterWiseCrHrDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.enums.OptCourseApplicationStatus;
import org.ums.enums.OptCourseCourseStatus;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.manager.StudentManager;
import org.ums.persistent.dao.PersistentOptionalCourseApplicationDao;
import org.ums.persistent.dao.PersistentSemesterWiseCrHrDao;
import org.ums.response.type.GenericMessageResponse;
import org.ums.response.type.GenericResponse;

@Component
public class OptionalCourseApplicationResourceHelper {

  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  CourseManager mCourseManager;
  @Autowired
  private PersistentOptionalCourseApplicationDao mManager;
  @Autowired
  private OptionalCourseApplicationBuilder mBuilder;
  @Autowired
  SemesterSyllabusMapManager mSemesterSyllabusManager;

  @Autowired
  CourseResourceHelper mCourseResourceHelper;

  @Autowired
  private PersistentSemesterWiseCrHrDao mSemesterWiseCrHrManager;

  public PersistentOptionalCourseApplicationDao getContentManager() {
    return mManager;
  }

  public OptionalCourseApplicationBuilder getBuilder() {
    return mBuilder;
  }

  public JsonObject getSemesterWiseCrHrInfo(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {

    Syllabus syllabus =
        mSemesterSyllabusManager.getSyllabusForSemester(pSemesterId, pProgramId, pYear, pSemester);
    SemesterWiseCrHrDto crHr =
        mSemesterWiseCrHrManager.getCrHrInfoByYearSemester(syllabus.getId(), pYear, pSemester);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    jsonReader = Json.createReader(new StringReader(crHr.toString()));
    object1 = jsonReader.readObject();
    jsonReader.close();

    object.add("CrHr", object1);

    return object.build();

  }

  public JsonObject getApplicationStatistics(final Integer pSemesterId, final Integer pProgramId) {
    List<OptionalCourseApplicationStatDto> statList =
        mManager.getApplicationStatistics(pSemesterId, pProgramId, 1, 1);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(OptionalCourseApplicationStatDto stat : statList) {
      jsonReader = Json.createReader(new StringReader(stat.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();

  }

  public Response saveApprovedAndApplicationCourses(final Integer pSemesterId,
      final Integer pProgramId, int pYear, int pSemester, final JsonObject pJsonObject) {
    OptionalCourseApplicationBuilder builder = getBuilder();
    List<Course> approvedCourseList = new ArrayList<>();
    builder.build(approvedCourseList, pJsonObject, "approved");

    List<Course> callForApplicationCourseList = new ArrayList<>();
    builder.build(callForApplicationCourseList, pJsonObject, "callForApplication");

    mManager.deleteApplicationCourses(pSemesterId, pProgramId, pYear, pSemester);
    mManager.insertApplicationCourses(pSemesterId, pProgramId, pYear, pSemester,
        callForApplicationCourseList);
    mManager.insertApprovedCourses(pSemesterId, pProgramId, pYear, pSemester, approvedCourseList);

    return Response.noContent().build();
  }

  public JsonObject getStudentList(int pSemesterId, String pCourseId, String pStatus) {
    List<OptCourseStudentDto> studentList =
        getContentManager().getStudentList(pSemesterId, pCourseId, pStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(OptCourseStudentDto student : studentList) {
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public JsonObject getNonAssignedSectionStudentList(int pSemesterId, int pProgramId,
      String pCourseId) {
    List<OptCourseStudentDto> studentList =
        getContentManager().getNonAssignedSectionStudentList(pSemesterId, pProgramId, pCourseId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(OptCourseStudentDto student : studentList) {
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public JsonObject getOptionalSectionListWithStudents(int pSemesterId, int pProgramId,
      String pCourseId) {
    List<OptSectionDto> studentList =
        getContentManager().getOptionalSectionListWithStudents(pSemesterId, pProgramId, pCourseId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(OptSectionDto student : studentList) {
      student
          .setStudentList(mStudentManager.getStudentListFromStudentsString(student.getStudents()));
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public Response deleteSection(int pSemesterId, int pProgramId, String pCourseId,
      String pSectionName) {
    mManager.deleteSection(pSemesterId, pProgramId, pCourseId, pSectionName);
    return Response.noContent().build();
  }

  public Response mergeSelection(int pSemesterId, int pProgramId, String pCourseId,
      String pSectionName, JsonObject pStudents) {

    mManager.mergeSection(pSemesterId, pProgramId, pCourseId, pSectionName,
        pStudents.getString("students"));
    // URI contextURI =
    // pUriInfo.getBaseUriBuilder().path(StudentResource.class).path(StudentResource.class,
    // "get").build(mutableStudent.getId());
    URI contextURI = null;
    try {
      contextURI = new URI("");
    } catch(URISyntaxException ue) {
      throw new RuntimeException(ue);
    }
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public JsonObject getApplicationStatus(String pStudentId, int pSemesterId) {
    OptCourseApplicationStatus status =
        getContentManager().getApplicationStatus(pStudentId, pSemesterId);
    JsonObject object =
        Json.createObjectBuilder().add("status_id", status.getId())
            .add("status_name", status.getLabel()).build();

    return object;
  }

  public JsonObject getAppliedCoursesByStudent(String pStudentId, int pSemesterId, int pProgramId) {
    // Todo: Need to verify that the user requested for the resource is in the same department under
    // the requested program Id.
    List<OptCourseStudentDto> courseList =
        getContentManager().getAppliedCoursesByStudent(pStudentId, pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(OptCourseStudentDto course : courseList) {
      jsonReader = Json.createReader(new StringReader(course.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  @Transactional
  public GenericResponse<Map> updateApplicationStatusByCourse(int pSemesterId, String pCourseId,
      final JsonObject pJsonObject) {

    OptionalCourseApplicationBuilder builder = getBuilder();
    List<String> approveStudentList = new ArrayList<>();
    builder.buildStudent(approveStudentList, pJsonObject, "approve");

    List<String> rejectStudentList = new ArrayList<>();
    builder.buildStudent(rejectStudentList, pJsonObject, "reject");

    List<String> removeStudentList = new ArrayList<>();
    builder.buildStudent(removeStudentList, pJsonObject, "remove");

    Course course = mCourseManager.get(pCourseId);

    mManager.updateCourseStatus(pSemesterId, pCourseId, approveStudentList,
        OptCourseCourseStatus.APPROVED.getId());
    mManager.updateCourseStatus(pSemesterId, pCourseId, rejectStudentList,
        OptCourseCourseStatus.REJECTED.getId());
    mManager.updateCourseStatusForDependentCourse(pSemesterId, pCourseId, removeStudentList,
        OptCourseCourseStatus.REJECTED.getId());
    mManager.deleteCourseAppliedByTeacher(pSemesterId, pCourseId, removeStudentList);

    if(course.getPairCourseId() != null && !course.getPairCourseId().equalsIgnoreCase("")) {
      mManager.updateCourseStatus(pSemesterId, course.getPairCourseId(), approveStudentList,
          OptCourseCourseStatus.APPROVED.getId());
      mManager.updateCourseStatus(pSemesterId, course.getPairCourseId(), rejectStudentList,
          OptCourseCourseStatus.REJECTED.getId());
      mManager.updateCourseStatusForDependentCourse(pSemesterId, course.getPairCourseId(),
          removeStudentList, OptCourseCourseStatus.REJECTED.getId());
      mManager.deleteCourseAppliedByTeacher(pSemesterId, course.getPairCourseId(),
          removeStudentList);
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS,
        "Successfully Updated Information.");
    // return Response.noContent().build();
  }

  @Transactional
  public Response updateApplicationStatusByStudent(int pSemesterId, String pStudentId,
      final JsonObject pJsonObject) {

    OptionalCourseApplicationBuilder builder = getBuilder();
    StringBuilder approveCourse = new StringBuilder("");
    builder.buildCourseId(approveCourse, pJsonObject, "approve");

    StringBuilder rejectCourse = new StringBuilder("");
    builder.buildCourseId(rejectCourse, pJsonObject, "reject");

    StringBuilder removeCourse = new StringBuilder("");
    builder.buildCourseId(removeCourse, pJsonObject, "remove");

    List<String> studentList = new ArrayList<>();
    studentList.add(pStudentId);

    Course course;

    if(!approveCourse.toString().equalsIgnoreCase("")) {
      mManager.updateCourseStatus(pSemesterId, approveCourse.toString(), studentList,
          OptCourseCourseStatus.APPROVED.getId());
      course = mCourseManager.get(approveCourse.toString());
      if(course.getPairCourseId() != null && !course.getPairCourseId().equalsIgnoreCase("")) {
        mManager.updateCourseStatus(pSemesterId, course.getPairCourseId(), studentList,
            OptCourseCourseStatus.APPROVED.getId());
      }
    }

    if(!rejectCourse.toString().equalsIgnoreCase("")) {
      mManager.updateCourseStatus(pSemesterId, rejectCourse.toString(), studentList,
          OptCourseCourseStatus.REJECTED.getId());
      course = mCourseManager.get(rejectCourse.toString());
      if(course.getPairCourseId() != null && !course.getPairCourseId().equalsIgnoreCase("")) {
        mManager.updateCourseStatus(pSemesterId, course.getPairCourseId(), studentList,
            OptCourseCourseStatus.REJECTED.getId());
      }
    }
    if(!removeCourse.toString().equalsIgnoreCase("")) {
      mManager.updateCourseStatusForDependentCourse(pSemesterId, removeCourse.toString(),
          studentList, OptCourseCourseStatus.REJECTED.getId());
      mManager.deleteCourseAppliedByTeacher(pSemesterId, removeCourse.toString(), studentList);
      course = mCourseManager.get(removeCourse.toString());
      if(course.getPairCourseId() != null && !course.getPairCourseId().equalsIgnoreCase("")) {
        mManager.updateCourseStatusForDependentCourse(pSemesterId, course.getPairCourseId(),
            studentList, OptCourseCourseStatus.REJECTED.getId());
        mManager.deleteCourseAppliedByTeacher(pSemesterId, course.getPairCourseId(), studentList);
      }
    }
    return Response.noContent().build();
  }

  @Transactional
  public Response shiftStudents(int pSemesterId, String pSourceCourseId, String pTargetCourseId,
      final JsonObject pJsonObject) {
    // Todo: need to make sure if the source code has a pair course id then the target course must
    // have a pair course id.

    OptionalCourseApplicationBuilder builder = getBuilder();
    List<String> studentList = new ArrayList<>();
    builder.buildStudent(studentList, pJsonObject, "students");

    mManager.updateCourseStatus(pSemesterId, pSourceCourseId, studentList,
        OptCourseCourseStatus.REJECTED_SHIFTED.getId());
    mManager.insertShiftApplication(pSemesterId, pTargetCourseId, pSourceCourseId, studentList);

    Course course = mCourseManager.get(pSourceCourseId);
    if(course.getPairCourseId() != null && !course.getPairCourseId().equalsIgnoreCase("")) {
      Course targetCourse = mCourseManager.get(pTargetCourseId);
      if(targetCourse.getPairCourseId() != null
          && !targetCourse.getPairCourseId().equalsIgnoreCase("")) {
        mManager.updateCourseStatus(pSemesterId, course.getPairCourseId(), studentList,
            OptCourseCourseStatus.REJECTED_SHIFTED.getId());
        mManager.insertShiftApplication(pSemesterId, targetCourse.getPairCourseId(),
            course.getPairCourseId(), studentList);
      }
    }

    return Response.noContent().build();
  }

  public JsonObject getDataForStudent(Request pRequest, UriInfo mUriInfo) {

    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Semester mSemester =
        mSemesterManager.getSemesterByStatus(ProgramType.UG, SemesterStatus.NEWLY_CREATED);
    OptCourseApplicationStatus mApplicationStatus =
        mManager.getApplicationStatus(mStudentId, mSemester.getId());
    JsonObject mStudentCourses = getAppliedCoursesByStudent(mStudentId, mSemester.getId(), 110500);
    JsonObject mCallForApplicationCourses =
        mCourseResourceHelper.getCallForApplicationCourses(mSemester.getId(), 110500, 4, 1,
            mUriInfo);

    JsonObject object =
        Json.createObjectBuilder().add("application_status", mApplicationStatus.getLabel())
            .add("applied_courses", mStudentCourses.get("entries"))
            .add("callForApplication_courses", mCallForApplicationCourses.get("entries")).build();

    return object;
  }

  @Transactional
  public Response saveStudentApplication(Integer status, final JsonObject pJsonObject) {

    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Semester mSemester =
        mSemesterManager.getSemesterByStatus(ProgramType.UG, SemesterStatus.NEWLY_CREATED);

    // ToDo: Below validation need to be done
    // ToDo: Check the Application Date time. I mean if the student can save or submit it in this
    // time period?
    // TODo: Check whether the student's current year -semester can apply
    // ToDo: Check the student's submitted courses against the dept-program offered course list

    List<String> mCourseList = new ArrayList<>();
    getBuilder().buildCourseId(mCourseList, pJsonObject);
    mManager.deleteCoursesAppliedByStudent(mStudentId, mSemester.getId());
    mManager.saveStudentApplication(mStudentId, mSemester.getId(), mCourseList);
    mManager.updateStatus(mStudentId, mSemester.getId(),
        OptCourseApplicationStatus.values()[status]);

    return Response.noContent().build();
  }

}
