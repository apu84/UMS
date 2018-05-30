package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;

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
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("studentId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
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
  }
}
