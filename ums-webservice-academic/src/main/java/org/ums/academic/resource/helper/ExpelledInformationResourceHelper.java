package org.ums.academic.resource.helper;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.ExpelledInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;
import org.ums.enums.ProgramType;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentExpelledInformation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
@Component
public class ExpelledInformationResourceHelper extends
    ResourceHelper<ExpelledInformation, MutableExpelledInformation, Long> {
  @Autowired
  ExpelledInformationManager mManager;
  @Autowired
  ExpelledInformationBuilder mBuilder;
  @Autowired
  UGRegistrationResultManager mUGRegistrationResultManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  CourseManager mCourseManager;
  @Autowired
  ExamRoutineManager mExamRoutineManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  DepartmentManager mDepartmentManager;
  @Autowired
  ProgramManager mProgramManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<MutableExpelledInformation> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentExpelledInformation application = new PersistentExpelledInformation();
    application.setSemesterId(11012017);
    getBuilder().build(application, jsonObject, localCache);

    mManager.create(application);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response deleteExpelStudents(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableExpelledInformation> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentExpelledInformation application = new PersistentExpelledInformation();
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    mManager.delete(applications);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getCourseList(final String pStudentId, final Integer pRegType, final Request pRequest,
      final UriInfo pUriInfo) {
    Integer examType = getExamType(pRegType);
    List<ExamRoutineDto> examRoutineList = mExamRoutineManager.getExamRoutine(11012017, examType);
    Map<String, String> examRoutineMapWithCourseId = examRoutineList
            .stream()
            .collect(Collectors.toMap(e->e.getCourseId(), e->e.getExamDate()));
    List<ExpelledInformation> expelledInfo=getContentManager().getAll();

    List<UGRegistrationResult> registeredTheoryCourseList =
        mUGRegistrationResultManager.getRegisteredTheoryCourseByStudent(pStudentId, 11012017, examType,pRegType);

    List<MutableExpelledInformation> mutableExpelledInformationList = new ArrayList<>();
    for(UGRegistrationResult registrationResult : registeredTheoryCourseList) {
      MutableExpelledInformation expelledInformation =new PersistentExpelledInformation();
      expelledInformation.setCourseId(registrationResult.getCourseId());
      expelledInformation.setCourseNo(mCourseManager.get(registrationResult.getCourseId()).getNo());
      expelledInformation.setCourseTitle(mCourseManager.get(registrationResult.getCourseId()).getTitle());
      expelledInformation.setExamDate(examRoutineMapWithCourseId.get(registrationResult.getCourseId()));
      expelledInformation.setRegType(registrationResult.getType().getId());
      expelledInformation.setStatus(expelledInfo.stream().filter(a->a.getSemesterId()==11012017 && a.getCourseId().equals(registrationResult.getCourseId()) && a.getStudentId().equals(pStudentId)
               && a.getExamType()==examType).collect(Collectors.toList()).size()==1 ? 1:0);
      mutableExpelledInformationList.add(expelledInformation);
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableExpelledInformation app : mutableExpelledInformationList) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getExpelInfoList(final Integer pSemesterId, final Integer pRegType, final Request pRequest,
                                  final UriInfo pUriInfo) {
    Integer examType = getExamType(pRegType);
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    Integer hideDeleteOption=0;
    if(pSemesterId.equals(semesterId)){
      hideDeleteOption=1;
    }
    List<ExamRoutineDto> examRoutineList = mExamRoutineManager.getExamRoutine(11012017, examType);

    Map<String, String> examRoutineMapWithCourseId = examRoutineList
            .stream()
            .collect(Collectors.toMap(e->e.getCourseId(), e->e.getExamDate()));
    Map<String, String> examRoutineMapWithProgramId= examRoutineList
            .stream()
            .collect(Collectors.toMap(e->e.getCourseId(), e->e.getProgramName()));
    List<ExpelledInformation> expelledInfo=getContentManager().getAll().stream().filter(a->a.getExamType()==examType && a.getRegType()==pRegType).collect(Collectors.toList());

    List<MutableExpelledInformation> expelInfoList = new ArrayList<>();
    for(ExpelledInformation exp : expelledInfo) {
      MutableExpelledInformation expelledInformation =new PersistentExpelledInformation();
      expelledInformation.setStudentId(exp.getStudentId());
      expelledInformation.setStudentName(mStudentManager.get(exp.getStudentId()).getFullName());
      expelledInformation.setSemesterId(exp.getSemesterId());
      expelledInformation.setSemesterName(mSemesterManager.get(exp.getSemesterId()).getName());
      expelledInformation.setDeptId(mStudentManager.get(exp.getStudentId()).getDepartmentId());
      expelledInformation.setDeptName(mDepartmentManager.get(mStudentManager.get(exp.getStudentId()).getDepartmentId()).getShortName());
      expelledInformation.setExamType(exp.getExamType());
      expelledInformation.setExamTypeName(CourseRegType.get(pRegType).getLabel());
      expelledInformation.setStatus(hideDeleteOption);
      expelledInformation.setExpelledReason(exp.getExpelledReason());
      expelledInformation.setCourseId(exp.getCourseId());
      expelledInformation.setCourseNo(mCourseManager.get(exp.getCourseId()).getNo());
      expelledInformation.setCourseTitle(mCourseManager.get(exp.getCourseId()).getTitle());
      expelledInformation.setExamDate(examRoutineMapWithCourseId.get(exp.getCourseId()));
      expelledInformation.setProgramName(examRoutineMapWithProgramId.get(exp.getCourseId()));
      expelInfoList.add(expelledInformation);
    }

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableExpelledInformation app : expelInfoList) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @NotNull
  public Integer getExamType(Integer pRegType) {
    return pRegType == CourseRegType.REGULAR.getId() ? ExamType.SEMESTER_FINAL.getId()
        : ExamType.CLEARANCE_CARRY_IMPROVEMENT.getId();
  }

  @Override
  protected ContentManager<ExpelledInformation, MutableExpelledInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ExpelledInformation, MutableExpelledInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ExpelledInformation pReadonly) {
    return null;
  }
}
