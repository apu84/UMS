package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 4/28/2016.
 */

@Component
public class SpStudentBuilder implements Builder<SpStudent, MutableSpStudent> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SpStudent pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("programId", pReadOnly.getProgram().getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("year", pReadOnly.getAcademicYear());
    pBuilder.add("semester", pReadOnly.getAcademicSemester());
    pBuilder.add("status", pReadOnly.getStatus());
  }

  @Override
  public void build(MutableSpStudent pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {
    pMutable.setId(pJsonObject.getString("id"));
    PersistentProgram program = new PersistentProgram();
    program.setId(pJsonObject.getInt("programId"));
    pMutable.setProgram(program);
    PersistentSemester semester = new PersistentSemester();
    semester.setId(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(semester);
    pMutable.setAcademicYear(pJsonObject.getInt("year"));
    pMutable.setAcademicSemester(pJsonObject.getInt("semester"));
    pMutable.setStatus(pJsonObject.getInt("status"));
  }
}
