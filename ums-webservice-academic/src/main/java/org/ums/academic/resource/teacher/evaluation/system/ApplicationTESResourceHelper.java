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
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.FacultyType;
import org.ums.enums.ProgramType;
import org.ums.enums.mstParameter.ParameterType;
import org.ums.enums.tes.ObservationType;
import org.ums.enums.tes.TesStatus;
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
  ProgramManager mProgramManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  DesignationManager mDesignationManager;

  @Autowired
  ApplicationTesQuestionManager mApplicationTesQuestionManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public Response saveToTes(JsonObject pJsonObject, UriInfo pUriInfo) {
    Boolean deadLineStatus =
        getSemesterParameter(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId(),
            ParameterType.TES_STUDENT_EVALUATION_TIME_PERIOD.getValue());
    if(deadLineStatus) {
      String studentId = SecurityUtils.getSubject().getPrincipal().toString();
      List<MutableApplicationTES> applications = new ArrayList<>();
      JsonArray entries = pJsonObject.getJsonArray("entries");
      for(int i = 0; i < entries.size(); i++) {
        LocalCache localCache = new LocalCache();
        JsonObject jsonObject = entries.getJsonObject(i);
        PersistentApplicationTES application = new PersistentApplicationTES();
        getBuilder().build(application, jsonObject, localCache);
        application.setStudentId(studentId);
        application.setSemester(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false;
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
      application.setSemester(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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
      application.setSemester(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
      applications = application;
    }
    getContentManager().addQuestions(applications);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response saveAssignedCourses(JsonObject pJsonObject, UriInfo pUriInfo) {
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false;
    List<ApplicationTES> semesterParameterHead =
        getContentManager().getDeadlines(ParameterType.TES_HEAD_COURSE_ASSIGN_DATE.getValue(),
            mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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

  public JsonObject getAssignedReviewableCoursesList(final String pTeacherId, final Integer pSemesterId, final Request pRequest, final UriInfo pUriInfo) {
    List<ApplicationTES> applications = getContentManager().getAssignedReviewableCoursesList(pTeacherId, pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getMigrationQuestions(final Integer pSemesterId, final Request pRequest, final UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = getContentManager().getMigrationQuestions(pSemesterId);
    List<ApplicationTES> questionSemesterMap = getContentManager().getQuestionSemesterMap(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    checkMigrationValidity(applications, questionSemesterMap);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private void checkMigrationValidity(List<MutableApplicationTES> applications, List<ApplicationTES> questionSemesterMap) {
    for (int i = 0; i < applications.size(); i++) {
      Long qId = applications.get(i).getQuestionId();
      Integer size = 0;
      size = questionSemesterMap.stream().filter(a -> a.getQuestionId().equals(qId)).collect(Collectors.toList()).size();
      if (size == 1) {
        applications.get(i).setStatus(1);
      } else {
        applications.get(i).setStatus(0);
      }

    }
  }

  private boolean getSemesterInfo() {
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false;
    List<ApplicationTES> semesterParameterHead =
        getContentManager().getDeadlines(ParameterType.TES_QUESTION_SET_DATE.getValue(),
            mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    for(int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);
    return deadLineStatus;
  }

  public JsonObject getInitialSemesterParameter(final Request pRequest, final UriInfo pUriInfo) {
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false;
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    String semesterName = mSemesterManager.get(semesterId).getName();
    List<ApplicationTES> semesterParameterHead =
        getContentManager().getDeadlines(ParameterType.TES_QUESTION_SET_DATE.getValue(),
            mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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

  public JsonObject getDeleteEligibleQuestions(final Request pRequest, final UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = getContentManager().getMigrationQuestions(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getQuestions(final Request pRequest, final UriInfo pUriInfo) {
    List<MutableApplicationTES> applications = getContentManager().getQuestions();
    List<ApplicationTES> questionSemesterMap = getContentManager().getQuestionSemesterMap(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    checkMigrationValidity(applications, questionSemesterMap);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public List<ComparisonReport> getComparisonResult(final String pDeptId, final Integer pSemesterId,
      final Request pRequest, final UriInfo pUriInfo) {
    Integer facultyId =
        getFacultyType(pDeptId, TesStatus.FACULTY_OF_ENGINEERING.getValue(),
            TesStatus.FACULTY_OF_BUSINESS_AND_SOCIAL_SCIENCE.getValue(), TesStatus.FACULTY_OF_ARCHITECTURE.getValue());
    List<ApplicationTES> teacherList = getContentManager().getFacultyListForReport(pDeptId, pSemesterId);
    List<ApplicationTES> teacherRelatedCourseList = null;
    DecimalFormat newFormat = new DecimalFormat("#.##");
    List<Department> getDeptList = mDepartmentManager.getAll();

    List<ComparisonReport> report = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportMaxMin = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportFaculty = new ArrayList<ComparisonReport>();
    for(int i = 0; i < teacherList.size(); i++) {
      teacherRelatedCourseList =
          getContentManager().getParametersForReport(teacherList.get(i).getTeacherId(), pSemesterId);
      evaluateTeacherInfo(pSemesterId, teacherRelatedCourseList, newFormat, report);
    }

    if(pDeptId.equals(TesStatus.MAXIMUM_SCORE_HOLDER.getValue())) {
      return getMaximumScoreHolderList(getDeptList, report, reportMaxMin);
    }
    else if(pDeptId.equals(TesStatus.MINIMUM_SCORE_HOLDER.getValue())) {
      return getMinimumScoreHolderList(getDeptList, report, reportMaxMin);
    }
    else if(pDeptId.equals(TesStatus.FACULTY_OF_ENGINEERING.getValue())
        || pDeptId.equals(TesStatus.FACULTY_OF_BUSINESS_AND_SOCIAL_SCIENCE.getValue())
        || pDeptId.equals(TesStatus.FACULTY_OF_ARCHITECTURE.getValue())) {
      return getFacultyReport(facultyId, report, reportFaculty);
    }
    else {
      return report;
    }
  }

  private List<ComparisonReport> getFacultyReport(Integer facultyId, List<ComparisonReport> report,
      List<ComparisonReport> reportFaculty) {
    List<ApplicationTES> facultyWiseDeptList = getContentManager().getDeptListByFacultyId(facultyId);
    reportFaculty = getComparisonReportFacultyMembers(report, reportFaculty, facultyWiseDeptList);
    return reportFaculty;
  }

  private List<ComparisonReport> getComparisonReportFacultyMembers(List<ComparisonReport> report,
      List<ComparisonReport> reportFaculty, List<ApplicationTES> facultyWiseDeptList) {
    reportFaculty = mergeList(report, reportFaculty, facultyWiseDeptList);
    return reportFaculty;
  }

  public List<ComparisonReport> mergeList(List<ComparisonReport> report, List<ComparisonReport> reportFaculty, List<ApplicationTES> facultyWiseDeptList) {
    List<ComparisonReport> tempList;
    for (int i = 0; i < facultyWiseDeptList.size(); i++) {
      String departmentId = facultyWiseDeptList.get(i).getDeptId();
      tempList = report.stream().filter(a -> a.getDeptId().equals(departmentId)).collect(Collectors.toList());
      reportFaculty = ListUtils.union(reportFaculty, tempList);
      tempList = Collections.emptyList();
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

  private List<ComparisonReport> getMinimumScoreHolderList(List<Department> getDeptList, List<ComparisonReport> report, List<ComparisonReport> reportMaxMin) {
    for (int k = 0; k < getDeptList.size(); k++) {
      double INITIAL_MINIMUM_VALUE = 10;//
      String dp = getDeptList.get(k).getId();
      List<ComparisonReport> list = report.stream().filter(a -> a.getDeptId().equals(dp)).collect(Collectors.toList());
      INITIAL_MINIMUM_VALUE = getMinimumScore(getDeptList, k, INITIAL_MINIMUM_VALUE, list);
      double finalMin = INITIAL_MINIMUM_VALUE;
      list = list.stream().filter(a -> a.getTotalScore() == finalMin).collect(Collectors.toList());
      getMinimumScoreHolderInfo(reportMaxMin, INITIAL_MINIMUM_VALUE, list);

    }
    return reportMaxMin;
  }

  private void getMinimumScoreHolderInfo(List<ComparisonReport> reportMaxMin, double min, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(), list.get(l)
          .getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore() == 10 ? 0 : min, list.get(l)
          .getReviewPercentage(), list.get(l).getProgramName(), list.get(l).getTeacherId(), list.get(l).getCourseId(),
          list.get(l).getDeptId()));
    }
  }

  private double getMinimumScore(List<Department> getDeptList, int deptSequence, double min, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      if(list.get(l).getDeptId().equals(getDeptList.get(deptSequence).getId())) {
        if(list.get(l).getTotalScore() < min) {
          min = list.get(l).getTotalScore();
        }

      }
    }
    return min;
  }

  private List<ComparisonReport> getMaximumScoreHolderList(List<Department> getDeptList, List<ComparisonReport> report, List<ComparisonReport> reportMaxMin) {
    for (int k = 0; k < getDeptList.size(); k++) {
      double INITIAL_MAXIMUM_VALUE = -1;
      String dp = getDeptList.get(k).getId();
      List<ComparisonReport> list = report.stream().filter(a -> a.getDeptId().equals(dp)).collect(Collectors.toList());
      INITIAL_MAXIMUM_VALUE = getMaximumScore(getDeptList, k, INITIAL_MAXIMUM_VALUE, list);
      double FINAL_MAX_VALUE = INITIAL_MAXIMUM_VALUE;
      list = list.stream().filter(a -> a.getTotalScore() == FINAL_MAX_VALUE).collect(Collectors.toList());
      getMaximumScoreHolderInfo(reportMaxMin, INITIAL_MAXIMUM_VALUE, list);

    }
    return reportMaxMin;
  }

  private void getMaximumScoreHolderInfo(List<ComparisonReport> reportMaxMin, double max, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(), list.get(l)
          .getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore() == -1 ? 0 : max, list.get(l)
          .getReviewPercentage(), list.get(l).getProgramName(), list.get(l).getTeacherId(), list.get(l).getCourseId(),
          list.get(l).getDeptId()));
    }
  }

  private double getMaximumScore(List<Department> getDeptList, int k, double max, List<ComparisonReport> list) {
    for(int l = 0; l < list.size(); l++) {
      if(list.get(l).getDeptId().equals(getDeptList.get(k).getId())) {
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
      e.printStackTrace();
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

  public double getScore(String pTeacherId, String pCourseId, Integer pSemesterId, Integer studentNo, List<ApplicationTES> applications) {
    double classRoomObservation = 0, nonClassRoomObservation = 0, score = 0;
    Integer counterForClassRoomObservation = 0, counterForNonClassRoomObservation = 0;
    DecimalFormat newFormat = new DecimalFormat("#.##");
    HashMap<Long, Double> mapForCalculateResult = new HashMap<Long, Double>();
    if (studentNo != 0) {
      applications.forEach(a -> {
        Integer observationType = mApplicationTesQuestionManager.get(a.getQuestionId()).getObservationType();
        if (observationType != ObservationType.NON_TEACHING_OBSERVATION.getValue()) {
          double value = 0;
          value = getContentManager().getAverageScore(pTeacherId, pCourseId, a.getQuestionId(), pSemesterId);
          mapForCalculateResult.put(a.getQuestionId(), (value / studentNo));
        }
      });
      for(Map.Entry m:mapForCalculateResult.entrySet()){
        Long questionId=(Long)m.getKey();
        if(mApplicationTesQuestionManager.get(questionId).getObservationType() == ObservationType.CLASSROOM_OBSERVATION.getValue()){
          counterForClassRoomObservation++;
          classRoomObservation=classRoomObservation+(double)m.getValue();
        }else if(mApplicationTesQuestionManager.get(questionId).getObservationType() ==ObservationType.NON_CLASSROOM_OBSERVATION.getValue()){
          counterForNonClassRoomObservation++;
          nonClassRoomObservation=nonClassRoomObservation+(double)m.getValue();
        }else{
           //ObservationType.NON_TEACHING_OBSERVATION.getValue()
          //duplicate code will be removed
        }
      }
      classRoomObservation = Double.valueOf(newFormat.format((classRoomObservation / counterForClassRoomObservation)));
      nonClassRoomObservation = Double.valueOf(newFormat.format((nonClassRoomObservation / counterForNonClassRoomObservation)));
      score = Double.valueOf(newFormat.format((classRoomObservation + nonClassRoomObservation) / 2));
    }
    return score;
  }

  public JsonObject getSemesterWiseQuestions(final Integer pSemesterId, final Request pRequest, final UriInfo pUriInfo) {
    List<ApplicationTES> applications = getContentManager().getAllQuestions(pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getAllQuestions(final Request pRequest, final UriInfo pUriInfo) {
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false, startingDeadline = false;
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines(ParameterType.TES_STUDENT_EVALUATION_TIME_PERIOD.getValue(), mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());//12=student TES mst_parameter_settings
    for (int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    try {
      if (startDate != null && endDate != null) {
        Date startDateConvert, lastApplyDate, currentDate;
        currentDate = new Date();
        startDateConvert = UmsUtils.convertToDate(startDate, "dd-MM-yyyy");
        lastApplyDate = UmsUtils.convertToDate(endDate, "dd-MM-yyyy");
        if (currentDate.compareTo(startDateConvert) >= 0 && currentDate.compareTo(lastApplyDate) <= 0) {
          deadLineStatus = true;
        } else {
          deadLineStatus = false;
        }
        if (currentDate.compareTo(startDateConvert) >= 0) {
          startingDeadline = true;
        } else {
          startingDeadline = false;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<ApplicationTES> applications = getContentManager().getAllQuestions(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());//mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId()
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    object.add("startDate", startDate);
    object.add("endDate", endDate);
    object.add("deadLine", deadLineStatus);
    object.add("startingDeadline", startingDeadline);
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
      final Integer pSemester, final Integer pSemesterId, final Long pQuestionId, final Request pRequest,
      final UriInfo pUriInfo) {

    DecimalFormat newFormat = new DecimalFormat("#.##");
    List<QuestionWiseReport> reportList =
        getQuestionWiseReports(pDeptId, pYear, pSemester, pSemesterId, pQuestionId, newFormat);
    return reportList;
  }

  @NotNull
  public List<QuestionWiseReport> getQuestionWiseReports(String pDeptId, Integer pYear, Integer pSemester,
      Integer pSemesterId, Long pQuestionId, DecimalFormat newFormat) {
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
    List<Report> reportList = new ArrayList<Report>();
    Integer studentNo = getContentManager().getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);
    List<ApplicationTES> applications = getContentManager().getAllQuestions(pSemesterId);
    getEvaluationDetails(pCourseId, pTeacherId, pSemesterId, newFormat, reportList, studentNo, applications);
    return reportList;
  }

  private void getEvaluationDetails(String pCourseId, String pTeacherId, Integer pSemesterId, DecimalFormat newFormat, List<Report> reportList, Integer studentNo, List<ApplicationTES> applications) {
    if (studentNo != 0) {
      applications.forEach(a -> {
        Integer observationType = mApplicationTesQuestionManager.get(a.getQuestionId()).getObservationType();
        if (observationType != ObservationType.NON_TEACHING_OBSERVATION.getValue()) {
          double value = 0;
          value = getContentManager().getAverageScore(pTeacherId, pCourseId, a.getQuestionId(), pSemesterId);
          String questionDetails = mApplicationTesQuestionManager.get(a.getQuestionId()).getQuestionDetails();
          reportList.add(new Report(a.getQuestionId(), questionDetails, value, studentNo, (Double.valueOf(newFormat.format(value / studentNo))), observationType));
        }
      });
    }
  }

  public List<StudentComment> getComments(final String pCourseId, final String pTeacherId, final Integer pSemesterId, final Request pRequest, final UriInfo pUriInfo) {
    List<ApplicationTES> applications = getContentManager().getAllQuestions(pSemesterId);
    List<ApplicationTES> getDetailedResult = null;
    List<StudentComment> commentList = new ArrayList<StudentComment>();

    for (int i = 0; i < applications.size(); i++) {
      Long questionId = applications.get(i).getQuestionId();
      Integer observationType = mApplicationTesQuestionManager.get(questionId).getObservationType();
      if (observationType == ObservationType.NON_TEACHING_OBSERVATION.getValue()) {
        String questionDetails = mApplicationTesQuestionManager.get(questionId).getQuestionDetails();
        getDetailedResult = getContentManager().getDetailedResult(pTeacherId, pCourseId, pSemesterId).
                stream().
                filter(a -> a.getComment() != null && a.getQuestionId().equals(questionId)).collect(Collectors.toList());
        int size = getDetailedResult.size();

        setCommentToList(getDetailedResult, commentList, questionId, observationType, questionDetails, size);
      }
    }
    return commentList;
  }

  public void setCommentToList(List<ApplicationTES> getDetailedResult, List<StudentComment> commentList,
      Long questionId, Integer observationType, String questionDetails, int size) {
    if(getDetailedResult.size() != 0) {
      String comments[] = new String[size];
      for(int j = 0; j < size; j++) {
        comments[j] = getDetailedResult.get(j).getComment();
      }
      commentList.add(new StudentComment(questionId, comments, observationType, questionDetails));
    }
  }

  // getRecordsOfAssignedCoursesByHead
  public JsonObject getRecordsOfAssignedCoursesByHead(final Request pRequest, final UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User loggedUser = mUserManager.get(userId);
    Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
    String empDeptId = loggedEmployee.getDepartment().getId();
    List<ApplicationTES> applications = getContentManager().getRecordsOfAssignedCoursesByHead(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId(), empDeptId);
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    String semesterName = mSemesterManager.get(semesterId).getName();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    object.add("semesterName", semesterName);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getAssignedCourses(final String pFacultyId, final Request pRequest, final UriInfo pUriInfo) {
    String startDate = "", endDate = "";
    Boolean deadLineStatus = false;
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    String semesterName = mSemesterManager.get(semesterId).getName();
    List<ApplicationTES> semesterParameterHead = getContentManager().getDeadlines(ParameterType.TES_HEAD_COURSE_ASSIGN_DATE.getValue(), mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    for (int i = 0; i < semesterParameterHead.size(); i++) {
      startDate = semesterParameterHead.get(i).getSemesterStartDate();
      endDate = semesterParameterHead.get(i).getSemesterEndDate();
    }

    deadLineStatus = checkDateValidity(startDate, endDate, deadLineStatus);

    List<MutableApplicationTES> applications = getContentManager().getAssignedCourses(pFacultyId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    List<ApplicationTES> assignedCoursesByHead = getContentManager().getAssignedCoursesByHead(pFacultyId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    Map<String, ApplicationTES> assignedCourseMap = assignedCoursesByHead
            .stream()
            .collect(Collectors.toMap(t -> t.getTeacherId() + t.getReviewEligibleCourseId() + t.getSection() + t.getSemester(), t -> t));

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    Boolean finalDeadLineStatus = deadLineStatus;
    applications.forEach(a -> {
      if (assignedCourseMap.containsKey(a.getTeacherId() + a.getReviewEligibleCourseId() + a.getSection() + a.getSemester())) {
        a.setStatus(1);
      } else {
        a.setStatus(0);
      }
      String programName = getContentManager().getCourseDepartmentMap(a.getReviewEligibleCourseId(), mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());//active semester
      a.setProgramShortName(programName);
      a.setDeadLineStatus(finalDeadLineStatus);
      children.add(toJson(a, pUriInfo, localCache));
    });

    object.add("entries", children);
    object.add("startDate", startDate);
    object.add("endDate", endDate);
    object.add("deadLine", deadLineStatus);
    object.add("semesterName", semesterName);
    localCache.invalidate();
    return object.build();

  }

  public JsonObject getStudentSubmitDateInfo(final Request pRequest, final UriInfo pUriInfo) {
    String sStartDate = "", sEndDate = "";
    Integer semesterId = 0;
    Boolean studentSubmitDeadline = false;
    List<ApplicationTES> semesterParameterStudent =
        getContentManager().getDeadlines(ParameterType.TES_STUDENT_EVALUATION_TIME_PERIOD.getValue(),
            mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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
          studentSubmitDeadline = false;
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

  public JsonObject getAllFacultyMembers(final Request pRequest, final UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User loggedUser = mUserManager.get(userId);
    Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
    String empDeptId = loggedEmployee.getDepartment().getId();
    List<MutableApplicationTES> applications = getContentManager().getFacultyMembers(empDeptId);
    getTeacherPersonalData(empDeptId, applications);
    Integer getTotalRecords = getContentManager().getTotalRecords(empDeptId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    object.add("totalRecords", getTotalRecords);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getEligibleFacultyMembers(final Integer pSemesterId, final String pDeptId, final Request pRequest, final UriInfo pUriInfo) {
    String empDeptId = null;
    if (pDeptId.equals("none") || pDeptId.equals(null)) {
      String userId = SecurityUtils.getSubject().getPrincipal().toString();
      User loggedUser = mUserManager.get(userId);
      Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
      empDeptId = loggedEmployee.getDepartment().getId();
    } else {
      empDeptId = pDeptId;
    }
    List<MutableApplicationTES> applications = getContentManager().getEligibleFacultyMembers(empDeptId, pSemesterId);
    getTeacherPersonalData(empDeptId, applications);
    Integer getTotalRecords = getContentManager().getTotalRecords(empDeptId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    object.add("totalRecords", getTotalRecords);
    localCache.invalidate();
    return object.build();
  }

  private void getTeacherPersonalData(String pDeptId, List<MutableApplicationTES> applications) {
    for(int i = 0; i < applications.size(); i++) {
      applications.get(i).setFirstName(
          mPersonalInformationManager.get(applications.get(i).getTeacherId()).getFirstName());
      applications.get(i)
          .setLastName(mPersonalInformationManager.get(applications.get(i).getTeacherId()).getLastName());
      applications.get(i).setDeptShortName(mDepartmentManager.get(pDeptId).getShortName());
      applications.get(i).setDeptId(pDeptId);
      applications.get(i).setDesignation(
          mDesignationManager.get(mEmployeeManager.get(applications.get(i).getTeacherId()).getDesignationId())
              .getDesignationName());
    }
  }

  public JsonObject getReviewEligibleCourses(final String pCourseType, final Request pRequest, final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    List<MutableApplicationTES> applications = getContentManager().
            getReviewEligibleCourses(studentId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId(), pCourseType, student.getTheorySection());
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    String semesterName = mSemesterManager.get(semesterId).getName();
    List<ApplicationTES> alreadyReviewedCourses = getContentManager().getAlreadyReviewedCourses(studentId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());

    Map<String, ApplicationTES> reviewedCourseMap = alreadyReviewedCourses
            .stream()
            .collect(Collectors.toMap(t -> t.getTeacherId() + t.getReviewEligibleCourseId() + t.getStudentId() + t.getSemester(), t -> t));


    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    applications.forEach(a -> {
      if (reviewedCourseMap.containsKey(a.getTeacherId() + a.getReviewEligibleCourseId() + a.getStudentId() + a.getSemester())) {
        a.setStatus(1);
      } else {
        a.setStatus(0);
      }
      children.add(toJson(a, pUriInfo, localCache));
    });
    object.add("entries", children);
    object.add("semesterName", semesterName);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getFacultyInfo(final String courseId, final String courseType, final Request pRequest, final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    String section = courseType.equals("Theory") ? student.getTheorySection() : student.getSessionalSection();
    List<ApplicationTES> facultyInfo = getContentManager().getTeachersInfo(courseId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId(), section);
    facultyInfo.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getAlreadyReviewedCoursesInfo(final String pCourseId, final String pTeacherId, final Request pRequest, final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    List<ApplicationTES> applications = getContentManager().getReviewedCoursesForReadOnlyMode(pCourseId, pTeacherId, studentId, mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    applications.forEach(a -> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
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
