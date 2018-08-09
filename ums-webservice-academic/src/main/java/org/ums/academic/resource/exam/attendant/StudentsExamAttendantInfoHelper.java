package org.ums.academic.resource.exam.attendant;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.exam.attendant.helper.ExamAttendantYearSemesterWiseData;
import org.ums.academic.resource.exam.attendant.helper.StudentsExamAttendantData;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.enums.ProgramType;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentStudentsExamAttendantInfo;
import org.ums.resource.ResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;
import java.util.stream.Collector;
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
      application.setSemesterId(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
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
    Map<String,Integer> absentStudentsMap=examAttendantInfoList
            .stream()
            .collect(Collectors.toMap(e->e.getYear().toString()+"-"+e.getSemester()+"-"+e.getProgramId(),e->e.getAbsentStudents(),
                    (oldValue, newValue) -> oldValue));
    List<Program> programs=mProgramManager.getAll();
    Map<Integer,String> programDeptMap=programs.stream().
            collect(Collectors.toMap(e->e.getId(),e->e.getDepartmentId()));
    Map<Integer, Map<String, List<StudentsExamAttendantInfo>>> map=
            examAttendantInfoList.stream().collect(Collectors.groupingBy(StudentsExamAttendantInfo::getProgramId,
                    Collectors.groupingBy(p->p.getYear().toString()+"-"+p.getSemester())));
    Map<Integer, Map<String, Integer>> tmpMap = new HashMap<>();
    for(Map.Entry<Integer, Map<String, List<StudentsExamAttendantInfo>>> entry: map.entrySet()){
      Map<String, List<StudentsExamAttendantInfo>> tmpInnerMap = entry.getValue();
      for(Map.Entry<String, List<StudentsExamAttendantInfo>> innerEntry: tmpInnerMap.entrySet()){
        Map<String, Integer> tmpConvertedMap = new HashMap<>();
        tmpConvertedMap.put(innerEntry.getKey(), innerEntry.getValue().stream().mapToInt(p->p.getRegisteredStudents()).sum());
        Map<String, Integer> previousMap = new HashMap<>();
        if(tmpMap.containsKey(entry.getKey()))
          previousMap = tmpMap.get(entry.getKey());
        previousMap.put(innerEntry.getKey(), innerEntry.getValue().stream().mapToInt(p->p.getRegisteredStudents()).sum());
        tmpMap.put(entry.getKey(), previousMap);
      }
    }
    List<StudentsExamAttendantData> studentsExamAttendantDataList = new ArrayList<>();
    return createStudentsExamAttendantData(tmpMap,studentsExamAttendantDataList,absentStudentsMap,programDeptMap);
  }

  public List<StudentsExamAttendantData> createStudentsExamAttendantData(Map<Integer, Map<String, Integer>> result,
      List<StudentsExamAttendantData> studentsExamAttendantDataList, Map<String, Integer> absentStudentsMap,
      Map<Integer, String> programDeptMap) {

    for(Map.Entry<Integer, Map<String, Integer>> entry : result.entrySet()) {
      StudentsExamAttendantData studentsExamAttendantData = new StudentsExamAttendantData();
      studentsExamAttendantData.setProgramName(mProgramManager.get(entry.getKey()).getShortName());
      studentsExamAttendantData.setDeptId(Integer.parseInt(programDeptMap.get(entry.getKey())));
      List<ExamAttendantYearSemesterWiseData> examAttendantYearSemesterWiseDataList = new ArrayList<>();
      Map<String, Integer> tmpInnerMap = entry.getValue();

      for(Map.Entry<String, Integer> innerMap : tmpInnerMap.entrySet()) {
        String key = innerMap.getKey();
        String[] parts = key.split("-");
        Integer year = Integer.parseInt(parts[0]);
        Integer semester = Integer.parseInt(parts[1]);
        Integer programId = entry.getKey();
        Integer registeredStudents = innerMap.getValue();
        ExamAttendantYearSemesterWiseData examAttendantYearSemesterWiseData =
            new ExamAttendantYearSemesterWiseData(
                year,
                semester,
                registeredStudents,
                programId,
                absentStudentsMap.get(year.toString() + "-" + semester.toString() + "-" + programId.toString()) == null ? 0
                    : absentStudentsMap.get(year.toString() + "-" + semester.toString() + "-" + programId.toString()),
                registeredStudents
                    - (absentStudentsMap.get(year.toString() + "-" + semester.toString() + "-" + programId.toString()) == null ? 0
                        : absentStudentsMap.get(year.toString() + "-" + semester.toString() + "-"
                            + programId.toString())));
        examAttendantYearSemesterWiseDataList.add(examAttendantYearSemesterWiseData);
      }
      studentsExamAttendantData.setExamAttendantYearSemesterWiseDataList(examAttendantYearSemesterWiseDataList);
      studentsExamAttendantDataList.add(studentsExamAttendantData);
    }
   studentsExamAttendantDataList.sort(Comparator.comparing(StudentsExamAttendantData::getDeptId));
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
