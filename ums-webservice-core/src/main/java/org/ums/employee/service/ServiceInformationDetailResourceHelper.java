package org.ums.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ServiceInformationDetailResourceHelper extends
    ResourceHelper<ServiceInformationDetail, MutableServiceInformationDetail, Long> {

  private static final Logger mLogger = LoggerFactory.getLogger(ServiceInformationResourceHelper.class);

  @Autowired
  private ServiceInformationDetailManager mManager;

  @Autowired
  private ServiceInformationDetailBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ServiceInformationDetail, MutableServiceInformationDetail> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ServiceInformationDetail pReadonly) {
    return pReadonly.getLastModified();
  }
}
