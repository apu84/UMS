package org.ums.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.RelationTypeBuilder;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;
import org.ums.manager.ContentManager;
import org.ums.manager.common.RelationTypeManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class RelationTypeResourceHelper extends ResourceHelper<RelationType, MutableRelationType, Integer> {

  @Autowired
  private RelationTypeManager mManager;

  @Autowired
  private RelationTypeBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<RelationType, MutableRelationType, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<RelationType, MutableRelationType> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(RelationType pReadonly) {
    return pReadonly.getLastModified();
  }
}
