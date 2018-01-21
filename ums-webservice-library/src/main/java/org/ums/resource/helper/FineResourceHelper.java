package org.ums.resource.helper;

import org.jetbrains.annotations.Mutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.FineBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.enums.library.FineStatus;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CirculationManager;
import org.ums.manager.library.FineManager;
import org.ums.persistent.model.library.PersistentCirculation;
import org.ums.persistent.model.library.PersistentFine;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class FineResourceHelper extends ResourceHelper<Fine, MutableFine, Long> {

  @Autowired
  FineManager mManager;

  @Autowired
  FineBuilder mBuilder;

  @Autowired
  CirculationManager mCirculationManager;

  public JsonObject getFines(String pPatronId, UriInfo pUriInfo) {
    List<Fine> circulations = new ArrayList<>();
    try {
      circulations = mManager.getFines(pPatronId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(circulations, pUriInfo);
  }

  @Transactional
  public Response updateFine(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<Fine> fines = new ArrayList<>();
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < pJsonObject.getJsonArray("entries").size(); i++) {
      MutableFine mutableFine = new PersistentFine();
      mBuilder.build(mutableFine, pJsonObject.getJsonArray("entries").getJsonObject(i), localCache);
      mManager.updateFine(mutableFine);
      MutableCirculation mutableCirculation = new PersistentCirculation();
      mutableCirculation = (MutableCirculation) mCirculationManager.getCirculation(mutableFine.getCirculationId());
      mutableCirculation.setFineStatus(3);
      mCirculationManager.updateCirculationStatus(mutableCirculation);
    }
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<Fine> pFine, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Fine fine : pFine) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, fine, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Fine, MutableFine, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Fine, MutableFine> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Fine pReadonly) {
    return pReadonly.getLastModified();
  }
}
