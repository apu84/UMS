package org.ums.accounts.resource.definitions.voucher.number.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.manager.accounts.VoucherNumberControlManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
@Component
public class VoucherNumberControlResourceHelper extends
    ResourceHelper<VoucherNumberControl, MutableVoucherNumberControl, Long> {
  @Autowired
  private VoucherNumberControlManager mVoucherNumberControlManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  protected List<VoucherNumberControl> createOrUpdate(List<MutableVoucherNumberControl> pVoucherNumberControls) {
    List<MutableVoucherNumberControl> newVoucherNumberControls = new ArrayList<>();
    List<MutableVoucherNumberControl> voucherNumberControlsForUpdate = new ArrayList<>();
    pVoucherNumberControls.forEach(v -> {
      if (v.getId() != null)
        newVoucherNumberControls.add(v);
      else
        voucherNumberControlsForUpdate.add(v);
    });

    if (newVoucherNumberControls.size() > 0)
      getContentManager().create(newVoucherNumberControls);
    if (voucherNumberControlsForUpdate.size() > 0)
      getContentManager().update(voucherNumberControlsForUpdate);

    return getContentManager().getByCurrentFinancialYear();
  }

  @Override
  protected VoucherNumberControlManager getContentManager() {
    return mVoucherNumberControlManager;
  }

  @Override
  protected Builder<VoucherNumberControl, MutableVoucherNumberControl> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(VoucherNumberControl pReadonly) {
    return pReadonly.getLastModified().toString();
  }
}
