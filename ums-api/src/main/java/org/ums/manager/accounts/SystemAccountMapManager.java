package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public interface SystemAccountMapManager extends ContentManager<SystemAccountMap, MutableSystemAccountMap, Long> {
  List<SystemAccountMap> getAll(Company pCompany);
}
