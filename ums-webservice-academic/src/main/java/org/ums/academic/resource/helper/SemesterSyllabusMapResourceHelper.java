package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.SemesterResource;
import org.ums.builder.SemesterSyllabusMapsBuilder;
import org.ums.cache.LocalCache;
import org.ums.builder.SemesterSyllabusMapBuilder;
import org.ums.domain.model.dto.MutableSemesterSyllabusMapDto;
import org.ums.domain.model.immutable.SemesterSyllabusMap;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.persistent.model.PersistentSemesterSyllabusMap;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */

@Component
public class SemesterSyllabusMapResourceHelper extends
    ResourceHelper<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> {
  @Autowired
  private SemesterSyllabusMapManager mManager;

  @Autowired
  private SemesterSyllabusMapBuilder mBuilder;

  @Autowired
  private SemesterSyllabusMapsBuilder mMapBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableSemesterSyllabusMapDto mutableSemesterSyllabusMap =
        new org.ums.domain.model.dto.SemesterSyllabusMap();
    LocalCache localCache = new LocalCache();
    mMapBuilder.build(mutableSemesterSyllabusMap, pJsonObject, localCache);

    mManager.copySyllabus(mutableSemesterSyllabusMap);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class)
            .path(SemesterResource.class, "get")
            .build(mutableSemesterSyllabusMap.getAcademicSemester().getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected SemesterSyllabusMapManager getContentManager() {
    return mManager;
  }

  public Response put(final JsonObject pJsonObject) {
    MutableSemesterSyllabusMap mutable = new PersistentSemesterSyllabusMap();
    getBuilder().build(mutable, pJsonObject, null);
    SemesterSyllabusMap readOnly = load(mutable.getId());
    System.out.println(readOnly.getAcademicSemester().getStatus());

    mutable.commit(true);
    return Response.noContent().build();
  }

  @Override
  protected SemesterSyllabusMapBuilder getBuilder() {
    return mBuilder;
  }

  public JsonObject buildMaps(final List<SemesterSyllabusMap> pSyllabuses, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SemesterSyllabusMap readOnly : pSyllabuses) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getEtag(SemesterSyllabusMap pReadonly) {
    return "";
  }
}
