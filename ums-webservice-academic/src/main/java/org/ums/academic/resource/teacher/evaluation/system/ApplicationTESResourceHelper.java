package org.ums.academic.resource.teacher.evaluation.system;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.apache.commons.collections.ListUtils;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.ComparisonReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.QuestionWiseReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.FacultyType;
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
import java.util.stream.Stream;

/*
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
@Component
public class ApplicationTESResourceHelper extends ResourceHelper<ApplicationTES, MutableApplicationTES, Long> {
    private static final Logger mLogger = LoggerFactory.getLogger(ApplicationTESResourceHelper.class);

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

  @Autowired
  ProgramManager mPogramManager;

  ApplicationTesQuestionManager mApplicationTesQuestionManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public Response saveToTes(JsonObject pJsonObject, UriInfo pUriInfo) {
    Boolean deadLineStatus = getSemesterParameter(mSemesterManager.getActiveSemester(11).getId(), "12");
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
        application.setSemester(mSemesterManager.getActiveSemester(11).getId());
        applications.add(application);
      }
      mManager.create(applications);
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @NotNull
  private Boolean getSemesterParameter(int i2, String s) {
    String startDate = "", endDate = "", sStartDate = "", sEndDate = "";
    Boolean deadLineStatus = false, studentSubmitDeadline = false;
    String semesterName = getContentManager().getSemesterName(i2);
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines(s, i2);// 12=student
    // TES
    // mst_parameter_settings
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);
    return deadLineStatus;
  }

  private Boolean checkDateValidity(String startDate, String endDate, Boolean deadLineStatus) {
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
    return deadLineStatus;
  }

  public Response setQuestion(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    deleteData(applications, entries);
    boolean deadline = getSemesterInfo();
    if(deadline) {
      getContentManager().setQuestions(applications);
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private void deleteData(List<MutableApplicationTES> applications, JsonArray entries) {
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationTES application = new PersistentApplicationTES();
      getBuilder().build(application, jsonObject, localCache);
      application.setSemester(mSemesterManager.getActiveSemester(11).getId());
      applications.add(application);
    }
  }

  public Response deleteQuestion(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    delete(applications, entries);
    boolean deadline = getSemesterInfo();
    if(deadline) {
      mManager.delete(applications);
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private void delete(List<MutableApplicationTES> applications, JsonArray entries) {
    deleteData(applications, entries);
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
    String semesterName = getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines("11", mSemesterManager.getActiveSemester(11).getId());
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);

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

  public  JsonObject getMigrationQuestions(final Integer pSemesterId,final Request pRequest, final UriInfo pUriInfo){
        List<MutableApplicationTES> applications=getContentManager().getMigrationQuestions(pSemesterId);
        List<ApplicationTES> questionSemesterMap=getContentManager().getQuestionSemesterMap(mSemesterManager.getActiveSemester(11).getId());
    checkMigrationValidity(applications, questionSemesterMap);
    JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
        applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
        object.add("entries", children);
        localCache.invalidate();
        return object.build();
    }

  private void checkMigrationValidity(List<MutableApplicationTES> applications, List<ApplicationTES> questionSemesterMap) {
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
  }

  private boolean getSemesterInfo() {
    String startDate = "", endDate = "", sStartDate = "", sEndDate = "";
    Boolean deadLineStatus = false, studentSubmitDeadline = false;
    String semesterName = getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
    List<ApplicationTES> semesterParameterHead =
        getContentManager().getDeadlines("13", mSemesterManager.getActiveSemester(11).getId());
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);
    return deadLineStatus;
  }

  public JsonObject getInitialSemesterParameter(final Request pRequest, final UriInfo pUriInfo) {
    String startDate = "", endDate = "", sStartDate = "", sEndDate = "";
    Boolean deadLineStatus = false, studentSubmitDeadline = false;
    String semesterName = getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
    List<ApplicationTES> semesterParameterHead =
        getContentManager().getDeadlines("13", mSemesterManager.getActiveSemester(11).getId());
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("startDate", startDate);
    object.add("endDate", endDate);
    object.add("deadLine", deadLineStatus);
    object.add("semesterName", semesterName);
    localCache.invalidate();
    return object.build();
  }

  public  JsonObject getDeleteEligibleQuestions(final Request pRequest, final UriInfo pUriInfo){
        List<MutableApplicationTES> applications=getContentManager().getMigrationQuestions(mSemesterManager.getActiveSemester(11).getId());
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
      checkMigrationValidity(applications, questionSemesterMap);
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
    final String maximum = "09", minimum = "10", engineering = "11", businessAndSocial = "12", architecture = "13";
    Integer facultyId = getFacultyType(pDeptId, engineering, businessAndSocial, architecture);
    List<ApplicationTES> teacherList = getContentManager().getFacultyListForReport(pDeptId, pSemesterId);
    List<ApplicationTES> teacherRelatedCourseList = null;
    DecimalFormat newFormat = new DecimalFormat("#.##");
    List<ApplicationTES> getDeptList = getContentManager().getDeptList();
    List<ComparisonReport> report = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportMaxMin = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportFaculty = new ArrayList<ComparisonReport>();
    for(int i = 0; i < teacherList.size(); i++) {
      teacherRelatedCourseList =
          getContentManager().getParametersForReport(teacherList.get(i).getTeacherId(), pSemesterId);
      evaluateTeacherInfo(pSemesterId, teacherRelatedCourseList, newFormat, report);
    }

    if(pDeptId.equals(maximum)) {
      return getMaximumScoreHolderList(getDeptList, report, reportMaxMin);
    }
    else if(pDeptId.equals(minimum)) {
      return getMinimumScoreHolderList(getDeptList, report, reportMaxMin);
    }
    else if(pDeptId.equals(engineering) || pDeptId.equals(businessAndSocial) || pDeptId.equals(architecture)) {
      return getFacultyReport(facultyId, report, reportFaculty);
    }
    else {
      return report;
    }
  }

  private List<ComparisonReport> getFacultyReport(Integer facultyId, List<ComparisonReport> report,
      List<ComparisonReport> reportFaculty) {
    List<ApplicationTES> facultyWiseDeptList = getContentManager().getDeptListByFacultyId(facultyId);
    List<ComparisonReport> tempList = null;
    reportFaculty = getComparisonReportFacultyMembers(report, reportFaculty, facultyWiseDeptList);
    return reportFaculty;
  }

  private List<ComparisonReport> getComparisonReportFacultyMembers(List<ComparisonReport> report, List<ComparisonReport> reportFaculty, List<ApplicationTES> facultyWiseDeptList) {
    List<ComparisonReport> tempList;
    for(int i = 0; i<facultyWiseDeptList.size(); i++){
        String departmentId=facultyWiseDeptList.get(i).getDeptId();
         tempList=report.stream().filter(a->a.getDeptId().equals(departmentId)).collect(Collectors.toList());
        reportFaculty= ListUtils.union(reportFaculty,tempList);
        tempList= Collections.emptyList();
    }
    return reportFaculty;
  }

  private void evaluateTeacherInfo(Integer pSemesterId, List<ApplicationTES> teacherRelatedCourseList,
      DecimalFormat newFormat, List<ComparisonReport> report) {
    for(int j = 0; j < teacherRelatedCourseList.size(); j++) {
      double score = 0;
      Integer studentNo =
          getContentManager().getTotalStudentNumber(teacherRelatedCourseList.get(j).getTeacherId(),
              teacherRelatedCourseList.get(j).getReviewEligibleCourseId(), pSemesterId);
      List<ApplicationTES> app = getContentManager().getAllQuestions(pSemesterId);
      if(studentNo != 0) {
        score =
            getScore(teacherRelatedCourseList.get(j).getTeacherId(), teacherRelatedCourseList.get(j)
                .getReviewEligibleCourseId(), pSemesterId, studentNo, app);
      }
      String teacherName, deptName, courseNo, courseTitle, programName = "";
      Integer registeredStudents = 0;
      double percentage = 0;
      teacherName = mPersonalInformationManager.get(teacherRelatedCourseList.get(j).getTeacherId()).getFullName();
      deptName = mEmployeeManager.get(teacherRelatedCourseList.get(j).getTeacherId()).getDepartment().getShortName();
      courseNo = mCourseManager.get(teacherRelatedCourseList.get(j).getReviewEligibleCourseId()).getNo();
      courseTitle = mCourseManager.get(teacherRelatedCourseList.get(j).getReviewEligibleCourseId()).getTitle();
      List<ApplicationTES> sectionList =
          getContentManager().getSectionList(teacherRelatedCourseList.get(j).getReviewEligibleCourseId(), pSemesterId,
              teacherRelatedCourseList.get(j).getTeacherId());
      try {
        programName =
            getContentManager().getCourseDepartmentMap(teacherRelatedCourseList.get(j).getReviewEligibleCourseId(),
                pSemesterId);
      } catch(Exception e) {
        e.printStackTrace();
      }
      percentage =
          getPercentage(pSemesterId, teacherRelatedCourseList, newFormat, j, studentNo, registeredStudents, sectionList);

      if(programName.equals(null) || programName.equals("")) {
        programName = "Not Found";
      }
      report.add(new ComparisonReport(teacherName, deptName, courseNo, courseTitle, score, percentage, programName,
          teacherRelatedCourseList.get(j).getTeacherId(), teacherRelatedCourseList.get(j).getReviewEligibleCourseId(),
          teacherRelatedCourseList.get(j).getDeptId()));
    }
  }

  private List<ComparisonReport> getMinimumScoreHolderList(List<ApplicationTES> getDeptList, List<ComparisonReport> report, List<ComparisonReport> reportMaxMin) {
        for(int k=0;k<getDeptList.size();k++){
            double min=10;
            String dp=getDeptList.get(k).getDeptId();
            List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
            min = getMinimumScore(getDeptList, k, min, list);
            double finalMin = min;
            list=list.stream().filter(a->a.getTotalScore()== finalMin).collect(Collectors.toList());
            getMinimumScoreHolderInfo(reportMaxMin, min, list);

        }
        return  reportMaxMin;
    }

  private void getMinimumScoreHolderInfo(List<ComparisonReport> reportMaxMin, double min, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(), list.get(l)
          .getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore() == 10 ? 0 : min, list.get(l)
          .getReviewPercentage(), list.get(l).getProgramName(), list.get(l).getTeacherId(), list.get(l).getCourseId(),
          list.get(l).getDeptId()));
    }
  }

  private double getMinimumScore(List<ApplicationTES> getDeptList, int deptSequence, double min,
      List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      if(list.get(l).getDeptId().equals(getDeptList.get(deptSequence).getDeptId())) {
        if(list.get(l).getTotalScore() < min) {
          min = list.get(l).getTotalScore();
        }

      }
    }
    return min;
  }

  private List<ComparisonReport> getMaximumScoreHolderList(List<ApplicationTES> getDeptList, List<ComparisonReport> report, List<ComparisonReport> reportMaxMin) {
        for(int k=0;k<getDeptList.size();k++){
            double max=-1;
            String dp=getDeptList.get(k).getDeptId();
            List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
            max = getMaximumScore(getDeptList, k, max, list);
            double finalMax = max;
            list=list.stream().filter(a->a.getTotalScore()== finalMax).collect(Collectors.toList());
            getMaximumScoreHolderInfo(reportMaxMin, max, list);

        }
        return  reportMaxMin;
    }

  private void getMaximumScoreHolderInfo(List<ComparisonReport> reportMaxMin, double max, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(), list.get(l)
          .getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore() == -1 ? 0 : max, list.get(l)
          .getReviewPercentage(), list.get(l).getProgramName(), list.get(l).getTeacherId(), list.get(l).getCourseId(),
          list.get(l).getDeptId()));
    }
  }

  private double getMaximumScore(List<ApplicationTES> getDeptList, int k, double max, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      if(list.get(l).getDeptId().equals(getDeptList.get(k).getDeptId())) {
        if(list.get(l).getTotalScore() > max) {
          max = list.get(l).getTotalScore();
        }

      }
    }
    return max;
  }

  private double getPercentage(Integer pSemesterId, List<ApplicationTES> parameters, DecimalFormat newFormat, int j,
      double studentNo, Integer registeredStudents, List<ApplicationTES> sectionList) {
    double percentage = 0;
    try {
      for(int k = 0; k < sectionList.size(); k++) {
        registeredStudents =
            registeredStudents
                + getContentManager().getTotalRegisteredStudentForCourse(parameters.get(j).getReviewEligibleCourseId(),
                    sectionList.get(k).getSection(), pSemesterId);
      }
      double total = (studentNo / (double) registeredStudents);
      percentage = Double.valueOf(newFormat.format((total * 100)));
    } catch(Exception e) {

    }

    return percentage;
  }

  public Integer getFacultyType(String pDeptId, String engineering, String businessAndSocial, String architecture) {
    Integer facultyId = 0;
    if(pDeptId.equals(engineering)) {
      facultyId = FacultyType.Engineering.getId();
    }
    else if(pDeptId.equals(businessAndSocial)) {
      facultyId = FacultyType.Business.getId();
    }
    else if(pDeptId.equals(architecture)) {
      facultyId = FacultyType.Architecture.getId();
    }
    return facultyId;
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

  public JsonObject getSemesterWiseQuestions(final Integer pSemesterId,final Request pRequest, final UriInfo pUriInfo){
    List<ApplicationTES> applications=getContentManager().getAllQuestions(pSemesterId);
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
      String semesterName=getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
      List<ApplicationTES> semesterParameterHead=getContentManager().getDeadlines("12",mSemesterManager.getActiveSemester(11).getId());//12=student TES mst_parameter_settings
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
        List<ApplicationTES> applications=getContentManager().getAllQuestions(mSemesterManager.getActiveSemester(11).getId());//mSemesterManager.getActiveSemester(11).getId()
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

  public JsonObject getReviewPercentage(final String pCourseId, final String pTeacherId, final Integer pSemesterId,
      final Request pRequest, final UriInfo pUriInfo) {
    DecimalFormat newFormat = new DecimalFormat("#.##");
    Integer studentNo = getContentManager().getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);

    String selectedSectionForReview = "", sectionForReview = "";
    Integer selectedRegisteredStudents = 0, registeredStudents = 0;
    double percentage = 0;
    List<ApplicationTES> getAllSectionForSelectedCourse =
        getContentManager().getAllSectionForSelectedCourse(pCourseId, pTeacherId, pSemesterId);
    List<ApplicationTES> sectionList = getContentManager().getSectionList(pCourseId, pSemesterId, pTeacherId);
    try {
      for(int j = 0; j < getAllSectionForSelectedCourse.size(); j++) {
        sectionForReview = sectionForReview + getAllSectionForSelectedCourse.get(j).getSection() + " ";
        registeredStudents =
            registeredStudents
                + getContentManager().getTotalRegisteredStudentForCourse(pCourseId,
                    getAllSectionForSelectedCourse.get(j).getSection(), pSemesterId);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    try {
      for(int k = 0; k < sectionList.size(); k++) {
        selectedSectionForReview = selectedSectionForReview + sectionList.get(k).getSection() + " ";
        selectedRegisteredStudents =
            selectedRegisteredStudents
                + getContentManager().getTotalRegisteredStudentForCourse(pCourseId, sectionList.get(k).getSection(),
                    pSemesterId);
      }
      double total = ((double) studentNo / (double) selectedRegisteredStudents);
      percentage = Double.valueOf(newFormat.format((total * 100)));
    } catch(Exception e) {
      e.printStackTrace();
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("sectionForReview", sectionForReview);
    object.add("registeredStudents", registeredStudents);
    object.add("selectedSectionForReview", selectedSectionForReview);
    object.add("selectedRegisteredStudents", selectedRegisteredStudents);
    object.add("percentage", percentage);
    object.add("studentReviewed", studentNo);
    localCache.invalidate();
    return object.build();
  }

  public List<QuestionWiseReport> getQuestionWiseReport(final String pDeptId, final Integer pYear,
      final Integer pSemester, final Integer pSemesterId, final Integer pQuestionId, final Request pRequest,
      final UriInfo pUriInfo) {

    DecimalFormat newFormat = new DecimalFormat("#.##");
    List<QuestionWiseReport> reportList =
        getQuestionWiseReports(pDeptId, pYear, pSemester, pSemesterId, pQuestionId, newFormat);
    return reportList;
  }

  @NotNull
  public List<QuestionWiseReport> getQuestionWiseReports(String pDeptId, Integer pYear, Integer pSemester,
      Integer pSemesterId, Integer pQuestionId, DecimalFormat newFormat) {
    List<QuestionWiseReport> reportList = new ArrayList<QuestionWiseReport>();
    List<ApplicationTES> getCourses =
        getContentManager().getCourseForQuestionWiseReport(pDeptId, pYear, pSemester, pSemesterId);
    List<ApplicationTES> getTeachers = null;

    for(int i = 0; i < getCourses.size(); i++) {
      getTeachers =
          getContentManager().getTeacherListForQuestionWiseReport(getCourses.get(i).getReviewEligibleCourseId(),
              pSemesterId);
      for(int j = 0; j < getTeachers.size(); j++) {
        Integer studentNo =
            getContentManager().getTotalStudentNumber(getTeachers.get(j).getTeacherId(),
                getCourses.get(i).getReviewEligibleCourseId(), pSemesterId);
        String teacherName = "", courseNo = "", courseTitle = "", programName = "";
        double value = 0;
        double percentage = 0;
        teacherName = mPersonalInformationManager.get(getTeachers.get(j).getTeacherId()).getFullName();
        courseNo = mCourseManager.get(getCourses.get(i).getReviewEligibleCourseId()).getNo();
        courseTitle = mCourseManager.get(getCourses.get(i).getReviewEligibleCourseId()).getTitle();
        if(studentNo != 0) {
          value =
              getContentManager().getAverageScore(getTeachers.get(j).getTeacherId(),
                  getCourses.get(i).getReviewEligibleCourseId(), pQuestionId, pSemesterId);

          value = (Double.valueOf(newFormat.format(value / studentNo)));

          List<ApplicationTES> sectionList =
              getContentManager().getSectionList(getCourses.get(i).getReviewEligibleCourseId(), pSemesterId,
                  getTeachers.get(j).getTeacherId());
          int selectedRegisteredStudents = 0;
          try {
            for(int k = 0; k < sectionList.size(); k++) {
              selectedRegisteredStudents =
                  selectedRegisteredStudents
                      + getContentManager().getTotalRegisteredStudentForCourse(
                          getCourses.get(i).getReviewEligibleCourseId(), sectionList.get(k).getSection(), pSemesterId);
            }
            double total = ((double) studentNo / (double) selectedRegisteredStudents);
            percentage = Double.valueOf(newFormat.format((total * 100)));
          } catch(Exception e) {
            e.printStackTrace();
          }

        }
        try {
          programName =
              getContentManager().getCourseDepartmentMap(getCourses.get(i).getReviewEligibleCourseId(), pSemesterId);
        } catch(Exception e) {
          e.printStackTrace();
        }
        if(programName.equals(null) || programName.equals("")) {
          programName = "Not Found";
        }
        reportList.add(new QuestionWiseReport(teacherName, courseNo, courseTitle, programName, value, percentage));

      }
    }
    return reportList;
  }

  public List<Report> getResult(final String pCourseId, final String pTeacherId, final Integer pSemesterId,
      final Request pRequest, final UriInfo pUriInfo) {
    DecimalFormat newFormat = new DecimalFormat("#.##");
    HashMap<Integer, Double> mapForCalculateResult = new HashMap<Integer, Double>();
    List<Report> reportList = new ArrayList<Report>();
    Integer studentNo = getContentManager().getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);
    List<ApplicationTES> applications = getContentManager().getAllQuestions(pSemesterId);
    getEvaluationDetails(pCourseId, pTeacherId, pSemesterId, newFormat, reportList, studentNo, applications);
    return reportList;
  }

  private void getEvaluationDetails(String pCourseId, String pTeacherId, Integer pSemesterId, DecimalFormat newFormat, List<Report> reportList, Integer studentNo, List<ApplicationTES> applications) {
    if(studentNo !=0){
        applications.forEach(a->{
            Integer observationType=getContentManager().getObservationType(a.getQuestionId());
            if(observationType!=3){
                double value=0;
                value=getContentManager().getAverageScore(pTeacherId,pCourseId,a.getQuestionId(),pSemesterId);
                String questionDetails=getContentManager().getQuestionDetails(a.getQuestionId());
                reportList.add(new Report(a.getQuestionId(),questionDetails,value,studentNo,(Double.valueOf(newFormat.format(value/studentNo))),observationType));
            }
        });
    }
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

              setCommentToList(getDetailedResult, commentList, questionId, observationType, questionDetails, size);
            }
            }
      return commentList;
    }

  public void setCommentToList(List<ApplicationTES> getDetailedResult, List<StudentComment> commentList,
      Integer questionId, Integer observationType, String questionDetails, int size) {
    if(getDetailedResult.size() != 0) {
      String comments[] = new String[size];
      for(int j = 0; j < size; j++) {
        comments[j] = getDetailedResult.get(j).getComment();
      }
      commentList.add(new StudentComment(questionId, comments, observationType, questionDetails));
    }
  }

  // getRecordsOfAssignedCoursesByHead
  public JsonObject getRecordsOfAssignedCoursesByHead(final Request pRequest, final UriInfo pUriInfo){
      String userId = SecurityUtils.getSubject().getPrincipal().toString();
      User loggedUser = mUserManager.get(userId);
      Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
      String empDeptId=loggedEmployee.getDepartment().getId();
        // Integer sem= mSemesterManager.getActiveSemester(11).getId();11022017
        List<ApplicationTES> applications=getContentManager().getRecordsOfAssignedCoursesByHead(mSemesterManager.getActiveSemester(11).getId(),empDeptId);
        String semesterName=getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
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
      String semesterName=getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
      List<ApplicationTES> semesterParameterHead=getContentManager().getDeadlines("11",mSemesterManager.getActiveSemester(11).getId());
     for(int i=0;i<semesterParameterHead.size();i++){
         startDate=semesterParameterHead.get(i).getSemesterStartDate();
         endDate=semesterParameterHead.get(i).getSemesterEndDate();
     }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);

    List<MutableApplicationTES> applications=getContentManager().getAssignedCourses(pFacultyId,mSemesterManager.getActiveSemester(11).getId());
      List<ApplicationTES> assignedCoursesByHead=getContentManager().getAssignedCoursesByHead(pFacultyId,mSemesterManager.getActiveSemester(11).getId());
      Map<String, ApplicationTES> assignedCourseMap = assignedCoursesByHead
              .stream()
              .collect(Collectors.toMap(t->t.getTeacherId()+t.getReviewEligibleCourseId()+t.getSection()+t.getSemester(), t->t));

        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder children = Json.createArrayBuilder();
        LocalCache localCache = new LocalCache();
      Boolean finalDeadLineStatus = deadLineStatus;
      applications.forEach(a->{
            if(assignedCourseMap.containsKey(a.getTeacherId()+a.getReviewEligibleCourseId()+a.getSection()+a.getSemester())){
                a.setStatus(1);
            }else{
                a.setStatus(0);
            }
            String programName=getContentManager().getCourseDepartmentMap(a.getReviewEligibleCourseId(),mSemesterManager.getActiveSemester(11).getId());//active semester
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
    List<ApplicationTES> semesterParameterStudent = getContentManager().getDeadlines("12", mSemesterManager.getActiveSemester(11).getId());
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
          studentSubmitDeadline = false;// -->false
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
                getReviewEligibleCourses(studentId,mSemesterManager.getActiveSemester(11).getId(),pCourseType,student.getTheorySection());
        String semesterName=getContentManager().getSemesterName(mSemesterManager.getActiveSemester(11).getId());
        List<ApplicationTES> alreadyReviewedCourses=getContentManager().getAlreadyReviewdCourses(studentId,mSemesterManager.getActiveSemester(11).getId());

        Map<String, ApplicationTES> reviewedCourseMap = alreadyReviewedCourses
              .stream()
              .collect(Collectors.toMap(t->t.getTeacherId()+t.getReviewEligibleCourseId()+t.getStudentId()+t.getSemester(), t->t));


      JsonObjectBuilder object = Json.createObjectBuilder();
      JsonArrayBuilder children = Json.createArrayBuilder();
      LocalCache localCache = new LocalCache();

      applications.forEach(a-> {
            if(reviewedCourseMap.containsKey(a.getTeacherId() +a.getReviewEligibleCourseId()+a.getStudentId()+a.getSemester())){
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
        List<ApplicationTES> facultyInfo=getContentManager().getTeachersInfo(courseId,mSemesterManager.getActiveSemester(11).getId(),section);
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
        List<ApplicationTES> applications=getContentManager().getRivewedCoursesForReadOnlyMode(pCourseId,pTeacherId,studentId,mSemesterManager.getActiveSemester(11).getId());
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
