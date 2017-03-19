package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.SeatPlanPublishBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.manager.SeatPlanPublishManager;
import org.ums.persistent.model.PersistentSeatPlanPublish;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 8/4/2016.
 */
@Component
public class SeatPlanPublishResourceHelper extends ResourceHelper<SeatPlanPublish, MutableSeatPlanPublish, Integer> {

  @Autowired
  private SeatPlanPublishManager mManager;

  @Autowired
  private SeatPlanPublishBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    return null;
  }

  /**
   * This method is a post operation. SeatPlanPublish records will be created.
   * 
   * @param pSemesterId will check with the parameter, if there is any record, that will be deleted,
   *        as this is not a put operation.
   * @param pJsonObject the json objects.
   * @param pUriInfo
   * @return
   * @throws Exception
   */
  public Response createBySemester(Integer pSemesterId, JsonObject pJsonObject, UriInfo pUriInfo) {

    Integer record = getContentManager().checkBySemester(pSemesterId);

    if(record > 0) {
      getContentManager().deleteBySemester(pSemesterId);
    }

    List<MutableSeatPlanPublish> seatPlanPublishs = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentSeatPlanPublish publish = new PersistentSeatPlanPublish();
      getBuilder().build(publish, jsonObject, localCache);
      seatPlanPublishs.add(publish);
    }

    getContentManager().create(seatPlanPublishs);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response updateBySemester(Integer pSemesterId, JsonObject pJsonObject, UriInfo pUriInfo) {
    Integer record = getContentManager().checkBySemester(pSemesterId);

    List<MutableSeatPlanPublish> seatPlanPublishs = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentSeatPlanPublish publish = new PersistentSeatPlanPublish();
      getBuilder().build(publish, jsonObject, localCache);
      seatPlanPublishs.add(publish);
    }
    if(record > 2) {
      getContentManager().update(seatPlanPublishs);

    }
    else {
      getContentManager().deleteBySemester(pSemesterId);
      getContentManager().create(seatPlanPublishs);
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getBySemester(Integer pSemesterId, final Request pRequest, final UriInfo pUriInfo) {
    Integer record = getContentManager().checkBySemester(pSemesterId);
    List<SeatPlanPublish> publishs = new ArrayList<>();
    if(record > 0) {
      publishs = getContentManager().getBySemester(pSemesterId);

    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SeatPlanPublish publish : publishs) {
      children.add(toJson(publish, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected SeatPlanPublishManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<SeatPlanPublish, MutableSeatPlanPublish> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(SeatPlanPublish pReadonly) {
    return pReadonly.getExamDate();
  }
}
