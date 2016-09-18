package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.common.builder.CourseBuilder;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.persistent.model.PersistentCourse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseResourceHelper extends ResourceHelper<Course, MutableCourse, String> {
  @Autowired
  private CourseManager mManager;

  @Autowired
  private SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @Autowired
  private CourseBuilder mBuilder;

  @Override
  public CourseManager getContentManager() {
    return mManager;
  }

  @Override
  public CourseBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableCourse mutableCourse = new PersistentCourse();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableCourse, pJsonObject, localCache);
    mutableCourse.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(SemesterResource.class).path(SemesterResource.class, "get").build(mutableCourse.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getEtag(Course pReadonly) {
    return "";
  }

  public JsonObject buildCourses(final List<Course> pCourses, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Course readOnly : pCourses) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getBySyllabus(final String pSyllabusId, final Request pRequest, final UriInfo pUriInfo) throws Exception {
    List<Course> courses = getContentManager().getBySyllabus(pSyllabusId);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Course course : courses) {
      children.add(toJson(course, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getBySemesterProgram(final String pSemesterId,final String pProgramId, final Request pRequest, final UriInfo pUriInfo) throws Exception {
    List<Course> courses = getContentManager().getBySemesterProgram(pSemesterId,pProgramId);

    return convertToJson(courses,pUriInfo);
  }

  public JsonObject getCourses(final String pSemesterId, final String pProgramId, final int pProgramType, final Request pRequest, final UriInfo pUriInfo) throws Exception{
    List<Course> courses = getContentManager().
        getBySemesterProgram(pSemesterId,pProgramId)
        .stream()
        .filter(course->course.getCourseType().getValue()==pProgramType)
        .collect(Collectors.toList());

    return convertToJson(courses,pUriInfo);
  }

  private JsonObject convertToJson(List<Course> courses, final UriInfo pUriInfo) throws Exception{
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Course course : courses) {
      children.add(toJson(course, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getByYearSemester(final String pSemesterId, final String pProgramId, final int year,final int semester,final Request pRequest, final UriInfo pUriInfo)throws Exception{
    List<Course> courses = getContentManager().getByYearSemester(pSemesterId,pProgramId,year,semester);

    return convertToJson(courses,pUriInfo);
  }




  public JsonObject getOptionalCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester,  final Request pRequest, final UriInfo pUriInfo) throws Exception {
    Syllabus syllabus=mSemesterSyllabusMapManager.getSyllabusForSemester(pSemesterId,pProgramId,pYear,pSemester);
    List<Course> courses = getContentManager().getOptionalCourseList(syllabus.getId(), pYear,pSemester) ;

    return convertToJson(courses,pUriInfo);
  }

  public JsonObject getOfferedCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getOfferedCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return convertToJson(courses,pUriInfo);
  }

  public JsonObject getCallForApplicationCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return convertToJson(courses,pUriInfo);
  }



  public JsonObject getApprovedCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getApprovedCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return convertToJson(courses,pUriInfo);
  }

  public JsonObject getApprovedCallForApplicationCourseList(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getApprovedCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return convertToJson(courses,pUriInfo);
  }


}
