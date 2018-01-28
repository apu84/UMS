package org.ums.decorator;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.manager.CompanyManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class CompanyDaoDecorator extends ContentDaoDecorator<Company, MutableCompany, Long, CompanyManager> implements
    CompanyManager {
}
