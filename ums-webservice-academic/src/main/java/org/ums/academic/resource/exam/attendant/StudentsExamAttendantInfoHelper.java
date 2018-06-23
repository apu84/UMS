package org.ums.academic.resource.exam.attendant;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.exam.attendant.helper.ExamAttendantYearSemesterWiseData;
import org.ums.academic.resource.exam.attendant.helper.StudentsExamAttendantData;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentStudentsExamAttendantInfo;
import org.ums.resource.ResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
@Component
public class StudentsExamAttendantInfoHelper extends
    ResourceHelper<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo, Long> {
  @Autowired
  StudentsExamAttendantInfoManager mManager;
  @Autowired
  StudentsExamAttendantInfoBuilder mBuilder;
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
    List<MutableStudentsExamAttendantInfo> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentStudentsExamAttendantInfo application = new PersistentStudentsExamAttendantInfo();
      application.setSemesterId(11012017);
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    try {
      mManager.create(applications);
    } catch(Exception e) {
      e.printStackTrace();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public List<StudentsExamAttendantData> getExamAttendantInfo(final Integer pSemesterId, final String pExamDate, final Integer pExamType,
                                         final Request pRequest, final UriInfo pUriInfo) {

    List<StudentsExamAttendantInfo> examAttendantInfoList =
        mUGRegistrationResultManager.getExamAttendantInfo(pSemesterId,
            UmsUtils.formatDate(pExamDate, "dd-MM-yyyy", "yyyy-MM-dd"), pExamType);
    HashMap<Integer,HashMap<String,Integer>> examAttendantMap= new HashMap<Integer, HashMap<String, Integer>>();
    HashMap<String, Integer> yearSemesterRegStudentMap= new HashMap<String, Integer>();
    for(StudentsExamAttendantInfo app:examAttendantInfoList){
        yearSemesterRegStudentMap.put(app.getYear().toString() + "-" + app.getSemester().toString()+"-"+app.getProgramId().toString(),
                yearSemesterRegStudentMap.containsKey(app.getYear().toString() + "-" + app.getSemester().toString()+"-"+app.getProgramId().toString()) ?
                        yearSemesterRegStudentMap.get(app.getYear().toString() + "-" + app.getSemester().toString()+"-"+app.getProgramId().toString())+
                        app.getRegisteredStudents():app.getRegisteredStudents());
    }


    Map<Integer, List<StudentsExamAttendantInfo>> result=examAttendantInfoList.stream().collect(Collectors.groupingBy(StudentsExamAttendantInfo::getProgramId));

    List<StudentsExamAttendantData> studentsExamAttendantDataList = new ArrayList<>();

    return createStudentsExamAttendantData(result, studentsExamAttendantDataList);
  }

  public List<StudentsExamAttendantData> createStudentsExamAttendantData(
      Map<Integer, List<StudentsExamAttendantInfo>> result,
      List<StudentsExamAttendantData> studentsExamAttendantDataList) {
    for(Map.Entry<Integer, List<StudentsExamAttendantInfo>> entry : result.entrySet()) {
      StudentsExamAttendantData studentsExamAttendantData = new StudentsExamAttendantData();
      studentsExamAttendantData.setProgramName(mProgramManager.get(entry.getKey()).getShortName());

      List<ExamAttendantYearSemesterWiseData> examAttendantYearSemesterWiseDataList = new ArrayList<>();
      for(StudentsExamAttendantInfo studentsExamAttendantInfo : entry.getValue()) {
        ExamAttendantYearSemesterWiseData examAttendantYearSemesterWiseData =
            new ExamAttendantYearSemesterWiseData(studentsExamAttendantInfo.getYear(),
                studentsExamAttendantInfo.getSemester(), studentsExamAttendantInfo.getRegisteredStudents(),
                studentsExamAttendantInfo.getProgramId(), studentsExamAttendantInfo.getCourseId(),
                studentsExamAttendantInfo.getAbsentStudents() == null ? 0 : studentsExamAttendantInfo
                    .getAbsentStudents(),
                (studentsExamAttendantInfo.getRegisteredStudents())
                    - (studentsExamAttendantInfo.getAbsentStudents() == null ? 0 : studentsExamAttendantInfo
                        .getAbsentStudents()));
        examAttendantYearSemesterWiseDataList.add(examAttendantYearSemesterWiseData);
      }
      studentsExamAttendantData.setExamAttendantYearSemesterWiseDataList(examAttendantYearSemesterWiseDataList);
      studentsExamAttendantDataList.add(studentsExamAttendantData);
    }
    return studentsExamAttendantDataList;
  }

  @Override
  protected ContentManager<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(StudentsExamAttendantInfo pReadonly) {
    return null;
  }
}
