package org.ums.academic.resource.fee.latefee;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.glassfish.jersey.server.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.latefee.MutableUGLateFee;
import org.ums.fee.latefee.PersistentUGLateFee;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

@Component
public class UGLatefeeResourceHelper extends ResourceHelper<UGLateFee, MutableUGLateFee, Long> {
  @Autowired
  private UGLateFeeManager mUGLateFeeManager;
  @Autowired
  private UGLatefeeBuilder mUGLatefeeBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject);
    Validate.notNull(pJsonObject.getJsonObject("entries"));
    List<MutableUGLateFee> mutableUGLateFees = readEntities(pJsonObject, PersistentUGLateFee.class);
    mUGLateFeeManager.create(mutableUGLateFees);
    return Response.ok().build();
  }

  JsonObject getLatefees(Integer pSemesterId, UriInfo pUriInfo) {
    List<UGLateFee> fees = mUGLateFeeManager.getLateFees(pSemesterId);
    LocalCache localCache = new LocalCache();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for(UGLateFee fee : fees) {
      arrayBuilder.add(toJson(fee, pUriInfo, localCache));
    }
    JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("entries", arrayBuilder);
    return builder.build();
  }

  Response updateLatefees(Integer pSemesterId, JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject);
    Validate.notNull(pJsonObject.getJsonObject("entries"));
    List<MutableUGLateFee> mutableUGLateFees = readEntities(pJsonObject, PersistentUGLateFee.class);

    // Validate first
    List<UGLateFee> fees = mUGLateFeeManager.getLateFees(pSemesterId);
    for(UGLateFee fee : fees) {
      for(UGLateFee mutableFee : mutableUGLateFees) {
        if(fee.getAdmissionType() == mutableFee.getAdmissionType() && !hasUpdatedVersion(fee, mutableFee)) {
          return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
      }
    }

    mUGLateFeeManager.update(mutableUGLateFees);
    return Response.ok().build();
  }

  @Override
  protected ContentManager<UGLateFee, MutableUGLateFee, Long> getContentManager() {
    return mUGLateFeeManager;
  }

  @Override
  protected Builder<UGLateFee, MutableUGLateFee> getBuilder() {
    return mUGLatefeeBuilder;
  }

  @Override
  protected String getETag(UGLateFee pReadonly) {
    return pReadonly.getLastModified();
  }
}
