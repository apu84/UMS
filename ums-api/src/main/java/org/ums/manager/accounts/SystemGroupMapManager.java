package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public interface SystemGroupMapManager extends ContentManager<SystemGroupMap, MutableSystemGroupMap, String> {
  List<SystemGroupMap> getAllByCompany(Company pCompany);
}
