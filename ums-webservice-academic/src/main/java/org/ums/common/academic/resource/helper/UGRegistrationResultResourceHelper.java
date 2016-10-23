package org.ums.common.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.builder.UGRegistrationResultBuilder;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.manager.StudentManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by My Pc on 7/13/2016.
 */

@Component
public class UGRegistrationResultResourceHelper extends
    ResourceHelper<UGRegistrationResult, MutableUGRegistrationResult, Integer> {

  @Autowired
  UGRegistrationResultManager mManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  UGRegistrationResultBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getResultForCarryClearanceAndImprovement(final Request pRequest,
      final UriInfo pUriInfo) throws Exception {

    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(mStudentId);
    List<UGRegistrationResult> results =
        mManager.getCarryClearanceImprovementCoursesByStudent(student.getSemesterId(),
            student.getId());
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(UGRegistrationResult result : results) {
      children.add(toJson(result, pUriInfo, localCache));

    }

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public JsonObject getResultForApplicationCCIOfCarryClearanceAndImprovement(
      List<UGRegistrationResult> results, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(UGRegistrationResult result : results) {
      children.add(toJson(result, pUriInfo, localCache));

    }

    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @Override
  protected UGRegistrationResultManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<UGRegistrationResult, MutableUGRegistrationResult> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(UGRegistrationResult pReadonly) {
    return "1";
  }
}
