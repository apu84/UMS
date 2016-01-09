package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.regular.Program;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class ProgramResourceHelper extends ResourceHelper<Program, MutableProgram, Integer> {
  @Autowired
  @Qualifier("programManager")
  private ContentManager<Program, MutableProgram, Integer> mManager;

  @Autowired
  private List<Builder<Program, MutableProgram>> mBuilders;

  @Override
  public ContentManager<Program, MutableProgram, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<Program, MutableProgram>> getBuilders() {
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
