package org.ums.academic.resource.exam.attendant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.manager.ProgramManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
@Component
public class StudentsExamAttendantInfoBuilder implements
    Builder<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo> {
  @Autowired
  ProgramManager mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, StudentsExamAttendantInfo pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }
    if(pReadOnly.getProgramId() != null) {
      pBuilder.add("programId", pReadOnly.getProgramId());
      pBuilder.add("programName", mProgramManager.get(pReadOnly.getProgramId()).getShortName());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getYear() != null) {
      pBuilder.add("year", pReadOnly.getYear());
    }
    if(pReadOnly.getSemester() != null) {
      pBuilder.add("semester", pReadOnly.getSemester());
    }
    if(pReadOnly.getExamType() != null) {
      pBuilder.add("examType", pReadOnly.getExamType());
    }
    if(pReadOnly.getPresentStudents() != null) {
      pBuilder.add("presentStudent", pReadOnly.getPresentStudents());
    }
    if(pReadOnly.getAbsentStudents() != null) {
      pBuilder.add("absentStudent", pReadOnly.getAbsentStudents());
    }
    if(pReadOnly.getRegisteredStudents() != null) {
      pBuilder.add("registeredStudent", pReadOnly.getRegisteredStudents());
    }
    if(pReadOnly.getCourseId() != null) {
      pBuilder.add("courseId", pReadOnly.getCourseId());
    }
    if(pReadOnly.getCourseNo() != null) {
      pBuilder.add("courseNo", pReadOnly.getCourseNo());
    }
    if(pReadOnly.getCourseTitle() != null) {
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    }
    if(pReadOnly.getDeptId() != null) {
      pBuilder.add("deptId", pReadOnly.getDeptId());
    }
    if(pReadOnly.getDeptName() != null) {
      pBuilder.add("deptName", pReadOnly.getDeptName());
    }

    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }

  }

  @Override
  public void build(MutableStudentsExamAttendantInfo pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    // System.out.println(pJsonObject.getString("absentStudent"));
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("programId"))
      pMutable.setProgramId(pJsonObject.getInt("programId"));
    if(pJsonObject.containsKey("year"))
      pMutable.setYear(pJsonObject.getInt("year"));
    if(pJsonObject.containsKey("examType"))
      pMutable.setExamType(pJsonObject.getInt("examType"));
    if(pJsonObject.containsKey("semester"))
      pMutable.setSemester(pJsonObject.getInt("semester"));
    if(pJsonObject.containsKey("absentStudent"))
      pMutable.setAbsentStudents(pJsonObject.getInt("absentStudent"));
    if(pJsonObject.containsKey("registeredStudent"))
      pMutable.setRegisteredStudents(pJsonObject.getInt("registeredStudent"));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setExamDate(pJsonObject.getString("examDate"));
  }
}
