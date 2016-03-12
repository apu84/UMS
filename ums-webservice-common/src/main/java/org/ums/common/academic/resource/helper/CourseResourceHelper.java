package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentCourse;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Course;
import org.ums.domain.model.readOnly.Syllabus;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.manager.SyllabusManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class CourseResourceHelper extends ResourceHelper<Course, MutableCourse, String> {
  @Autowired
  @Qualifier("courseManager")
  private CourseManager mManager;

  @Autowired
  @Qualifier("semesterSyllabusMapManager")
  private SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @Autowired
  private List<Builder<Course, MutableCourse>> mBuilders;

  @Override
  public CourseManager getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<Course, MutableCourse>> getBuilders() {
    return mBuilders;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableCourse mutableCourse = new PersistentCourse();
    LocalCache localCache = new LocalCache();
    for (Builder<Course, MutableCourse> builder : mBuilders) {
      builder.build(mutableCourse, pJsonObject, localCache);
    }
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

  public JsonObject getOptionalCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester,  final Request pRequest, final UriInfo pUriInfo) throws Exception {
    Syllabus syllabus=mSemesterSyllabusMapManager.getSyllabusForSemester(pSemesterId,pProgramId,pYear,pSemester);
    List<Course> courses = getContentManager().getOptionalCourseList(syllabus.getId(), pYear,pSemester) ;

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

  public JsonObject getOfferedCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getOfferedCourseList(pSemesterId, pProgramId, pYear, pSemester);

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

  public JsonObject getCallForApplicationCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester);

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



  public JsonObject getApprovedCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester, final UriInfo pUriInfo) throws Exception {

    List<Course> courses = getContentManager().getApprovedCourseList(pSemesterId, pProgramId, pYear, pSemester);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Course course : courses) {
      children.add(toJson(course,pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }


}
