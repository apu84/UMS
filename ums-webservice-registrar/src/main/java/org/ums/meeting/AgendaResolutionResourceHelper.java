package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.ContentManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.persistent.model.meeting.PersistentAgendaResolution;
import org.ums.resource.ResourceHelper;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;
import org.ums.solr.repository.meeting.AgendaResolutionRepository;

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

  @Autowired
  AgendaResolutionRepository mRepository;

  public JsonObject getAgendaResolution(final String pScheduleId, final UriInfo pUriInfo) {
    if(mManager.exists(Long.parseLong(pScheduleId))) {
      List<AgendaResolution> agendaResolutionList = mManager.getAgendaResolution(Long.parseLong(pScheduleId));
      return buildJsonResponse(agendaResolutionList, pUriInfo);
    }
    return null;
  }

  public JsonObject searchAgendaResolution(int pPage, int pItemPerPage, final String pFilter, final UriInfo pUriInfo) {
    List<AgendaResolutionDocument> agendaResolutionDocuments =
        mRepository.findByCustomQuery(queryBuilder(pFilter), new PageRequest(pPage, pItemPerPage));
    List<AgendaResolution> agendaResolutions = new ArrayList<>();
    for(AgendaResolutionDocument document : agendaResolutionDocuments) {
      agendaResolutions.add(mManager.get(Long.valueOf(document.getId())));
    }

    return convertToJson(agendaResolutions, mRepository.getTotalCount(queryBuilder(pFilter)), pUriInfo);
  }

  private String queryBuilder(String pFilter) {
    return "*Agenda* and type_s:Meeting";
  }

  public Response updateAgendaResolution(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableAgendaResolution mutableAgendaResolution = new PersistentAgendaResolution();
    LocalCache localCache = new LocalCache();
    mBuilder.build(mutableAgendaResolution, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableAgendaResolution);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response deleteAgendaResolution(String pId, UriInfo pUriInfo) {
    mManager.delete((MutableAgendaResolution) mManager.get(Long.parseLong(pId)));
    return Response.noContent().build();
  }

  private JsonObject convertToJson(List<AgendaResolution> agendaResolutions, long totalCount, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AgendaResolution agendaResolution : agendaResolutions) {
      children.add(toJson(agendaResolution, pUriInfo, localCache));
    }
    object.add("entries", children);
    object.add("total", totalCount);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableAgendaResolution mutableAgendaResolution = new PersistentAgendaResolution();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableAgendaResolution, pJsonObject.getJsonObject("entries"), localCache);
    mManager.create(mutableAgendaResolution);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
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
