package org.ums.academic.resource.teacher.evaluation.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.ComparisonReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentApplicationTES;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.UmsUtils;

import javax.json.*;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.*;
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

  @Autowired
  CourseManager mCourseManager;

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public Response saveToTes(JsonObject pJsonObject, UriInfo pUriInfo) {
    String startDate = "", endDate = "", sStartDate = "", sEndDate = "";
    Boolean deadLineStatus = false, studentSubmitDeadline = false;
    String semesterName = getContentManager().getSemesterName(11012017);
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines("12", 11012017);// 12=student
                                                                                                  // TES
                                                                                                  // mst_parameter_settings
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    try {
      if(startDate != null && endDate != null) {
        Date startDateConvert, lastApplyDate, currentDate;
        currentDate = new Date();
        startDateConvert = UmsUtils.convertToDate(startDate, "dd-MM-yyyy");
        lastApplyDate = UmsUtils.convertToDate(endDate, "dd-MM-yyyy");
        if(currentDate.compareTo(startDateConvert) >= 0 && currentDate.compareTo(lastApplyDate) <= 0) {
          deadLineStatus = true;
        }
        else {
          deadLineStatus = false;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    if(deadLineStatus) {
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
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response setQuestion(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationTES application = new PersistentApplicationTES();
      getBuilder().build(application, jsonObject, localCache);
      application.setSemester(mSemesterManager.getActiveSemester(11).getId());
      applications.add(application);
    }
    getContentManager().setQuestions(applications);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response addQuestion(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableApplicationTES applications = new PersistentApplicationTES();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationTES application = new PersistentApplicationTES();
      getBuilder().build(application, jsonObject, localCache);
      application.setSemester(mSemesterManager.getActiveSemester(11).getId());
      applications = application;
    }
    getContentManager().addQuestions(applications);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response saveAssignedCourses(JsonObject pJsonObject, UriInfo pUriInfo) {
    String startDate = "", endDate = "", sStartDate = "", sEndDate = "";
    Boolean deadLineStatus = false, studentSubmitDeadline = false;
    String semesterName = getContentManager().getSemesterName(11012017);
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines("11", 11012017);
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    try {
      if(startDate != null && endDate != null) {
        Date startDateConvert, lastApplyDate, currentDate;
        currentDate = new Date();
        startDateConvert = UmsUtils.convertToDate(startDate, "dd-MM-yyyy");
        lastApplyDate = UmsUtils.convertToDate(endDate, "dd-MM-yyyy");
        if(currentDate.compareTo(startDateConvert) >= 0 && currentDate.compareTo(lastApplyDate) <= 0) {
          deadLineStatus = true;
        }
        else {
          deadLineStatus = false;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    if(deadLineStatus) {
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
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public  JsonObject getAssignedReviewableCoursesList(final String pTeacherId,final  Integer pSemesterId,final Request pRequest, final UriInfo pUriInfo){
        List<ApplicationTES> applications=getContentManager().getAssignedReviewableCoursesList(pTeacherId,pSemesterId);
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public  JsonObject getQuestions(final Request pRequest, final UriInfo pUriInfo){
        List<MutableApplicationTES> applications=getContentManager().getQuestions();
        List<ApplicationTES> questionSemesterMap=getContentManager().getQuestionSemesterMap(mSemesterManager.getActiveSemester(11).getId());
        for(int i=0;i<applications.size();i++){
            Integer qId=applications.get(i).getQuestionId();
            Integer size=0;
            size=questionSemesterMap.stream().filter(a->a.getQuestionId()==qId).collect(Collectors.toList()).size();
                if(size==1){
                    applications.get(i).setStatus(1);
                }else{
                    applications.get(i).setStatus(0);
                }

        }
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public List<ComparisonReport> getComparisonResult(final String pDeptId, final Integer pSemesterId,
      final Request pRequest, final UriInfo pUriInfo) {
    List<ApplicationTES> applications = getContentManager().getFacultyListForReport(pDeptId, pSemesterId);
    List<ApplicationTES> parameters = null;
    List<ApplicationTES> getDeptList = getContentManager().getDeptList();
    List<ComparisonReport> report = new ArrayList<ComparisonReport>();
      List<ComparisonReport> reportMaxMin = new ArrayList<ComparisonReport>();
    for(int i = 0; i < applications.size(); i++) {
      parameters = getContentManager().getParametersForReport(applications.get(i).getTeacherId(), pSemesterId);
      for(int j = 0; j < parameters.size(); j++) {
        double score = 0;
        Integer studentNo =
            getContentManager().getTotalStudentNumber(parameters.get(j).getTeacherId(),
                parameters.get(j).getReviewEligibleCourses(), pSemesterId);
        List<ApplicationTES> app = getContentManager().getAllQuestions(pSemesterId);
        if(studentNo != 0) {
          score =
              getScore(parameters.get(j).getTeacherId(), parameters.get(j).getReviewEligibleCourses(), pSemesterId,
                  studentNo, app);
        }
        String teacherName, deptName, courseNo, courseTitle, programName = "";
        Integer registeredStudents=0;double percentage=0;
        teacherName = mPersonalInformationManager.get(parameters.get(j).getTeacherId()).getFullName();
        deptName = mEmployeeManager.get(parameters.get(j).getTeacherId()).getDepartment().getShortName();
        courseNo = mCourseManager.get(parameters.get(j).getReviewEligibleCourses()).getNo();
        courseTitle = mCourseManager.get(parameters.get(j).getReviewEligibleCourses()).getTitle();
        try {
          programName = getContentManager().getCourseDepartmentMap(parameters.get(j).getReviewEligibleCourses(), pSemesterId);
          registeredStudents=getContentManager().getTotalRegisteredStudentForCourse(parameters.get(j).getReviewEligibleCourses(), pSemesterId);
          int total=studentNo*registeredStudents;
          percentage= (double)total/100;
        } catch(Exception e) {
          e.printStackTrace();
        }
        if(programName.equals(null) || programName.equals("")) {
          programName = "Not Found";
        }
        report.add(new ComparisonReport(teacherName, deptName, courseNo, courseTitle, score, percentage, programName,
            parameters.get(j).getTeacherId(), parameters.get(j).getReviewEligibleCourses(), parameters.get(j)
                .getDeptId()));
      }

    }

    if(pDeptId.equals("09")){
        for(int k=0;k<getDeptList.size();k++){
            double max=-1;
            String dp=getDeptList.get(k).getDeptId();
            List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
            for(int l=0;l<list.size();l++){
                if(list.get(l).getDeptId().equals(getDeptList.get(k).getDeptId())){
                    if(list.get(l).getTotalScore()> max){
                        max=list.get(l).getTotalScore();
                    }

                }
            }
            double finalMax = max;
            list=list.stream().filter(a->a.getTotalScore()== finalMax).collect(Collectors.toList());
            for(int l=0;l<list.size();l++){
                reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(),
                        list.get(l).getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore()==-1? 0:max,
                        list.get(l).getReviewPercentage(), list.get(l).getProgramName(),
                        list.get(l).getTeacherId(), list.get(l).getCourseId(), list.get(l).getDeptId()));
            }

        }
        return  reportMaxMin;
    }else if(pDeptId.equals("10")){
        for(int k=0;k<getDeptList.size();k++){
            double min=10;
            String dp=getDeptList.get(k).getDeptId();
            List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
            for(int l=0;l<list.size();l++){
                if(list.get(l).getDeptId().equals(getDeptList.get(k).getDeptId())){
                    if(list.get(l).getTotalScore()< min){
                        min=list.get(l).getTotalScore();
                    }

                }
            }
            double finalMin = min;
            list=list.stream().filter(a->a.getTotalScore()== finalMin).collect(Collectors.toList());
            for(int l=0;l<list.size();l++){
                reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(),
                        list.get(l).getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore()==10? 0:min,
                        list.get(l).getReviewPercentage(), list.get(l).getProgramName(),
                        list.get(l).getTeacherId(), list.get(l).getCourseId(), list.get(l).getDeptId()));
            }

        }
        return  reportMaxMin;
    }else{
        return report;
    }
  }

  public double getScore(String pTeacherId,String pCourseId,Integer pSemesterId,Integer studentNo,List<ApplicationTES> applications){
      double cRoombservation=0,noncRoomObservation=0,score=0;
      Integer countercR=0,counterncR=0;
      DecimalFormat newFormat = new DecimalFormat("#.##");
      HashMap<Integer,Double> mapForCalculateResult=new HashMap<Integer,Double>();
      if(studentNo !=0){
          applications.forEach(a->{
              Integer observationType=getContentManager().getObservationType(a.getQuestionId());
              if(observationType!=3){
                  double value=0;
                  value=getContentManager().getAverageScore(pTeacherId,pCourseId,a.getQuestionId(),pSemesterId);
                  mapForCalculateResult.put(a.getQuestionId(),(value/studentNo));
              }

          });
          for(Map.Entry m:mapForCalculateResult.entrySet()){
              Integer questionId=(Integer)m.getKey();
              if(getContentManager().getObservationType(questionId) ==1){
                  countercR++;
                  cRoombservation=cRoombservation+(double)m.getValue();
              }else if(getContentManager().getObservationType(questionId) ==2){
                  counterncR++;
                  noncRoomObservation=noncRoomObservation+(double)m.getValue();
              }else{

              }
          }
          cRoombservation=Double.valueOf(newFormat.format((cRoombservation/countercR)));
          noncRoomObservation=Double.valueOf(newFormat.format((noncRoomObservation/counterncR)));
          score=Double.valueOf(newFormat.format((cRoombservation+noncRoomObservation)/2));
      }
      return  score;
  }

  public JsonObject getAllSemesterNameList(final Request pRequest, final UriInfo pUriInfo){
        List<ApplicationTES> applications=getContentManager().getAllSemesterNameList();
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  public JsonObject getAllQuestions(final Request pRequest, final UriInfo pUriInfo){
      String startDate="",endDate="",sStartDate="",sEndDate="";
      Boolean deadLineStatus = false,startingDeadline=false;
      String semesterName=getContentManager().getSemesterName(11012017);
      List<ApplicationTES> semesterParameterHead=getContentManager().getDeadlines("12",11012017);//12=student TES mst_parameter_settings
      for(int i=0;i<semesterParameterHead.size();i++){
          startDate=semesterParameterHead.get(i).getSemesterStartDate();
          endDate=semesterParameterHead.get(i).getSemesterEndDate();
      }

      try{
          if(startDate != null && endDate != null) {
              Date startDateConvert, lastApplyDate, currentDate;
              currentDate = new Date();
              startDateConvert = UmsUtils.convertToDate(startDate, "dd-MM-yyyy");
              lastApplyDate = UmsUtils.convertToDate(endDate, "dd-MM-yyyy");
              if (currentDate.compareTo(startDateConvert) >= 0 && currentDate.compareTo(lastApplyDate) <= 0) {
                  deadLineStatus=true;
              }else{
                  deadLineStatus=false;
              }
              if (currentDate.compareTo(startDateConvert) >= 0){
                  startingDeadline=true;
              }else{
                  startingDeadline=false;
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }

        String studentId = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = mStudentManager.get(studentId);
        List<ApplicationTES> applications=getContentManager().getAllQuestions(student.getCurrentEnrolledSemester().getId());
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        object.add("startDate",startDate);
        object.add("endDate",endDate);
        object.add("deadLine",deadLineStatus);
        object.add("startingDeadline",startingDeadline);
        localCache.invalidate();
        return object.build();
    }

  public List<Report> getResult(final String pCourseId,final String pTeacherId,final  Integer pSemesterId,final Request pRequest, final UriInfo pUriInfo){
      double cRoombservation=0,noncRoomObservation=0,score=0;
      Integer countercR=0,counterncR=0;
      DecimalFormat newFormat = new DecimalFormat("#.##");
      HashMap<Integer,Double> mapForCalculateResult=new HashMap<Integer,Double>();
      List<Report> reportList= new ArrayList<Report>();
      Integer studentNo=getContentManager().getTotalStudentNumber(pTeacherId,pCourseId,pSemesterId);
        List<ApplicationTES> applications=getContentManager().getAllQuestions(pSemesterId);
        if(studentNo !=0){
            applications.forEach(a->{
                Integer observationType=getContentManager().getObservationType(a.getQuestionId());
                if(observationType!=3){
                    double value=0;

                    value=getContentManager().getAverageScore(pTeacherId,pCourseId,a.getQuestionId(),pSemesterId);

                    String questionDetails=getContentManager().getQuestionDetails(a.getQuestionId());
                    reportList.add(new Report(a.getQuestionId(),questionDetails,value,studentNo,(Double.valueOf(newFormat.format(value/studentNo))),observationType));
                    mapForCalculateResult.put(a.getQuestionId(),(value/studentNo));
                }

            });
            for(Map.Entry m:mapForCalculateResult.entrySet()){
                Integer questionId=(Integer)m.getKey();
                if(getContentManager().getObservationType(questionId) ==1){
                    countercR++;
                    cRoombservation=cRoombservation+(double)m.getValue();
                }else if(getContentManager().getObservationType(questionId) ==2){
                    counterncR++;
                    noncRoomObservation=noncRoomObservation+(double)m.getValue();
                }else{

                }
            }
            cRoombservation=Double.valueOf(newFormat.format((cRoombservation/countercR)));
            noncRoomObservation=Double.valueOf(newFormat.format((noncRoomObservation/counterncR)));
            score=Double.valueOf(newFormat.format((cRoombservation+noncRoomObservation)/2));
        }

      return reportList;
    }

  public List<StudentComment> getComments(final String pCourseId,final String pTeacherId,final  Integer pSemesterId,final Request pRequest, final UriInfo pUriInfo){
        List<ApplicationTES> applications=getContentManager().getAllQuestions(pSemesterId);
      List<ApplicationTES> getDetailedResult=null;
      List<StudentComment> commentList= new ArrayList<StudentComment>();

        for(int i=0;i<applications.size();i++){
            Integer questionId=applications.get(i).getQuestionId();
            Integer observationType=getContentManager().getObservationType(questionId);
            if(observationType ==3){
                String questionDetails=getContentManager().getQuestionDetails(questionId);
                getDetailedResult=getContentManager().getDetailedResult(pTeacherId,pCourseId,pSemesterId).
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
      String startDate="",endDate="",sStartDate="",sEndDate="";
       Boolean deadLineStatus = false,studentSubmitDeadline=false;
      String semesterName=getContentManager().getSemesterName(11012017);
      List<ApplicationTES> semesterParameterHead=getContentManager().getDeadlines("11",11012017);
     for(int i=0;i<semesterParameterHead.size();i++){
         startDate=semesterParameterHead.get(i).getSemesterStartDate();
         endDate=semesterParameterHead.get(i).getSemesterEndDate();
     }

      try{
          if(startDate != null && endDate != null) {
              Date startDateConvert, lastApplyDate, currentDate;
              currentDate = new Date();
              startDateConvert = UmsUtils.convertToDate(startDate, "dd-MM-yyyy");
              lastApplyDate = UmsUtils.convertToDate(endDate, "dd-MM-yyyy");
              if (currentDate.compareTo(startDateConvert) >= 0 && currentDate.compareTo(lastApplyDate) <= 0) {
                  deadLineStatus=true;
              }else{
                  deadLineStatus=false;
              }
          }
      }catch (Exception e){
      e.printStackTrace();
      }

      List<MutableApplicationTES> applications=getContentManager().getAssignedCourses(pFacultyId,11012017);
      List<ApplicationTES> assignedCoursesByHead=getContentManager().getAssignedCoursesByHead(pFacultyId,11012017);
      Map<String, ApplicationTES> assignedCourseMap = assignedCoursesByHead
              .stream()
              .collect(Collectors.toMap(t->t.getTeacherId()+t.getReviewEligibleCourses()+t.getSection()+t.getSemester(), t->t));

        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
      Boolean finalDeadLineStatus = deadLineStatus;
      applications.forEach(a->{
            if(assignedCourseMap.containsKey(a.getTeacherId()+a.getReviewEligibleCourses()+a.getSection()+a.getSemester())){
                a.setStatus(1);
            }else{
                a.setStatus(0);
            }
            String programName=getContentManager().getCourseDepartmentMap(a.getReviewEligibleCourses(),11012017);//active semester
            a.setProgramShortName(programName);
            a.setDeadLineStatus(finalDeadLineStatus);
                children.add(toJson(a, pUriInfo, localCache));
        });

        object.add("entries", children);
        object.add("startDate",startDate);
        object.add("endDate",endDate);
        object.add("deadLine",deadLineStatus);
        object.add("semesterName",semesterName);
        localCache.invalidate();
        return object.build();

    }

  public JsonObject getStudentSubmitDateInfo(final Request pRequest, final UriInfo pUriInfo) {
    // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
    String sStartDate = "", sEndDate = "";
    Integer semesterId = 0;
    Boolean studentSubmitDeadline = false;
    List<ApplicationTES> semesterParameterStudent = getContentManager().getDeadlines("12", 11012017);
    for(int j = 0; j < semesterParameterStudent.size(); j++) {
      semesterId = semesterParameterStudent.get(j).getSemester();
      sStartDate = semesterParameterStudent.get(j).getSemesterStartDate();
      sEndDate = semesterParameterStudent.get(j).getSemesterEndDate();
    }
    try {
      if(sStartDate != null && sEndDate != null) {
        Date studentLastApplyDate, currentDate;
        currentDate = new Date();
        studentLastApplyDate = UmsUtils.convertToDate(sEndDate, "dd-MM-yyyy");
        if(currentDate.compareTo(studentLastApplyDate) >= 0) {
          studentSubmitDeadline = true;
        }
        else {
          studentSubmitDeadline = true;// false
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("studentSubmitDeadLine", studentSubmitDeadline);
    object.add("endDate", sEndDate);
    object.add("currentSemesterId", semesterId);
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

  public JsonObject getEligibleFacultyMembers(final Integer pSemesterId,final String pDeptId,final Request pRequest, final UriInfo pUriInfo){
      String empDeptId = null;
      if(pDeptId.equals("none") || pDeptId.equals(null)){
          String userId = SecurityUtils.getSubject().getPrincipal().toString();
          User loggedUser = mUserManager.get(userId);
          Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
          empDeptId=loggedEmployee.getDepartment().getId();
      }else{
          empDeptId=pDeptId;
      }

        // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
        List<ApplicationTES> applications=getContentManager().getEligibleFacultyMembers(empDeptId,pSemesterId);
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
