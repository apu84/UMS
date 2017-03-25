package org.ums.academic.resource.latefee;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.fee.latefee.MutableUGLateFee;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

@Component
public class UGLatefeeResourceHelper extends ResourceHelper<UGLateFee, MutableUGLateFee, Long> {
  @Autowired
  UGLateFeeManager mUGLateFeeManager;
  @Autowired
  UGLatefeeBuilder mUGLatefeeBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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
