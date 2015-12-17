package org.ums.academic.builder;


import org.ums.domain.model.MutableProgram;
import org.ums.domain.model.Program;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class ProgramBuilder implements Builder<Program, MutableProgram> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Program pReadOnly, UriInfo pUriInfo) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add("longName", pReadOnly.getLongName());
    pBuilder.add("programType", pUriInfo.getBaseUriBuilder().path("academic").path("programtype")
        .path(String.valueOf(pReadOnly.getProgramType().getId())).build().toString());
    pBuilder.add("department", pUriInfo.getBaseUriBuilder().path("academic").path("department")
        .path(String.valueOf(pReadOnly.getDepartment().getId())).build().toString());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableProgram pMutable, JsonObject pJsonObject) throws Exception {
    //Do nothing
  }
}
