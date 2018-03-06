package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.ApplicationTESBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentApplicationTES;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
@Component
public class ApplicationTESResourceHelper extends ResourceHelper<ApplicationTES, MutableApplicationTES, Long> {

  @Autowired
  ApplicationTESManager mManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  ApplicationTESBuilder mBuilder;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public Response saveToTes(JsonObject pJsonObject, UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    List<MutableApplicationTES> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationTES application = new PersistentApplicationTES();
      getBuilder().build(application, jsonObject, localCache);
      application.setStudentId(studentId);
      application.setSemester(student.getCurrentEnrolledSemester().getId());
      applications.add(application);
    }
    mManager.create(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getAllQuestions(final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        List<ApplicationTES> applications=getContentManager().getAllQuestions(student.getCurrentEnrolledSemester().getId());
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getAssignedCourses(final String pFacultyId,final Request pRequest, final UriInfo pUriInfo){
         // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
        List<ApplicationTES> applications=getContentManager().getAssignedCourses(pFacultyId,11012017);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getAllFacultyMembers(final Request pRequest, final UriInfo pUriInfo){
        String userId = SecurityUtils.getSubject().getPrincipal().toString();
        User loggedUser = mUserManager.get(userId);
        Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
        String empDeptId=loggedEmployee.getDepartment().getId();
        List<ApplicationTES> applications=getContentManager().getFacultyMembers(empDeptId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getReviewEligibleCourses(final String pCourseType,final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        List<ApplicationTES> applications=getContentManager().
                getReviewEligibleCourses(studentId,student.getCurrentEnrolledSemester().getId(),pCourseType);
        String semesterName=getContentManager().getSemesterName(student.getCurrentEnrolledSemester().getId());
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a,pUriInfo,localCache)));
        object.add("entries", children);
        object.add("semesterName",semesterName);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getFacultyInfo(final String courseId,final String courseType,final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        String section = courseType.equals("Theory") ? student.getTheorySection() : student.getSessionalSection();
        List<ApplicationTES> facultyInfo=getContentManager().getTeachersInfo(courseId,student.getCurrentEnrolledSemester().getId(),section);
        facultyInfo.forEach( a-> children.add(toJson(a,pUriInfo,localCache)));
            object.add("entries",children);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getAlreadyReviewedCoursesInfo(final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        List<ApplicationTES> applications=getContentManager().getAlreadyReviewdCourses(studentId,student.getCurrentEnrolledSemester().getId());
        applications.forEach( a-> children.add(toJson(a,pUriInfo,localCache)));
        object.add("entries",children);
        localCache.invalidate();
        return object.build();
    }

  @Override
  protected ApplicationTESManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ApplicationTES, MutableApplicationTES> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ApplicationTES pReadonly) {
    return pReadonly.getApplicationDate();
  }
}
