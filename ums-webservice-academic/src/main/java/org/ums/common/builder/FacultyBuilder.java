package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.mutable.MutableFaculty;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 07-Dec-16.
 */
@Component
public class FacultyBuilder implements Builder<Faculty, MutableFaculty> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Faculty pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("longName", pReadOnly.getLongName());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add(
        "self",
        pUriInfo.getBaseUriBuilder().path("academic").path("academic").path("faculty")
            .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableFaculty pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
