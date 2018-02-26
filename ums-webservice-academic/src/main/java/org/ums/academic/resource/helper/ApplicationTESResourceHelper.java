package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.ApplicationTESBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.generator.IdGenerator;
import org.ums.manager.ApplicationTESManager;
import org.ums.manager.ContentManager;
import org.ums.manager.StudentManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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

  public JsonObject getReviewEligibleCourses(final String pCourseType,final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        List<ApplicationTES> applications=getContentManager().getReviewEligibleCourses(studentId,student.getCurrentEnrolledSemester().getId(),pCourseType);
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
