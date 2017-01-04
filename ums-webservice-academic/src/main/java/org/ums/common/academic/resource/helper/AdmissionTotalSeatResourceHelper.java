package org.ums.common.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.common.builder.AdmissionTotalSeatBuilder;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    return null;
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
