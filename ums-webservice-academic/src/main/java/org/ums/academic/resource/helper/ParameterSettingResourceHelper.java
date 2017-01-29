package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.ParameterSettingResource;
import org.ums.builder.ParameterSettingBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.StudentManager;
import org.ums.persistent.model.PersistentParameterSetting;
import org.ums.resource.ResourceHelper;

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
public class ParameterSettingResourceHelper extends
    ResourceHelper<ParameterSetting, MutableParameterSetting, String> {

  @Autowired
  private ParameterSettingManager mManager;

  @Autowired
  private StudentManager mStudentManager;

  @Autowired
  private ParameterSettingBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableParameterSetting mutableParameterSetting = new PersistentParameterSetting();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableParameterSetting, pJsonObject, localCache);
    mutableParameterSetting.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(ParameterSettingResource.class)
            .path(ParameterSettingResource.class, "get").build("0");
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getBySemester(final int pSemesterId, final Request pRequest,
      final UriInfo pUriInfo) {
    List<ParameterSetting> parameterSettings = getContentManager().getBySemester(pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ParameterSetting parameters : parameterSettings) {
      children.add(toJson(parameters, pUriInfo, localCache));
    }

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getByParameterIdAndSemesterId(final int pParameterId, final int pSemesterId,
      final Request pRequest, final UriInfo pUriInfo) {
    ParameterSetting parameterSettings =
        getContentManager().getBySemesterAndParameterId(pParameterId, pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    children.add(toJson(parameterSettings, pUriInfo, localCache));

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getByParameterAndSemesterId(final String parameter, final Request pRequest,
      final UriInfo pUriInfo) {
    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(mStudentId);
    ParameterSetting parameterSettings =
        getContentManager().getByParameterAndSemesterId(parameter, student.getSemester().getId());
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    children.add(toJson(parameterSettings, pUriInfo, localCache));

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getAllInfo(final UriInfo pUriInfo) {
    List<ParameterSetting> parameterSettings = getContentManager().getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ParameterSetting parameters : parameterSettings) {
      children.add(toJson(parameters, pUriInfo, localCache));
    }

    object.add("entries", children);
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
  public String getETag(ParameterSetting pReadonly) {
    return pReadonly.getLastModified();
  }
}
