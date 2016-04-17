package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ParameterSettingResource;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.ParameterSettingBuilder;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.ContentManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.persistent.model.PersistentParameterSetting;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by My Pc on 3/15/2016.
 */
@Component
public class ParameterSettingResourceHelper extends ResourceHelper<ParameterSetting,MutableParameterSetting,String> {


  @Autowired
  private ParameterSettingManager mManager;

  @Autowired
  private ParameterSettingBuilder mBuilder;


  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception
  {
    MutableParameterSetting mutableParameterSetting = new PersistentParameterSetting();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableParameterSetting,pJsonObject,localCache);
    mutableParameterSetting.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(ParameterSettingResource.class).path(ParameterSettingResource.class,"get").build("0");
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getBySemester(final int pSemesterId, final Request pRequest, final UriInfo pUriInfo) throws Exception{
    List<ParameterSetting> parameterSettings = getContentManager().getBySemester(pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ParameterSetting parameters: parameterSettings){
      children.add(toJson(parameters,pUriInfo,localCache));
    }

    object.add("entries",children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getByParameterAndSemesterId(final int pParameterId,final int pSemesterId, final Request pRequest, final UriInfo pUriInfo) throws Exception{
    ParameterSetting parameterSettings = getContentManager().getBySemesterAndParameterId(pParameterId,pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    children.add(toJson(parameterSettings,pUriInfo,localCache));

    object.add("entries",children);
    localCache.invalidate();

    return object.build();
  }


  public JsonObject getAllInfo(final UriInfo pUriInfo) throws Exception{
    List<ParameterSetting> parameterSettings = getContentManager().getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ParameterSetting parameters: parameterSettings){
      children.add(toJson(parameters,pUriInfo,localCache));
    }

    object.add("entries",children);
    localCache.invalidate();

    return object.build();
  }


  @Override
  public ParameterSettingManager getContentManager() {
    return mManager;
  }

  @Override
  public ParameterSettingBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public String getEtag(ParameterSetting pReadonly) {
    return pReadonly.getLastModified();
  }
}
