package org.ums.accounts.resource.definitions.group;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 26-Dec-17.
 */
@Component
public class GroupBuilder implements Builder<Group, MutableGroup> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Group pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("compCode", pReadOnly.getCompCode());
    pBuilder.add("groupCode", pReadOnly.getGroupCode());
    pBuilder.add("groupName", pReadOnly.getGroupName());
    pBuilder.add("mainGroup", pReadOnly.getMainGroup());
    pBuilder.add("flag", pReadOnly.getFlag());
    pBuilder.add("taxLimit", pReadOnly.getTaxLimit());
    pBuilder.add("tdsPercent", pReadOnly.getTdsPercent());
    pBuilder.add("defaultComp", pReadOnly.getDefaultComp());
    pBuilder.add("statusFlag", pReadOnly.getStatFlag());
    pBuilder.add("modifiedDate", UmsUtils.formatDate(pReadOnly.getModifiedDate(), "dd/MM/yyyy"));
    pBuilder.add("modifiedBy", pReadOnly.getModifiedBy());
  }

  @Override
  public void build(MutableGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
