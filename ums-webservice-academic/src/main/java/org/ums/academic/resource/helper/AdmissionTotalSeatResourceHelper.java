package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AdmissionTotalSeatBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.persistent.model.PersistentAdmissionTotalSeat;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
@Component
public class AdmissionTotalSeatResourceHelper extends
    ResourceHelper<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer> {

  private static final Logger mLogger = LoggerFactory
      .getLogger(AdmissionTotalSeatResourceHelper.class);

  @Autowired
  AdmissionTotalSeatManager mAdmissionTotalSeatManager;

  @Autowired
  AdmissionTotalSeatBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    String buildType = "new";
    return saveTotalSeatPlanData(pJsonObject, buildType);
  }

  private Response saveTotalSeatPlanData(JsonObject pJsonObject, String pBuildType) {
    List<MutableAdmissionTotalSeat> seats = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentAdmissionTotalSeat seat = new PersistentAdmissionTotalSeat();
      getBuilder().build(seat, jsonObject, localCache, pBuildType);
      seats.add(seat);
    }

    if(pBuildType.equals("new")) {
      getContentManager().create(seats);
    }
    else {
      getContentManager().update(seats);
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response put(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    String buildType = "update";
    return saveTotalSeatPlanData(pJsonObject, buildType);
  }

  public JsonObject getAdmissionTotalSeat(final int pSemesterId, final ProgramType pProgramType,
      QuotaType pQuotaType, final UriInfo pUriInfo) {

    List<AdmissionTotalSeat> seats;

    try {
      seats = getContentManager().getAdmissionTotalSeat(pSemesterId, pProgramType, pQuotaType);
    } catch(Exception e) {
      seats = new ArrayList<>();
      mLogger.warn("No data found in AdmissionTotalSeat");
    }

    return jsonCreator(pUriInfo, seats);
  }

  private JsonObject jsonCreator(UriInfo pUriInfo, List<AdmissionTotalSeat> pSeats) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionTotalSeat seat : pSeats) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, seat, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @Override
  protected AdmissionTotalSeatManager getContentManager() {
    return mAdmissionTotalSeatManager;
  }

  @Override
  protected AdmissionTotalSeatBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionTotalSeat pReadonly) {
    return pReadonly.getLastModified();
  }
}
