package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.ContributorManager;

import java.util.List;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class ContributorDaoDecorator extends
    ContentDaoDecorator<Contributor, MutableContributor, Long, ContributorManager> implements ContributorManager {
  @Override
  public List<Contributor> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pWhereClause, final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    return getManager().getTotalForPagination(pWhereClause);
  }
}
