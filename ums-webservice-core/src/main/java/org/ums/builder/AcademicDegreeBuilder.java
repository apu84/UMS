package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicDegreeBuilder implements Builder<AcademicDegree, MutableAcademicDegree> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicDegree pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("type", pReadOnly.getDegreeType());
    if(pReadOnly.getDegreeType() == 11) {
      pBuilder.add("typeName", "SSC");
    }
    else if(pReadOnly.getDegreeType() == 22) {
      pBuilder.add("typeName", "HSC");
    }
    else if(pReadOnly.getDegreeType() == 101) {
      pBuilder.add("typeName", "Bachelor");
    }
    else if(pReadOnly.getDegreeType() == 1001) {
      pBuilder.add("typeName", "Masters");
    }
    else if(pReadOnly.getDegreeType() == 2001) {
      pBuilder.add("typeName", "Phd");
    }
    pBuilder.add("name", pReadOnly.getDegreeName());
    pBuilder.add("shortName", pReadOnly.getDegreeShortName());
  }

  @Override
  public void build(MutableAcademicDegree pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
