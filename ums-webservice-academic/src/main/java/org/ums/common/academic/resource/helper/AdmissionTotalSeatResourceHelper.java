package org.ums.common.academic.resource.helper;

import org.ums.builder.Builder;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class AdmissionTotalSeatResourceHelper
    extends ResourceHelper<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer> {
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer> getContentManager() {
    return null;
  }

  @Override
  protected Builder<AdmissionTotalSeat, MutableAdmissionTotalSeat> getBuilder() {
    return null;
  }

  @Override
  protected String getEtag(AdmissionTotalSeat pReadonly) {
    return null;
  }
}
