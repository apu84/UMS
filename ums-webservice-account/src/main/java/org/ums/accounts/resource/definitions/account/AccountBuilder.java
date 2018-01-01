package org.ums.accounts.resource.definitions.account;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 01-Jan-18.
 */
@Component
public class AccountBuilder implements Builder<Account, MutableAccount> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Account pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("accountCode", pReadOnly.getAccountCode());
    pBuilder.add("accountName", pReadOnly.getAccountName());
    pBuilder.add("accGroupCode", pReadOnly.getAccGroupCode());
    pBuilder.add("reserved", pReadOnly.getReserved());
    pBuilder.add("taxLimit", pReadOnly.getTaxLimit());
    pBuilder.add("taxCode", pReadOnly.getTaxCode());
    pBuilder.add("statFlag", pReadOnly.getStatFlag());
    pBuilder.add("statUpFlag", pReadOnly.getStatUpFlag());
    pBuilder.add("modifiedDate", UmsUtils.formatDate(pReadOnly.getModifiedDate(), "dd-MM-yyyy"));
    pBuilder.add("modifiedBy", pReadOnly.getModifiedBy());
  }

  @Override
  public void build(MutableAccount pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
