package org.ums.accounts.resource.cheque.register;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 26-Feb-18.
 */
@Component
public class ChequeRegisterBuilder implements Builder<ChequeRegister, MutableChequeRegister> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ChequeRegister pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableChequeRegister pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
