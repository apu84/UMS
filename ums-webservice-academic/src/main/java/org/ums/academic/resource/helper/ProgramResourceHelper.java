package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.ProgramBuilder;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.enums.ProgramType;
import org.ums.manager.ProgramManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgramResourceHelper extends ResourceHelper<Program, MutableProgram, Integer> {
  @Autowired
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

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    return null;
  }

  public JsonObject getPrograms(final ProgramType pProgramType, UriInfo pUriInfo){
    List<Program> programs = getContentManager()
        .getAll()
        .stream()
        .filter(p-> p.getProgramType().getId()==pProgramType.getValue())
        .collect(Collectors.toList());

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Program program: programs){
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, program, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getETag(Program pReadonly) {
    return "";
  }
}
