package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.ContributorManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class ContributorDaoDecorator extends
    ContentDaoDecorator<Contributor, MutableContributor, Integer, ContributorManager> implements
    ContributorManager {

}
