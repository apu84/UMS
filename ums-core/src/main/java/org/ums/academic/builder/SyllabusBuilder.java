package org.ums.academic.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.domain.model.*;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Configurable
public class SyllabusBuilder implements Builder<Syllabus, MutableSyllabus> {
  @Autowired
  @Qualifier("semesterManager")
  ContentManager<Semester, MutableSemester, Integer> mSemesterManager;

  @Autowired
  @Qualifier("programManager")
  ContentManager<Program, MutableProgram, Integer> mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Syllabus pReadOnly, UriInfo pUriInfo) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semester", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(pReadOnly.getSemester().getId())).build().toString());
    pBuilder.add("program", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(pReadOnly.getProgram().getId())).build().toString());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("syllabus")
        .path(pReadOnly.getId()).build().toString());
  }

  @Override
  public void build(MutableSyllabus pMutable, JsonObject pJsonObject) throws Exception {
    String id = pJsonObject.getString("id");
    int semesterId = pJsonObject.getInt("semester");
    int programId = pJsonObject.getInt("program");
    pMutable.setId(id);
    pMutable.setSemester(mSemesterManager.get(semesterId));
    pMutable.setProgram(mProgramManager.get(programId));
  }
}
