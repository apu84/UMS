package org.ums.accounts.resource.cheque.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.manager.ContentManager;
import org.ums.manager.accounts.ChequeRegisterManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Feb-18.
 */
@Component
public class ChequeRegisterResourceHelper extends ResourceHelper<ChequeRegister, MutableChequeRegister, Long> {

  @Autowired
  private ChequeRegisterManager mChequeRegisterManager;
  @Autowired
  private ChequeRegisterBuilder mChequeRegisterBuilder;

  public List<ChequeRegister> getChequeRegisterList(final List<Long> pTransactionIdList) {
    return new ArrayList<>(mChequeRegisterManager.getByTransactionIdList(pTransactionIdList));
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ChequeRegister, MutableChequeRegister, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<ChequeRegister, MutableChequeRegister> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(ChequeRegister pReadonly) {
    return null;
  }
}
