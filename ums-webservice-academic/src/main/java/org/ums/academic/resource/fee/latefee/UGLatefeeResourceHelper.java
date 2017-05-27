package org.ums.academic.resource.fee.latefee;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.latefee.MutableLateFee;
import org.ums.fee.latefee.PersistentLateFee;
import org.ums.fee.latefee.LateFee;
import org.ums.fee.latefee.LateFeeManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import java.util.List;

@Component
public class UGLatefeeResourceHelper extends ResourceHelper<LateFee, MutableLateFee, Long> {
  @Autowired
  private LateFeeManager mLateFeeManager;
  @Autowired
  private UGLatefeeBuilder mUGLatefeeBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject);
    Validate.notNull(pJsonObject.getJsonObject("entries"));
    List<MutableLateFee> mutableUGLateFees = readEntities(pJsonObject, PersistentLateFee.class);
    mLateFeeManager.create(mutableUGLateFees);
    return Response.ok().build();
  }

  JsonObject getLatefees(Integer pSemesterId, UriInfo pUriInfo) {
    List<LateFee> fees = mLateFeeManager.getLateFees(pSemesterId);
    LocalCache localCache = new LocalCache();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for(LateFee fee : fees) {
      arrayBuilder.add(toJson(fee, pUriInfo, localCache));
    }
    JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("entries", arrayBuilder);
    return builder.build();
  }

  Response updateLatefees(Integer pSemesterId, JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject);
    Validate.notNull(pJsonObject.getJsonObject("entries"));
    List<MutableLateFee> mutableUGLateFees = readEntities(pJsonObject, PersistentLateFee.class);
    // Validate first
    List<LateFee> fees = mLateFeeManager.getLateFees(pSemesterId);
    if(!isValidUpdateOfEntities(fees, mutableUGLateFees)) {
      return Response.status(Response.Status.PRECONDITION_FAILED).build();
    }
    mLateFeeManager.update(mutableUGLateFees);
    return Response.ok().build();
  }

  @Override
  protected ContentManager<LateFee, MutableLateFee, Long> getContentManager() {
    return mLateFeeManager;
  }

  @Override
  protected Builder<LateFee, MutableLateFee> getBuilder() {
    return mUGLatefeeBuilder;
  }

  @Override
  protected String getETag(LateFee pReadonly) {
    return pReadonly.getLastModified();
  }
}
