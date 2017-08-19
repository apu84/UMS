package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.ContentManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.persistent.model.meeting.PersistentAgendaResolution;
import org.ums.resource.ResourceHelper;
import org.ums.solr.repository.transaction.meeting.AgendaResolutionTransaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgendaResolutionResourceHelper extends ResourceHelper<AgendaResolution, MutableAgendaResolution, Long> {

  @Autowired
  AgendaResolutionManager mManager;

  @Autowired
  AgendaResolutionBuilder mBuilder;

  public JsonObject getAgendaResolution(final String pScheduleId, final UriInfo pUriInfo) {
    List<AgendaResolution> pAgendaResolution = new ArrayList<>();
    try {
      pAgendaResolution = mManager.getAgendaResolution(Long.parseLong(pScheduleId));
    } catch(EmptyResultDataAccessException e) {

    }
    return buildJson(pAgendaResolution, pUriInfo);
  }

  @Transactional
  public Response saveAgendaResolution(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableAgendaResolution mutableAgendaResolution = new PersistentAgendaResolution();
    LocalCache localCache = new LocalCache();
    mBuilder.build(mutableAgendaResolution, pJsonObject.getJsonObject("entries"), localCache);
    mManager.saveAgendaResolution(mutableAgendaResolution);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response updateAgendaResolution(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableAgendaResolution mutableAgendaResolution = new PersistentAgendaResolution();
    LocalCache localCache = new LocalCache();
    mBuilder.updateBuilder(mutableAgendaResolution, pJsonObject.getJsonObject("entries"), localCache);
    mManager.updateAgendaResolution(mutableAgendaResolution);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject buildJson(List<AgendaResolution> pAgendaResolution, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(AgendaResolution agendaResolution : pAgendaResolution) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, agendaResolution, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<AgendaResolution, MutableAgendaResolution, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AgendaResolution, MutableAgendaResolution> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AgendaResolution pReadonly) {
    return pReadonly.getLastModified();
  }
}
