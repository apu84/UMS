package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.enums.common.DegreeLevel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DegreeTitleBuilder implements Builder<DegreeTitle, MutableDegreeTitle> {

  private DegreeLevel mDegreeLevel;

  @Override
  public void build(JsonObjectBuilder pBuilder, DegreeTitle pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("title", pReadOnly.getTitle());
    JsonObjectBuilder degreeLevelBuilder = Json.createObjectBuilder();
    degreeLevelBuilder.add("id", pReadOnly.getDegreeLevelId()).add("name",
        mDegreeLevel.get(pReadOnly.getDegreeLevelId()).getLabel());
    pBuilder.add("degreeLevel", degreeLevelBuilder);
    pBuilder.add("degreeLevelId", pReadOnly.getDegreeLevelId());
  }

  @Override
  public void build(MutableDegreeTitle pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setTitle(pJsonObject.getString("entries"));
    pMutable.setDegreeLevelId(DegreeLevel.PhD.getId());
  }
}
