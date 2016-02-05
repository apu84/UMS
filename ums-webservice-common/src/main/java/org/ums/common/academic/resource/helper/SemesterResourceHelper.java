package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentSemester;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.Semester;
import org.ums.manager.ContentManager;

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
  @Qualifier("semesterManager")
  private ContentManager<Semester, MutableSemester, Integer> mManager;

  @Autowired
  private List<Builder<Semester, MutableSemester>> mBuilders;

  @Override
  public ContentManager<Semester, MutableSemester, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<Semester, MutableSemester>> getBuilders() {
    return mBuilders;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableSemester mutableSemester = new PersistentSemester();
    LocalCache localCache = new LocalCache();
    for (Builder<Semester, MutableSemester> builder : mBuilders) {
      builder.build(mutableSemester, pJsonObject, localCache);
    }
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
