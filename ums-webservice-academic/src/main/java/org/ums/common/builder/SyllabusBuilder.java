package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class SyllabusBuilder implements Builder<Syllabus, MutableSyllabus> {
  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  ProgramManager mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Syllabus pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) {
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

    pBuilder.add("department_name", program.getDepartment().getShortName());

    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("syllabus")
        .path(pReadOnly.getId()).build().toString());
  }

  @Override
  public void build(MutableSyllabus pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) {
    String id = pJsonObject.getString("syllabusId");
    int semesterId = Integer.parseInt(pJsonObject.getString("semesterId"));
    int programId =
        Integer.parseInt(pJsonObject.getJsonObject("programSelector").getString("programId"));
    pMutable.setId(id);

    PersistentSemester pSemester = new PersistentSemester();
    pSemester.setId(semesterId);
    pMutable.setSemester(pSemester);
    PersistentProgram persistentProgram = new PersistentProgram();
    persistentProgram.setId(programId);
    pMutable.setSemester(pSemester);
    pMutable.setProgram(persistentProgram);

    // Unnecessary. No use of it in any Use Case. If any Use case need this then we will open it
    // again
    // pMutable.setSemester(mSemesterManager.get(semesterId));
    // pMutable.setProgram(mProgramManager.get(programId));
  }
}
