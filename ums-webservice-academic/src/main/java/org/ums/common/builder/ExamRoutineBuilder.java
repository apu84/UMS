package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.immutable.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExamRoutineBuilder implements Builder<ExamRoutine, MutableExamRoutine> {

  @Override
  public void build(JsonObjectBuilder pBuilder, ExamRoutine pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) throws Exception {

    // please check if the object is null or not as required.
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getExamTypeId() != null) {
      pBuilder.add("examTypeId", pReadOnly.getExamTypeId());
    }
    if(pReadOnly.getExamTypeName() != null) {
      pBuilder.add("examTypeName", pReadOnly.getExamTypeName());
    }
    if(pReadOnly.getInsertType() != null) {
      pBuilder.add("insertType", pReadOnly.getInsertType());
    }

    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }
    if(pReadOnly.getTotalStudent() != null) {
      pBuilder.add("totalStudent", pReadOnly.getTotalStudent());
    }
    if(pReadOnly.getProgramName() != null) {
      pBuilder.add("programName", pReadOnly.getProgramName());
    }

    if(pReadOnly.getCourseYear() != null) {
      pBuilder.add("year", pReadOnly.getCourseYear());
    }

    if(pReadOnly.getCourseSemester() != null) {
      pBuilder.add("semester", pReadOnly.getCourseSemester());
    }

    if(pReadOnly.getCourseNumber() != null) {
      pBuilder.add("courseNo", pReadOnly.getCourseNumber());
    }

    if(pReadOnly.getCourseTitle() != null) {
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());
    }
    if(pReadOnly.getExamDateOriginal() != null) {
      pBuilder.add("examDateOriginal", pReadOnly.getExamDateOriginal());
    }

    if(pReadOnly.getExamGroup() != null) {
      pBuilder.add("examGroup", pReadOnly.getExamGroup());
    }
  }

  @Override
  public void build(MutableExamRoutine pMutable, JsonObject pJsonObject,
      final LocalCache pLocalCache) throws Exception {
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setExamTypeId(pJsonObject.getInt("examType"));
    pMutable.setInsertType(pJsonObject.getString("insertType"));

    JsonArray entries = pJsonObject.getJsonArray("entries");
    ExamRoutineDto routine;
    List<ExamRoutineDto> routineList = new ArrayList<>(entries.size());
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      routine = new ExamRoutineDto();
      routine.setExamDate(jsonObject.getString("date"));
      routine.setExamTime(jsonObject.getString("time"));
      routine.setProgramId(jsonObject.getInt("program"));
      routine.setCourseId(jsonObject.getString("course"));
      routine.setExamGroup(jsonObject.getInt("group"));

      routineList.add(routine);
    }
    pMutable.setRoutine(routineList);
  }
}
