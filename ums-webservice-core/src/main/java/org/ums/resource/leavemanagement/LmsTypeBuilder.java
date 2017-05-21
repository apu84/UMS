package org.ums.resource.leavemanagement;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
@Component
public class LmsTypeBuilder implements Builder<LmsType, MutableLmsType> {
  @Override
  public void build(JsonObjectBuilder pBuilder, LmsType pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    if(pReadOnly.getDuration() != 0)
      pBuilder.add("duration", pReadOnly.getDuration());
    if(pReadOnly.getDurationType() != null) {
      pBuilder.add("durationType", pReadOnly.getDurationType().getId());
      pBuilder.add("durationLabel", pReadOnly.getDurationType().getLabel());
    }
    if(pReadOnly.getMaxDuration() != 0)
      pBuilder.add("maxDuration", pReadOnly.getMaxDuration());
    if(pReadOnly.getMaxSimultaneousDuration() != 0)
      pBuilder.add("maxSimultaneousDuration", pReadOnly.getMaxSimultaneousDuration());
    if(pReadOnly.getSalaryType() != null) {
      pBuilder.add("salaryType", pReadOnly.getSalaryType().getId());
      pBuilder.add("salaryTypeLabel", pReadOnly.getSalaryType().getLabel());
    }

  }

  @Override
  public void build(MutableLmsType pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
