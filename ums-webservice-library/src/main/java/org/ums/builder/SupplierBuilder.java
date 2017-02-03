package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.DepartmentManager;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class SupplierBuilder implements Builder<Supplier, MutableSupplier> {

  @Autowired
  DepartmentManager mDepartmentManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Supplier pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("address", UmsUtils.nullConversion(pReadOnly.getAddress()));
    pBuilder.add("contactPerson", UmsUtils.nullConversion(pReadOnly.getContactPerson()));
    pBuilder.add("contactNumber", UmsUtils.nullConversion(pReadOnly.getContactNumber()));
    pBuilder.add("lastModified", UmsUtils.nullConversion(pReadOnly.getLastModified()));

  }

  @Override
  public void build(final MutableSupplier pMutable, final JsonObject pJsonObject,
      final LocalCache pLocalCache) {

    pMutable.setId(pJsonObject.getInt("id"));
    pMutable.setName(pJsonObject.getString("name"));
    pMutable.setAddress(pJsonObject.getString("address"));
    pMutable.setContactPerson(pJsonObject.getString("contactPerson"));
    pMutable.setContactNumber(pJsonObject.getString("contactNumber"));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
