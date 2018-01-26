package org.ums.builder;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UGRegistrationResultBuilder implements Builder<UGRegistrationResult, MutableUGRegistrationResult> {

  @Override
  public void build(JsonObjectBuilder pBuilder, UGRegistrationResult pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("studentId", pReadOnly.getStudentId());
    pBuilder.add("courseId", pReadOnly.getCourseId());
    pBuilder.add("gradeLetter", pReadOnly.getGradeLetter());
    pBuilder.add("examType", pReadOnly.getExamType().getId());
    pBuilder.add("type", pReadOnly.getType().getId());
    pBuilder.add("courseNo", pReadOnly.getCourseNo());
    pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    pBuilder.add("courseYear", pReadOnly.getCourse().getYear());
    pBuilder.add("courseSemester", pReadOnly.getCourse().getSemester());
    pBuilder.add("examDate", pReadOnly.getExamDate());
    try {
      Date lastApplyDate, currentDate;

      lastApplyDate = DateUtils.addDays(UmsUtils.convertToDate(pReadOnly.getExamDate(), "dd-MM-yyyy"), -5);
      currentDate = new Date();
      pBuilder.add("lastApplyDate", UmsUtils.formatDate(lastApplyDate, "dd-MM-yyyy"));

      if(currentDate.compareTo(lastApplyDate) > 0) {
        pBuilder.add("deadline", "Date Over");
      }
      else {
        pBuilder.add("deadline", "Available");
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
    if(pReadOnly.getMessage() == null) {
      pBuilder.add("message", "null");

    }
    else {
      pBuilder.add("message", pReadOnly.getMessage());
    }

  }

  @Override
  public void build(MutableUGRegistrationResult pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
