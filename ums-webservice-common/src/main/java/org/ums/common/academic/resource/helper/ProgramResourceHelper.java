package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.builder.ProgramBuilder;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.immutable.Program;
import org.ums.manager.ProgramManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ProgramResourceHelper extends ResourceHelper<Program, MutableProgram, Integer> {
  @Autowired
  @Qualifier("programManager")
  private ProgramManager mManager;

  @Autowired
  private ProgramBuilder mBuilders;

  @Override
  public ProgramManager getContentManager() {
    return mManager;
  }

  @Override
  public ProgramBuilder getBuilder() {
    return mBuilders;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected String getEtag(Program pReadonly) {
    return "";
  }
}
