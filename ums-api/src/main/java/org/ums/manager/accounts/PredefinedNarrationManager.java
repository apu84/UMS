package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.manager.ContentManager;

import java.util.List;

public interface PredefinedNarrationManager extends
    ContentManager<PredefinedNarration, MutablePredefinedNarration, Long> {
  List<PredefinedNarration> getAll(Company pCompany);
}
