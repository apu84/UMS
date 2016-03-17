package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.common.academic.resource.StudentResource;
import org.ums.domain.model.dto.MutableSemesterSyllabusMapDto;
import org.ums.domain.model.dto.OptSectionDto;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.OptCourseApplicationStatus;
import org.ums.manager.StudentManager;
import org.ums.persistent.dao.PersistentOptionalCourseApplicationDao;
import org.ums.common.builder.OptionalCourseApplicationBuilder;
import org.ums.domain.model.dto.OptCourseStudentDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.immutable.Course;
import org.ums.persistent.dao.PersistentStudentDao;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class OptionalCourseApplicationResourceHelper {

  @Autowired
  private PersistentOptionalCourseApplicationDao mManager;


  @Autowired
  StudentManager mStudentManager;

  @Autowired
  private OptionalCourseApplicationBuilder mBuilder;


  public PersistentOptionalCourseApplicationDao getContentManager() {
    return mManager;
  }

  public OptionalCourseApplicationBuilder getBuilder() {
    return mBuilder;
  }


  public JsonObject getApplicationStatistics(final Integer pSemesterId,final Integer pProgramId) throws Exception {
    List<OptionalCourseApplicationStatDto> statList= mManager.getApplicationStatistics(pSemesterId, pProgramId,1,1);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (OptionalCourseApplicationStatDto stat : statList) {
      children.add(stat.toString());
    }
    object.add("entries", children);
    return object.build();
  }


  public Response saveApprovedAndApplicationCourses(final Integer pSemesterId, final Integer pProgramId, int pYear, int pSemester, final JsonObject pJsonObject) throws Exception {
    OptionalCourseApplicationBuilder builder = getBuilder();
    List<Course> approvedCourseList = new ArrayList<>();
    builder.build(approvedCourseList, pJsonObject, "approved");

    List<Course> callForApplicationCourseList = new ArrayList<>();
    builder.build(callForApplicationCourseList, pJsonObject, "callForApplication");


    mManager.deleteApplicationCourses(pSemesterId, pProgramId, pYear, pSemester);
    mManager.insertApplicationCourses(pSemesterId, pProgramId, pYear, pSemester, callForApplicationCourseList);
    mManager.insertApprovedCourses(pSemesterId, pProgramId, pYear, pSemester, approvedCourseList);

    return Response.noContent().build();
  }

  public JsonObject getStudentList(int pSemesterId, String pCourseId, String pStatus) throws Exception {
    List<OptCourseStudentDto> studentList = getContentManager().getStudentList(pSemesterId, pCourseId, pStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (OptCourseStudentDto student : studentList) {
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public JsonObject getNonAssignedSectionStudentList(int pSemesterId, int pProgramId, String pCourseId) throws Exception {
    List<OptCourseStudentDto> studentList = getContentManager().getNonAssignedSectionStudentList(pSemesterId, pProgramId, pCourseId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (OptCourseStudentDto student : studentList) {
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public JsonObject getOptionalSectionListWithStudents(int pSemesterId, int pProgramId,String pCourseId) throws Exception {
    List<OptSectionDto> studentList = getContentManager().getOptionalSectionListWithStudents(pSemesterId, pProgramId, pCourseId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (OptSectionDto student : studentList) {
      student.setStudentList(mStudentManager.getStudentListFromStudentsString(student.toString()));
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

  public Response deleteSection(int pSemesterId, int pProgramId,String pCourseId,String pSectionName) throws Exception {
    mManager.deleteSection(pSemesterId,pProgramId,pCourseId,pSectionName);
    return Response.noContent().build();
  }

  public Response mergeSelection(int pSemesterId, int pProgramId,String pCourseId,String pSectionName,JsonObject pStudents) throws Exception {
    mManager.mergeSection(pSemesterId, pProgramId, pCourseId, pSectionName, "160105004,160105005,160105006");
    //URI contextURI = pUriInfo.getBaseUriBuilder().path(StudentResource.class).path(StudentResource.class, "get").build(mutableStudent.getId());
    URI contextURI=new URI("") ;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }
  public JsonObject getApplicationStatus(int pStudentId,int pSemesterId) throws Exception {
    Integer status= getContentManager().getApplicationStatus(pStudentId,pSemesterId);
    JsonObject object =Json.createObjectBuilder()
        .add("status_id", status)
        .add("status_name", OptCourseApplicationStatus.values()[status].getLabel())
        .build();

    return object;
  }

  public JsonObject getAppliedCoursesByStudent(int pStudentId,int pSemesterId,int pProgramId) throws Exception {
    //Todo: Need to verify that the user requested for the resource is in the same department under the requested program Id.
    List<OptCourseStudentDto> courseList = getContentManager().getAppliedCoursesByStudent(pStudentId, pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (OptCourseStudentDto course : courseList) {
      jsonReader = Json.createReader(new StringReader(course.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }




}
