package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 08-Aug-16.
 */
@Component
public class StudentRecordBuilder implements Builder<StudentRecord, MutableStudentRecord> {
  @Override
  public void build(JsonObjectBuilder pBuilder, StudentRecord pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }

    if(pReadOnly.getStudentId() != null) {
      pBuilder.add("studentId", pReadOnly.getStudentId());
    }

    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }

    if(pReadOnly.getYear() != null) {
      pBuilder.add("year", pReadOnly.getYear());
    }

    if(pReadOnly.getAcademicSemester() != null) {
      pBuilder.add("semester", pReadOnly.getAcademicSemester());
    }

    if(pReadOnly.getCGPA() != null) {
      pBuilder.add("cgpa", pReadOnly.getCGPA());
    }

    if(pReadOnly.getGPA() != null) {
      pBuilder.add("gpa", pReadOnly.getGPA());
    }

    if(pReadOnly.getType() != null) {
      pBuilder.add("type", pReadOnly.getType().getValue());
    }

    if(pReadOnly.getStatus() != null) {
      pBuilder.add("status", pReadOnly.getStatus().getValue());
    }

    if(pReadOnly.getProgramId() != null) {
      pBuilder.add("programId", pReadOnly.getProgramId());
    }

  }

  @Override
  public void build(MutableStudentRecord pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
