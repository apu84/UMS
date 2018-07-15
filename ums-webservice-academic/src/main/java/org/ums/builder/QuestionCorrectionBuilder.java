package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
@Component
public class QuestionCorrectionBuilder implements Builder<QuestionCorrectionInfo, MutableQuestionCorrectionInfo> {
  @Override
  public void build(JsonObjectBuilder pBuilder, QuestionCorrectionInfo pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getExamType() != null) {
      pBuilder.add("examType", pReadOnly.getExamType());
    }
    if(pReadOnly.getProgramId() != null) {
      pBuilder.add("programId", pReadOnly.getProgramId());
    }
    if(pReadOnly.getProgramName() != null) {
      pBuilder.add("programName", pReadOnly.getProgramName());
    }
    if(pReadOnly.getCourseId() != null) {
      pBuilder.add("courseId", pReadOnly.getCourseId());
    }
    if(pReadOnly.getCourseNo() != null) {
      pBuilder.add("courseNo", pReadOnly.getCourseId());
    }
    if(pReadOnly.getCourseTitle() != null) {
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    }
    if(pReadOnly.getYear() != null) {
      pBuilder.add("year", pReadOnly.getYear());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semester", pReadOnly.getSemester());
    }
    if(pReadOnly.getEmployeeId() != null) {
      pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    }
    if(pReadOnly.getEmployeeName() != null) {
      pBuilder.add("employeeName", pReadOnly.getEmployeeId());
    }
    if(pReadOnly.getIncorrectQuestionNo() != null) {
      pBuilder.add("incorrectQuestionNo", pReadOnly.getIncorrectQuestionNo());
    }
    if(pReadOnly.getTypeOfMistake() != null) {
      pBuilder.add("mistakeType", pReadOnly.getTypeOfMistake());
    }

  }

  @Override
  public void build(MutableQuestionCorrectionInfo pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("examType"))
      pMutable.setExamType(pJsonObject.getInt("examType"));
    if(pJsonObject.containsKey("programId"))
      pMutable.setProgramId(pJsonObject.getInt("programId"));
    if(pJsonObject.containsKey("year"))
      pMutable.setYear(pJsonObject.getInt("year"));
    if(pJsonObject.containsKey("semester"))
      pMutable.setSemester(pJsonObject.getInt("semester"));
    if(pJsonObject.containsKey("courseId"))
      pMutable.setCourseId(pJsonObject.getString("courseId"));
    if(pJsonObject.containsKey("incorrectQuestionNo"))
      pMutable.setIncorrectQuestionNo(pJsonObject.getString("incorrectQuestionNo"));
    if(pJsonObject.containsKey("mistakeType"))
      pMutable.setTypeOfMistake(pJsonObject.getString("mistakeType"));

  }
}
