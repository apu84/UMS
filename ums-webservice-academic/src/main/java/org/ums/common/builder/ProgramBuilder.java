package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.ProgramType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ProgramBuilder implements Builder<Program, MutableProgram> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Program pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add("longName", pReadOnly.getLongName());

    ProgramType programType = (ProgramType) pLocalCache.cache(() -> pReadOnly.getProgramType(),
        pReadOnly.getProgramTypeId(), ProgramType.class);
    pBuilder.add("programType", pUriInfo.getBaseUriBuilder().path("academic").path("programtype")
        .path(String.valueOf(programType.getId())).build().toString());

    Department department = (Department) pLocalCache.cache(() -> pReadOnly.getDepartment(),
        pReadOnly.getDepartmentId(), Department.class);
    pBuilder.add("department", pUriInfo.getBaseUriBuilder().path("academic").path("department")
        .path(String.valueOf(department.getId())).build().toString());

    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableProgram pMutable, JsonObject pJsonObject, final LocalCache pLocalCache)
      throws Exception {
    // Do nothing
  }
}
