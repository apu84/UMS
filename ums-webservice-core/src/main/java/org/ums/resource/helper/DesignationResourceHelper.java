package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.DesignationBuilder;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;
import org.ums.manager.ContentManager;
import org.ums.manager.DesignationManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DesignationResourceHelper extends ResourceHelper<Designation, MutableDesignation, Integer> {
  @Autowired
  private DesignationManager mManager;

  @Autowired
  private DesignationBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Designation, MutableDesignation, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Designation, MutableDesignation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Designation pReadonly) {
    return "";
  }
}
