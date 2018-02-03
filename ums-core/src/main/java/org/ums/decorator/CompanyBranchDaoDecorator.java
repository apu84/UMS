package org.ums.decorator;

import org.ums.domain.model.immutable.CompanyBranch;
import org.ums.domain.model.mutable.MutableCompanyBranch;
import org.ums.manager.CompanyBranchManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class CompanyBranchDaoDecorator extends
    ContentDaoDecorator<CompanyBranch, MutableCompanyBranch, Long, CompanyBranchManager> implements
    CompanyBranchManager {
}
