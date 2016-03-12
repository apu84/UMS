package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.builder.Builder;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.readOnly.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExamRoutineBuilder  implements Builder<ExamRoutine, MutableExamRoutine> {

  @Override
  public void build(JsonObjectBuilder pBuilder, ExamRoutine pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {

  }

  @Override
  public void build(MutableExamRoutine pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setExamTypeId(Integer.parseInt(pJsonObject.getString("examType")));
    pMutable.setInsertType(pJsonObject.getString("insertType"));

    JsonArray entries=pJsonObject.getJsonArray("entries");
    ExamRoutineDto routine;
    List<ExamRoutineDto> routineList=new ArrayList<>(entries.size());
    for (int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      routine = new ExamRoutineDto();
      routine.setExamDate(jsonObject.getString("date"));
      routine. setExamTime(jsonObject.getString("time"));
      routine.setProgramId(jsonObject.getInt("program"));
      routine.setCourseId(jsonObject.getString("course"));

      routineList.add(routine);
    }
    pMutable.setRoutine(routineList);
  }
}
