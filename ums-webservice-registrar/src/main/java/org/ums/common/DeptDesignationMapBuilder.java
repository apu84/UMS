package org.ums.common;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DeptDesignationMapBuilder implements Builder<DeptDesignationMap, MutableDeptDesignationMap> {
  @Override
  public void build(JsonObjectBuilder pBuilder, DeptDesignationMap pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableDeptDesignationMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
