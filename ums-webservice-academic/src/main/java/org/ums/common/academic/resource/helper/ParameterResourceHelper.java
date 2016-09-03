package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ParameterResource;
import org.ums.common.builder.ParameterBuilder;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.mutable.MutableParameter;
import org.ums.manager.ContentManager;
import org.ums.manager.ParameterManager;
import org.ums.persistent.model.PersistentParameter;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by My Pc on 3/13/2016.
 */
@Component
public class ParameterResourceHelper extends ResourceHelper<Parameter,MutableParameter,String> {

  @Autowired
  private ParameterManager mManager;

  @Autowired
  private ParameterBuilder mBuilder;

  @Override
  public  Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableParameter mutableParameter = new PersistentParameter();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableParameter,pJsonObject,localCache);
    mutableParameter.commit(false);
    URI contextURI = pUriInfo.getBaseUriBuilder().path(ParameterResource.class,"get").build(mutableParameter.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }



  @Override
  public ContentManager<Parameter, MutableParameter, String> getContentManager() {
    return mManager;
  }

  @Override
  public Builder<Parameter, MutableParameter> getBuilder() {
    return mBuilder;
  }

  @Override
  public String getEtag(Parameter pReadonly) {
    return null;
  }
}
