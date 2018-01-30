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
    // Rumi
    if(pReadOnly.getCCIStatus() != null)
      pBuilder.add("cciStatus", pReadOnly.getCCIStatus());

    if(pReadOnly.getCCIStatus() != null)
      pBuilder.add("statusName", ApplicationStatus.get(pReadOnly.getCCIStatus()).getLabel());

    if(pReadOnly.getGradeLetter() != null)
      pBuilder.add("grade", pReadOnly.getGradeLetter());

    if(pReadOnly.getCarryYear() != null)
      pBuilder.add("carryYear", pReadOnly.getCarryYear());

    if(pReadOnly.getCarrySemester() != null)
      pBuilder.add("carrySemester", pReadOnly.getCarrySemester());

    if(pReadOnly.getFullName() != null)
      pBuilder.add("fullName", pReadOnly.getFullName());

    if(pReadOnly.getCurrentEnrolledSemester() != null)
      pBuilder.add("currentEnrolledSemester", pReadOnly.getCurrentEnrolledSemester());

    if(pReadOnly.getTotalcarry() != null)
      pBuilder.add("TOTALCARRY", pReadOnly.getTotalcarry());

    if(pReadOnly.getTotalApplied() != null)
      pBuilder.add("totalApplied", pReadOnly.getTotalApplied());

    if(pReadOnly.getTotalApproved() != null)
      pBuilder.add("taotalApproved", pReadOnly.getTotalApproved());

    if(pReadOnly.getTotalRejected() != null)
      pBuilder.add("totalRejected", pReadOnly.getTotalRejected());

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
    if(pJsonObject.containsKey("status"))
      pMutable.setApplicationStatus(ApplicationStatus.get(pJsonObject.getInt("status")));
    // pMutable.setApplicationDate(pJsonObject.getString("applicationDate"));

    // rumi
    if(pJsonObject.containsKey("cciStatus"))
      pMutable.setCCIStatus(pJsonObject.getInt("cciStatus"));

    // if(pJsonObject.containsKey("courseTitle"))
    // pMutable.setCourseTitle(pJsonObject.getString("courseTitle"));

    if(pJsonObject.containsKey("grade"))
      pMutable.setGradeLetter(pJsonObject.getString("grade"));

  }
}
