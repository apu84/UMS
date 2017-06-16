package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.EmploymentTypeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.MutableEmploymentType;
import org.ums.manager.ContentManager;
import org.ums.manager.EmploymentTypeManager;
import org.ums.persistent.model.PersistentEmploymentType;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmploymentTypeResourceHelper extends ResourceHelper<EmploymentType, MutableEmploymentType, Integer> {

  @Autowired
  EmploymentTypeManager mManager;

  @Autowired
  EmploymentTypeBuilder mBuilder;

  public JsonObject getEmploymentTypes(final UriInfo pUriInfo) {
    List<EmploymentType> employmentType = new ArrayList<>();
    employmentType = mManager.getAll();
    return buildJsonResponse(employmentType, pUriInfo);
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<EmploymentType, MutableEmploymentType, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<EmploymentType, MutableEmploymentType> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(EmploymentType pReadonly) {
    return null;
  }
}
