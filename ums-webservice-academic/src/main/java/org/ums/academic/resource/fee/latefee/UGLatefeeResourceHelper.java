package org.ums.academic.resource.fee.latefee;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
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
  UGLateFeeManager mUGLateFeeManager;
  @Autowired
  UGLatefeeBuilder mUGLatefeeBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject);
    Validate.notNull(pJsonObject.getJsonObject("entries"));
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    List<MutableUGLateFee> mutableUGLateFees = new ArrayList<>();
    for(JsonValue value : entries) {
      JsonObject entry = (JsonObject) value;
      MutableUGLateFee mutable = new PersistentUGLateFee();
      getBuilder().build(mutable, entry, localCache);
      mutableUGLateFees.add(mutable);
    }
    mUGLateFeeManager.create(mutableUGLateFees);
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
