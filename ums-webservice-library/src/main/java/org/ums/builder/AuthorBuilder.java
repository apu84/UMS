package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.DepartmentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 30-Jan-17.
 */

@Component
public class AuthorBuilder implements Builder<Author, MutableAuthor> {

  @Autowired
  DepartmentManager mDepartmentManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Author pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("firstName", pReadOnly.getFirstName());
    pBuilder.add("middleName", pReadOnly.getMiddleName());
    pBuilder.add("lastName", pReadOnly.getLastName());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("address", pReadOnly.getAddress());
    pBuilder.add("countryId", pReadOnly.getCountryId());

  }

  @Override
  public void build(final MutableAuthor pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    pMutable.setId(pJsonObject.getInt("Id"));
    pMutable.setFirstName(pJsonObject.getString("firstName"));
    pMutable.setMiddleName(pJsonObject.getString("middleName"));
    pMutable.setLastName(pJsonObject.getString("lastName"));
    pMutable.setShortName(pJsonObject.getString("shortName"));
    pMutable.setGender(pJsonObject.getString("gender"));
    pMutable.setAddress(pJsonObject.getString("address"));
    pMutable.setCountryId(pJsonObject.getInt("countryId"));
  }
}
