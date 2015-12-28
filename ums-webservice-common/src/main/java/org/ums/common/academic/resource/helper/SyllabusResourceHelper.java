package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentSyllabus;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SyllabusResource;
import org.ums.domain.model.MutableSyllabus;
import org.ums.domain.model.Semester;
import org.ums.domain.model.Syllabus;
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
public class SyllabusResourceHelper extends ResourceHelper<Syllabus, MutableSyllabus, String> {
  @Autowired
  @Qualifier("syllabusManager")
  private ContentManager<Syllabus, MutableSyllabus, String> mManager;

  @Autowired
  private List<Builder<Syllabus, MutableSyllabus>> mBuilders;

  @Override
  public ContentManager<Syllabus, MutableSyllabus, String> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<Syllabus, MutableSyllabus>> getBuilders() {
    return mBuilders;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableSyllabus mutableSyllabus = new PersistentSyllabus();
    LocalCache localCache = new LocalCache();
    for (Builder<Syllabus, MutableSyllabus> builder : mBuilders) {
      builder.build(mutableSyllabus, pJsonObject, localCache);
    }
    mutableSyllabus.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(SyllabusResource.class).path(SyllabusResource.class, "get").build(mutableSyllabus.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }
  public JsonObject buildSyllabuses(final List<Syllabus> pSyllabuses, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Syllabus readOnly : pSyllabuses) {
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

