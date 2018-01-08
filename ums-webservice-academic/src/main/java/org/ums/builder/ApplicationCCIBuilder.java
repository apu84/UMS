package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationStatus;
import org.ums.enums.ApplicationType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ApplicationCCIBuilder implements Builder<ApplicationCCI, MutableApplicationCCI> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationCCI pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null)
      pBuilder.add("id", pReadOnly.getId());
    if(pReadOnly.getSemesterId() != null)
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    if(pReadOnly.getStudentId() != null)
      pBuilder.add("studentId", pReadOnly.getStudentId());
    if(pReadOnly.getCourseId() != null)
      pBuilder.add("courseId", pReadOnly.getCourseId());
    if(pReadOnly.getApplicationType() != null)
      pBuilder.add("applicationType", pReadOnly.getApplicationType().getValue());
    if(pReadOnly.getApplicationDate() != null)
      pBuilder.add("applicationDate", pReadOnly.getApplicationDate());
    if(pReadOnly.getCourseNo() != null)
      pBuilder.add("courseNo", pReadOnly.getCourseNo());
    if(pReadOnly.getCourseTitle() != null)
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
      pBuilder.add("examDateOriginal", pReadOnly.getExamDate());
    }
    if(pReadOnly.totalStudent() != null) {
      pBuilder.add("totalStudent", pReadOnly.totalStudent());
      pBuilder.add("studentNumber", pReadOnly.totalStudent());

    }
    if(pReadOnly.getCourseYear() != null) {
      pBuilder.add("year", pReadOnly.getCourseYear());
    }

    if(pReadOnly.getCourseSemester() != null) {
      pBuilder.add("semester", pReadOnly.getCourseSemester());
    }

    if(pReadOnly.getRoomNo() != null) {
      pBuilder.add("roomNo", pReadOnly.getRoomNo());
    }
    if(pReadOnly.getRoomId() != null) {
      pBuilder.add("roomId", pReadOnly.getRoomId());
    }

    if(pReadOnly.getApplicationStatus() != null) {
      pBuilder.add("status", pReadOnly.getApplicationStatus().getId());
      pBuilder.add("statusName", pReadOnly.getApplicationStatus().getLabel());
    }

  }

  @Override
  public void build(MutableApplicationCCI pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("studentId"))
      pMutable.setStudentId(pJsonObject.getString("studentId"));
    if(pJsonObject.containsKey("courseId"))
      pMutable.setCourseId(pJsonObject.getString("courseId"));
    if(pJsonObject.containsKey("applicationType"))
      pMutable.setApplicationType(ApplicationType.get(pJsonObject.getInt("applicationType")));
    if(pJsonObject.containsKey("staus"))
      pMutable.setApplicationStatus(ApplicationStatus.get(pJsonObject.getInt("status")));
    // pMutable.setApplicationDate(pJsonObject.getString("applicationDate"));
  }
}
