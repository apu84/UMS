package org.ums.academic.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.cache.LocalCache;
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
  public void build(JsonObjectBuilder pBuilder, Syllabus pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    Semester semester = (Semester) pLocalCache.cache(() -> pReadOnly.getSemester(),
        pReadOnly.getSemesterId(), Semester.class);
    pBuilder.add("semester", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(semester.getId())).build().toString());
    pBuilder.add("semester_name", semester.getName());

    Program program = (Program) pLocalCache.cache(() -> pReadOnly.getProgram(),
        pReadOnly.getProgramId(), Program.class);
    pBuilder.add("program", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(program.getId())).build().toString());
    pBuilder.add("program_name", program.getShortName());

    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("syllabus")
        .path(pReadOnly.getId()).build().toString());
  }

  @Override
  public void build(MutableSyllabus pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    String id = pJsonObject.getString("id");
    int semesterId = pJsonObject.getInt("semester");
    int programId = pJsonObject.getInt("program");
    pMutable.setId(id);
    pMutable.setSemester(mSemesterManager.get(semesterId));
    pMutable.setProgram(mProgramManager.get(programId));
  }
}
