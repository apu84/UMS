package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.library.ContributorManager;
import org.ums.manager.library.PublisherManager;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class ContributorBuilder implements Builder<Contributor, MutableContributor> {

  @Autowired
  ContributorManager mContributorManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Contributor pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("first_name", pReadOnly.getFirstName());
    pBuilder.add("middle_name", pReadOnly.getMiddleName());
    pBuilder.add("last_name", pReadOnly.getLastName());
    pBuilder.add("text", pReadOnly.getFirstName() + ' ' + pReadOnly.getMiddleName() + ' ' + pReadOnly.getLastName());
    pBuilder.add("name", pReadOnly.getFirstName() + ' ' + pReadOnly.getMiddleName() + ' ' + pReadOnly.getLastName());
    // pBuilder.add("countryId", pReadOnly.getCountryId());
    // pBuilder.add("countryName", mContributorManager.get(pReadOnly.getCountryId()).getName());
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));

  }

  @Override
  public void build(final MutableContributor pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    // pMutable.setId(pJsonObject.getInt("id"));
    // pMutable.setName(pJsonObject.getString("name"));
    // pMutable.setCountryId(pJsonObject.getInt("countryId"));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
