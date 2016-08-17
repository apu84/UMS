package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.common.builder.SemesterBuilder;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.manager.SemesterManager;
import org.ums.persistent.model.PersistentSemester;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class SemesterResourceHelper extends ResourceHelper<Semester, MutableSemester, Integer> {
  @Autowired
  private SemesterManager mManager;

  @Autowired
  private SemesterBuilder mBuilder;

  @Override
  public SemesterManager getContentManager() {
    return mManager;
  }

  @Override
  public SemesterBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableSemester mutableSemester = new PersistentSemester();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSemester, pJsonObject, localCache);
    mutableSemester.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(SemesterResource.class).path(SemesterResource.class, "get").build(mutableSemester.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getEtag(Semester pReadonly) {
    return "";
  }

  //TODO: Remove this @RequiresPermissions, as it is added to test permission workflow
//  @RequiresPermissions(value = "lookup:semester")
  public JsonObject buildSemesters(final List<Semester> pSemesters, final UriInfo pUriInfo) throws Exception {

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Semester readOnly : pSemesters) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
}
