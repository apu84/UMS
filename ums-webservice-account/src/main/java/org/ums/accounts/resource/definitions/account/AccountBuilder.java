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
    if(pReadOnly.getId() != null)
      pBuilder.add("id", pReadOnly.getId().toString());
    if(pReadOnly.getRowNumber() != null)
      pBuilder.add("rowNum", pReadOnly.getRowNumber());
    if(pReadOnly.getAccountCode() != null)
      pBuilder.add("accountCode", pReadOnly.getAccountCode());
    if(pReadOnly.getAccountName() != null)
      pBuilder.add("accountName", pReadOnly.getAccountName());
    pBuilder.add("accGroupCode", pReadOnly.getAccGroupCode());
    if(pReadOnly.getReserved() != null)
      pBuilder.add("reserved", pReadOnly.getReserved());
    if(pReadOnly.getTaxLimit() != null)
      pBuilder.add("taxLimit", pReadOnly.getTaxLimit());
    if(pReadOnly.getTaxCode() != null)
      pBuilder.add("taxCode", pReadOnly.getTaxCode());
    if(pReadOnly.getStatFlag() != null)
      pBuilder.add("statFlag", pReadOnly.getStatFlag());
    if(pReadOnly.getStatUpFlag() != null)
      pBuilder.add("statUpFlag", pReadOnly.getStatUpFlag());
    if(pReadOnly.getModifiedDate() != null)
      pBuilder.add("modifiedDate", UmsUtils.formatDate(pReadOnly.getModifiedDate(), "dd-MM-yyyy"));
    if(pReadOnly.getModifiedBy() != null)
      pBuilder.add("modifiedBy", pReadOnly.getModifiedBy());
  }

  @Override
  public void build(MutableAccount pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("accountCode"))
      pMutable.setAccountCode(Long.parseLong(pJsonObject.getString("accountCode")));
    if(pJsonObject.containsKey("accountName"))
      pMutable.setAccountName(pJsonObject.getString("accountName"));
    if(pJsonObject.containsKey("accGroupCode"))
      pMutable.setAccGroupCode(pJsonObject.getString("accGroupCode"));
  }
}
