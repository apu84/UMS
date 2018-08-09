package org.ums.academic.resource.helper;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.QuestionCorrectionResource;
import org.ums.builder.Builder;
import org.ums.builder.QuestionCorrectionBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.CourseType;
import org.ums.enums.ProgramType;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentQuestionCorrectionInfo;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
@Component
public class QuestionCorrectionResourceHelper extends
    ResourceHelper<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long> {

  @Autowired
  QuestionCorrectionManager mManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  QuestionCorrectionBuilder mBuilder;

  @Autowired
  CourseManager mCourseManager;
  @Autowired
  ProgramManager mProgramManager;
  @Autowired
  CourseTeacherManager mCourseTeacherManager;
  @Autowired
  ExamRoutineManager mExamRoutineManager;
  @Autowired
  SemesterManager mSemesterManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentQuestionCorrectionInfo application = new PersistentQuestionCorrectionInfo();
    application.setSemesterId(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    getBuilder().build(application, jsonObject, localCache);
    try {
      mManager.create(application);
    } catch(Exception e) {
      e.printStackTrace();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response deleteRecords(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableQuestionCorrectionInfo> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentQuestionCorrectionInfo application = new PersistentQuestionCorrectionInfo();
      application.setSemesterId(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    mManager.delete(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getCourses(final Integer pProgramId, final Integer pYear, final Integer pSemester,
      final Request pRequest, final UriInfo pUriInfo) {
    List<Course> courses = mCourseManager.getByYearSemester(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId().toString(), pProgramId.toString(), pYear, pSemester);
    List<Course> theoryCourses=courses.stream().filter(f->f.getCourseType().getId()==CourseType.THEORY.getId()).collect(Collectors.toList());

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    List<MutableQuestionCorrectionInfo> list=new ArrayList<>();
    for(int i=0;i<theoryCourses.size();i++ ){
      MutableQuestionCorrectionInfo app = new PersistentQuestionCorrectionInfo();
      app.setCourseId(theoryCourses.get(i).getId());
      app.setCourseNo(mCourseManager.get(theoryCourses.get(i).getId()).getNo());
      app.setCourseTitle(mCourseManager.get(theoryCourses.get(i).getId()).getTitle());
      list.add(app);
    }
     for(MutableQuestionCorrectionInfo app : list) {
       children.add(toJson(app, pUriInfo, localCache));
     }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getQuestionCorrectionInfo(final Integer pSemesterId, final Integer pExamType,
      final Request pRequest, final UriInfo pUriInfo) {
    List<MutableQuestionCorrectionInfo> list = getMutableQuestionCorrectionInfo(pSemesterId, pExamType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableQuestionCorrectionInfo app : list) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getExamDate(final Integer pSemesterId, final Integer pExamType, final String pCourseId,
      final Request pRequest, final UriInfo pUriInfo) {
    String examDate = "";
    List<ExamRoutineDto> examDateList =
        mExamRoutineManager.getExamDatesBySemesterAndTypeAndCourseId(pCourseId, pSemesterId, pExamType);
    for(ExamRoutineDto app : examDateList) {
      examDate = app.getExamDate();
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("entries", examDate);
    localCache.invalidate();
    return object.build();
  }

  public List<MutableQuestionCorrectionInfo> getMutableQuestionCorrectionInfo(Integer pSemesterId, Integer pExamType) {
    List<QuestionCorrectionInfo> questionCorrectionInfo = mManager.getInfoBySemesterIdAndExamType(pSemesterId, pExamType);
    List<CourseTeacher> courseTeachers=mCourseTeacherManager.getDistinctCourseTeacher(pSemesterId);
    Map<String ,String> courseTeacherMap=courseTeachers.stream()
            .collect(Collectors.toMap(a->a.getCourseId(), a->a.getTeacher().getName(),(oldValue, newValue) -> oldValue=oldValue+","+newValue));

    List<MutableQuestionCorrectionInfo> list=new ArrayList<>();
    for(QuestionCorrectionInfo app:questionCorrectionInfo){
     MutableQuestionCorrectionInfo info=new PersistentQuestionCorrectionInfo();
     info.setProgramId(app.getProgramId());
     info.setProgramName(mProgramManager.get(app.getProgramId()).getShortName());
     info.setYear(app.getYear());
     info.setSemester(app.getSemester());
     info.setCourseId(app.getCourseId());
     info.setCourseNo(mCourseManager.get(app.getCourseId()).getNo());
     info.setCourseTitle(mCourseManager.get(app.getCourseId()).getTitle());
     info.setIncorrectQuestionNo(app.getIncorrectQuestionNo());
     info.setTypeOfMistake(app.getTypeOfMistake());
     info.setEmployeeName(courseTeacherMap.get(app.getCourseId()));
     info.setExamDate(app.getExamDate());
     info.setExamType(app.getExamType());
     list.add(info);
    }
    return list;
  }

  @Override
  protected ContentManager<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<QuestionCorrectionInfo, MutableQuestionCorrectionInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(QuestionCorrectionInfo pReadonly) {
    return null;
  }
}
