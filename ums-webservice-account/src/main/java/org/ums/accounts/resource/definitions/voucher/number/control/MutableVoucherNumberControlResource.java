package org.ums.accounts.resource.definitions.voucher.number.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 10-Jan-18.
 */
public class MutableVoucherNumberControlResource {
  @Autowired
  protected VoucherNumberControlResourceHelper mHelper;

  @POST
  @Path("/save")
  public List<VoucherNumberControl> saveAndReturn(List<MutableVoucherNumberControl> pVoucherNumberControls) {
    return mHelper.createOrUpdate(pVoucherNumberControls);
  }

}
