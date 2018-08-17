package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.manager.StudentManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
@Component
public class ExpelledInformationBuilder implements Builder<ExpelledInformation, MutableExpelledInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, ExpelledInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }
    if(pReadOnly.getStudentId() != null) {
      pBuilder.add("studentId", pReadOnly.getStudentId());
      /*
       * JsonObjectBuilder student = Json.createObjectBuilder(); mStudentBuilder.build(student,
       * mStudentManager.get(pReadOnly.getStudentId()), pUriInfo, pLocalCache);
       * pBuilder.add("student", student);
       */
    }
    if(pReadOnly.getStudentName() != null) {
      pBuilder.add("studentName", pReadOnly.getStudentName());
    }
    if(pReadOnly.getSemesterName() != null) {
      pBuilder.add("semesterName", pReadOnly.getSemesterName());
    }
    if(pReadOnly.getProgramName() != null) {
      pBuilder.add("programName", pReadOnly.getProgramName());
    }
    if(pReadOnly.getExamTypeName() != null) {
      pBuilder.add("examTypeName", pReadOnly.getExamTypeName());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getDeptId() != null) {
      pBuilder.add("deptId", pReadOnly.getDeptId());
    }
    if(pReadOnly.getDeptName() != null) {
      pBuilder.add("deptName", pReadOnly.getDeptName());
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
    if(pReadOnly.getExamType() != null) {
      pBuilder.add("examType", pReadOnly.getExamType());
    }
    if(pReadOnly.getExpelledReason() != null) {
      pBuilder.add("expelledReason", pReadOnly.getExpelledReason());
    }
    if(pReadOnly.getStatus() != null) {
      pBuilder.add("status", pReadOnly.getStatus());
    }
    if(pReadOnly.getInsertionDate() != null) {
      pBuilder.add("insertionDate", pReadOnly.getInsertionDate());
    }
    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }
    if(pReadOnly.getRegType() != null) {
      pBuilder.add("regType", pReadOnly.getRegType());
    }

  }

  @Override
  public void build(MutableExpelledInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("studentId"))
      pMutable.setStudentId(pJsonObject.getString("studentId"));
    if(pJsonObject.containsKey("courseId"))
      pMutable.setCourseId(pJsonObject.getString("courseId"));
    if(pJsonObject.containsKey("examType"))
      pMutable.setExamType(pJsonObject.getInt("examType"));
    if(pJsonObject.containsKey("expelReason"))
      pMutable.setExpelledReason(pJsonObject.getString("expelReason"));
    if(pJsonObject.containsKey("regType"))
      pMutable.setRegType(pJsonObject.getInt("regType"));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setExamDate(pJsonObject.getString("examDate"));
  }
}
