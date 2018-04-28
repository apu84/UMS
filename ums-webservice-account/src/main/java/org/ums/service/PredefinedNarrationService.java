package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.manager.accounts.PredefinedNarrationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 10-Apr-18.
 */
@Service
public class PredefinedNarrationService {

  @Autowired
  private PredefinedNarrationManager mManager;

  public Map<Voucher, String> getVoucherNarrationMap() {
    List<PredefinedNarration> narrationList = mManager.getAll();
    Map<Voucher, String> voucherPredefinedNarrationMap = new HashMap<>();
    for(PredefinedNarration narration : narrationList) {
      voucherPredefinedNarrationMap.put(narration.getVoucher(),
          narration.getNarration() == null ? "" : narration.getNarration());
    }
    return voucherPredefinedNarrationMap;
  }
}
