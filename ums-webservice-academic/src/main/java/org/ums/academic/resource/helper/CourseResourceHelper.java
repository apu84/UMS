package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.SemesterResource;
import org.ums.builder.CourseBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentCourse;
import org.ums.resource.ResourceHelper;

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
  private UserManager mUserManager;

  @Autowired
  private SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private ProgramManager mProgramManager;

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
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableCourse mutableCourse = new PersistentCourse();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableCourse, pJsonObject, localCache);
    mutableCourse.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class)
            .path(SemesterResource.class, "get").build(mutableCourse.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getETag(Course pReadonly) {
    return pReadonly.getLastModified();
  }

  /*
   * public JsonObject buildCourses(final List<Course> pCourses, final UriInfo pUriInfo) throws
   * Exception { JsonObjectBuilder object = Json.createObjectBuilder(); JsonArrayBuilder children =
   * Json.createArrayBuilder(); LocalCache localCache = new LocalCache(); for (Course readOnly :
   * pCourses) { children.add(toJson(readOnly, pUriInfo, localCache)); } object.add("entries",
   * children); localCache.invalidate(); return object.build(); }
   */

  public JsonObject getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pOrder, final UriInfo pUriInfo) {
    List<Course> courses = getContentManager().getAllForPagination(pItemPerPage, pPage, pOrder);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Course course : courses) {
      children.add(toJson(course, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getBySyllabus(final String pSyllabusId, final Request pRequest,
      final UriInfo pUriInfo) {
    List<Course> courses = getContentManager().getBySyllabus(pSyllabusId);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Course course : courses) {
      children.add(toJson(course, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getBySemesterProgram(final String pSemesterId, final String pProgramId,
      final Request pRequest, final UriInfo pUriInfo) {
    List<Course> courses = getContentManager().getBySemesterProgram(pSemesterId, pProgramId);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getCourses(final Integer pSemesterId, final int pProgramType, final Request pRequest, final UriInfo pUriInfo){
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.get(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> program = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram ->pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());
    List<Course> courses = getContentManager().
        getBySemesterProgram(pSemesterId.toString(),program.get(0).getId().toString())
        .stream()
        .filter(course->course.getCourseType().getId()==pProgramType)
        .collect(Collectors.toList());

    return buildCourse(courses,pUriInfo);
  }

  public JsonObject getCourses(final Integer pSemesterId, final int pYear, final int pSemester, final Request pRequest, final UriInfo pUriInfo){
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.get(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> program = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram ->pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());
    List<Course> courses2 = getContentManager().getBySemesterProgram(pSemesterId.toString(),program.get(0).getId().toString());
    List<Course> courses = getContentManager().
        getBySemesterProgram(pSemesterId.toString(),program.get(0).getId().toString())
        .stream()
        .filter(course-> course.getYear()==pYear && course.getSemester()==pSemester)
        .collect(Collectors.toList());

    return buildCourse(courses,pUriInfo);
  }

  public JsonObject getCoursesForTeacher(final Request pRequest, final UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    List<Course> courses = getContentManager().getByTeacher(employeeId);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getByYearSemester(final String pSemesterId, final String pProgramId,
      final int year, final int semester, final Request pRequest, final UriInfo pUriInfo) {
    List<Course> courses =
        getContentManager().getByYearSemester(pSemesterId, pProgramId, year, semester);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getOptionalCourses(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester, final Request pRequest, final UriInfo pUriInfo) {
    Syllabus syllabus =
        mSemesterSyllabusMapManager.getSyllabusForSemester(pSemesterId, pProgramId, pYear,
            pSemester);
    List<Course> courses =
        getContentManager().getOptionalCourseList(syllabus.getId(), pYear, pSemester);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getOfferedCourses(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) {

    List<Course> courses =
        getContentManager().getOfferedCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getCallForApplicationCourses(final Integer pSemesterId,
      final Integer pProgramId, final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) {

    List<Course> courses =
        getContentManager().getCallForApplicationCourseList(pSemesterId, pProgramId, pYear,
            pSemester);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getApprovedCourses(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) {

    List<Course> courses =
        getContentManager().getApprovedCourseList(pSemesterId, pProgramId, pYear, pSemester);

    return buildCourse(courses, pUriInfo);
  }

  public JsonObject getApprovedCallForApplicationCourseList(final Integer pSemesterId,
      final Integer pProgramId, final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) {

    List<Course> courses =
        getContentManager().getApprovedCallForApplicationCourseList(pSemesterId, pProgramId, pYear,
            pSemester);

    return buildCourse(courses, pUriInfo);
  }

  private JsonObject buildCourse(List<Course> courses, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Course course : courses) {
      children.add(toJson(course, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

}
