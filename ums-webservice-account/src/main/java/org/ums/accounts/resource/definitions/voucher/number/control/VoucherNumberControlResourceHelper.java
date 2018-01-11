package org.ums.accounts.resource.definitions.voucher.number.control;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.manager.accounts.VoucherNumberControlManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
@Component
public class VoucherNumberControlResourceHelper extends
    ResourceHelper<VoucherNumberControl, MutableVoucherNumberControl, Long> {
  @Autowired
  private VoucherNumberControlManager mVoucherNumberControlManager;
  @Autowired
  private FinancialAccountYearManager financialAccountYearManager;
  @Autowired
  private UserManager userManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  protected List<VoucherNumberControl> createOrUpdate(List<MutableVoucherNumberControl> pVoucherNumberControls) {
    List<MutableVoucherNumberControl> newVoucherNumberControls = new ArrayList<>();
    List<MutableVoucherNumberControl> voucherNumberControlsForUpdate = new ArrayList<>();
    User user = userManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    pVoucherNumberControls.forEach(v -> {
      v.setModifiedBy(user.getEmployeeId());
      v.setModifiedDate(new Date());
      if (v.getId() == null) {
        FinancialAccountYear financialAccountYear = financialAccountYearManager.getOpenedFinancialAccountYear();
        v.setFinAccountYearId(financialAccountYear.getId());
        newVoucherNumberControls.add(v);
      } else
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
