package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.enums.common.Gender;
import org.ums.manager.common.CountryManager;
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

  @Autowired
  CountryManager mCountryManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Contributor pReadOnly, UriInfo pUriInfo,
                    final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("name", pReadOnly.getFullName());
    pBuilder.add("text", pReadOnly.getFullName());
    pBuilder.add("shortName", pReadOnly.getShortName() == null ? "" : pReadOnly.getShortName());
    pBuilder.add("gender", pReadOnly.getGender() == null ? 101101 : pReadOnly.getGender().getId());
    pBuilder.add("countryId", pReadOnly.getCountryId());
    pBuilder.add("countryName", pReadOnly.getCountryId() == 0 ? "" : mCountryManager.get(pReadOnly.getCountryId())
        .getName());
    pBuilder.add("address", pReadOnly.getAddress() == null ? "" : pReadOnly.getAddress());
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));
  }

  @Override
  public void build(final MutableContributor pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    if (pJsonObject.containsKey("id"))
      pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
    pMutable.setFullName(pJsonObject.getString("name"));
    if (pJsonObject.containsKey("shortName"))
      pMutable.setShortName(pJsonObject.getString("shortName"));
    if (pJsonObject.containsKey("gender"))
      pMutable.setGender(Gender.get(pJsonObject.getInt("gender")));
    if (pJsonObject.containsKey("address"))
      pMutable.setAddress(pJsonObject.getString("address"));
    if (pJsonObject.containsKey("countryId"))
      pMutable.setCountryId(pJsonObject.getInt("countryId"));

    // pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
