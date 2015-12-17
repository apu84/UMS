package org.ums.academic.builder;


import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.ProgramType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class ProgramTypeBuilder implements Builder<ProgramType, MutableProgramType> {
  public void build(final JsonObjectBuilder pBuilder, final ProgramType pProgramType,
                    final UriInfo pUriInfo) throws Exception {
    pBuilder.add("id", pProgramType.getId());
    pBuilder.add("name", pProgramType.getName());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("programtype")
        .path(String.valueOf(pProgramType.getId())).build().toString());
  }

  public void build(final MutableProgramType pMutableProgramType, JsonObject pJsonObject) throws Exception {
    pMutableProgramType.setId(pJsonObject.getInt("id"));
    pMutableProgramType.setName(pJsonObject.getString("name"));
  }
}
