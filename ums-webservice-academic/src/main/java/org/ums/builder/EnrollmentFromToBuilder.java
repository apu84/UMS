package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.immutable.EnrollmentFromTo;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EnrollmentFromToBuilder implements Builder<EnrollmentFromTo, MutableEnrollmentFromTo> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EnrollmentFromTo pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("year", pReadOnly.getToYear());
    pBuilder.add("semester", pReadOnly.getToSemester());
  }

  @Override
  public void build(MutableEnrollmentFromTo pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
