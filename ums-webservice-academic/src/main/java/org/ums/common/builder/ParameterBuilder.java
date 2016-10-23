package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.mutable.MutableParameter;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ParameterBuilder implements Builder<Parameter, MutableParameter> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Parameter pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("parameter", pReadOnly.getParameter());
    pBuilder.add("shortDescription", pReadOnly.getShortDescription());
    pBuilder.add("longDescription", pReadOnly.getLongDescription());
    pBuilder.add("type", pReadOnly.getType());
    pBuilder.add(
        "self",
        pUriInfo.getBaseUriBuilder().path("academic").path("academicCalenderParameter")
            .path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableParameter pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {
    pMutable.setId(pJsonObject.getString("id"));
    pMutable.setParameter(pJsonObject.getString("parameter"));
    pMutable.setShortDescription(pJsonObject.getString("shortDescription"));
    pMutable.setLongDescription(pJsonObject.getString("longDescription"));
    pMutable.setType(Integer.parseInt(pJsonObject.getString("type")));
  }
}
