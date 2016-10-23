package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.SyllabusResource;
import org.ums.common.builder.SyllabusBuilder;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.manager.SyllabusManager;
import org.ums.persistent.model.PersistentSyllabus;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class SyllabusResourceHelper extends ResourceHelper<Syllabus, MutableSyllabus, String> {
  @Autowired
  private SyllabusManager mManager;

  @Autowired
  private SyllabusBuilder mBuilder;

  @Override
  public SyllabusManager getContentManager() {
    return mManager;
  }

  @Override
  public SyllabusBuilder getBuilder() {
    return mBuilder;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableSyllabus mutableSyllabus = new PersistentSyllabus();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSyllabus, pJsonObject, localCache);
    mutableSyllabus.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SyllabusResource.class)
            .path(SyllabusResource.class, "get").build(mutableSyllabus.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public JsonObject buildSyllabuses(final List<Syllabus> pSyllabuses, final UriInfo pUriInfo)
      throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Syllabus readOnly : pSyllabuses) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getEtag(Syllabus pReadonly) {
    return pReadonly.getLastModified();
  }
}
