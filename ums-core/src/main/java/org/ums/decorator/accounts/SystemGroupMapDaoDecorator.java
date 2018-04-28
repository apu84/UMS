package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.manager.accounts.SystemGroupMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public class SystemGroupMapDaoDecorator extends
    ContentDaoDecorator<SystemGroupMap, MutableSystemGroupMap, String, SystemGroupMapManager> implements
    SystemGroupMapManager {
  @Override
  public List<SystemGroupMap> getAllByCompany(Company pCompany) {
    return getManager().getAllByCompany(pCompany);
  }
}
