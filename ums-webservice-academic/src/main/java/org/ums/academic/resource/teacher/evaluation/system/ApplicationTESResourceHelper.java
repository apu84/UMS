package org.ums.academic.resource.teacher.evaluation.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Autowired
  ApplicationTESManager applicationTESManager;

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

  public Response saveAssignedCourses(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationTES application = new PersistentApplicationTES();
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    getContentManager().saveAssignedCourses(applications);
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

  public List<Report> getResult(final Request pRequest, final UriInfo pUriInfo){
      double cRoombservation=0,noncRoomObservation=0;
      Integer countercR=0,counterncR=0;
      DecimalFormat newFormat = new DecimalFormat("#.##");
      HashMap<Integer,Double> mapForCalculateResult=new HashMap<Integer,Double>();
      List<Report> reportList= new ArrayList<Report>();
      List<Report> reportListValue= new ArrayList<Report>();
      Integer studentNo=getContentManager().getTotalStudentNumber("","",11);
        List<ApplicationTES> getDetailedResult=getContentManager().getDetailedResult("","",11).
                stream().
                filter(a->a.getComment() !=null).collect(Collectors.toList());
        List<ApplicationTES> applications=getContentManager().getAllQuestions(11012017);

        applications.forEach(a->{
            double value=getContentManager().getAverageScore("","",a.getQuestionId(),11);
            String questionDetails=getContentManager().getQuestionDetails(a.getQuestionId());
            Integer observationType=getContentManager().getObservationType(a.getQuestionId());
            reportList.add(new Report(a.getQuestionId(),questionDetails,value,studentNo,(Double.valueOf(newFormat.format(value/studentNo))),observationType));
            mapForCalculateResult.put(a.getQuestionId(),(value/studentNo));
        });
      for(Map.Entry m:mapForCalculateResult.entrySet()){
          Integer questionId=(Integer)m.getKey();
          if(getContentManager().getObservationType(questionId) ==1){
              countercR++;
           cRoombservation=cRoombservation+(double)m.getValue();
          }else {
              counterncR++;
              noncRoomObservation=noncRoomObservation+(double)m.getValue();
          }
      }
      cRoombservation=(cRoombservation/countercR);
      noncRoomObservation=(noncRoomObservation/counterncR);
      return reportList;
    }

  public List<StudentComment> getComments(final Request pRequest, final UriInfo pUriInfo){
        List<ApplicationTES> applications=getContentManager().getAllQuestions(11012017);
      List<ApplicationTES> getDetailedResult=null;
      List<StudentComment> commentList= new ArrayList<StudentComment>();

        for(int i=0;i<applications.size();i++){
            Integer questionId=applications.get(i).getQuestionId();
            Integer observationType=getContentManager().getObservationType(questionId);
            String questionDetails=getContentManager().getQuestionDetails(questionId);
             getDetailedResult=getContentManager().getDetailedResult("","",11).
                    stream().
                    filter(a->a.getComment() !=null && a.getQuestionId()==questionId).collect(Collectors.toList());
            int size=getDetailedResult.size();

            if(getDetailedResult.size() !=0){
                String comments[] =new String[size];
                for(int j=0;j<size;j++){
                    comments[j]=getDetailedResult.get(j).getComment();
                }
                commentList.add(new StudentComment(questionId,comments,observationType,questionDetails));
            }
        }

        return commentList;
    }

  // getRecordsOfAssignedCoursesByHead
  public JsonObject getRecordsOfAssignedCoursesByHead(final Request pRequest, final UriInfo pUriInfo){
      String userId = SecurityUtils.getSubject().getPrincipal().toString();
      User loggedUser = mUserManager.get(userId);
      Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
      String empDeptId=loggedEmployee.getDepartment().getId();
        // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
        List<ApplicationTES> applications=getContentManager().getRecordsOfAssignedCoursesByHead(11012017,empDeptId);
        String semesterName=getContentManager().getSemesterName(11012017);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        object.add("semesterName",semesterName);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getAssignedCourses(final String pFacultyId,final Request pRequest, final UriInfo pUriInfo){
         // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
        List<MutableApplicationTES> applications=getContentManager().getAssignedCourses(pFacultyId,11012017);
        List<ApplicationTES> assignedCoursesByHead=getContentManager().getAssignedCoursesByHead(pFacultyId,11012017);
      Map<String, ApplicationTES> assignedCourseMap = assignedCoursesByHead
              .stream()
              .collect(Collectors.toMap(t->t.getTeacherId()+t.getReviewEligibleCourses()+t.getSection()+t.getSemester(), t->t));

        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a->{
            if(assignedCourseMap.containsKey(a.getTeacherId()+a.getReviewEligibleCourses()+a.getSection()+a.getSemester())){
                a.setStatus(1);
            }else{
                a.setStatus(0);
            }
                children.add(toJson(a, pUriInfo, localCache));
        });

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
        Integer getTotalRecords=getContentManager().getTotalRecords(empDeptId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
         object.add("totalRecords", getTotalRecords);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getReviewEligibleCourses(final String pCourseType,final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        List<MutableApplicationTES> applications=getContentManager().
                getReviewEligibleCourses(studentId,student.getCurrentEnrolledSemester().getId(),pCourseType,student.getTheorySection());
        String semesterName=getContentManager().getSemesterName(student.getCurrentEnrolledSemester().getId());
        List<ApplicationTES> alreadyReviewedCourses=getContentManager().getAlreadyReviewdCourses(studentId,student.getCurrentEnrolledSemester().getId());

        Map<String, ApplicationTES> reviewedCourseMap = alreadyReviewedCourses
              .stream()
              .collect(Collectors.toMap(t->t.getTeacherId()+t.getReviewEligibleCourses()+t.getStudentId()+t.getSemester(), t->t));


      JsonObjectBuilder object = Json.createObjectBuilder();
      JsonArrayBuilder children = Json.createArrayBuilder();
      LocalCache localCache = new LocalCache();

      applications.forEach(a-> {
            if(reviewedCourseMap.containsKey(a.getTeacherId() +a.getReviewEligibleCourses()+a.getStudentId()+a.getSemester())){
                a.setStatus(1);
            }else{
                a.setStatus(0);
            }
            children.add(toJson(a, pUriInfo, localCache));
        });
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

  public JsonObject getAlreadyReviewedCoursesInfo(final String pCourseId,final String pTeacherId,final Request pRequest, final UriInfo pUriInfo){
        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        List<ApplicationTES> applications=getContentManager().getRivewedCoursesForReadOnlyMode(pCourseId,pTeacherId,studentId,student.getCurrentEnrolledSemester().getId());
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
