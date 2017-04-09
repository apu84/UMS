package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface ContributorManager extends ContentManager<Contributor, MutableContributor, Long> {
  List<Contributor> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pWhereClause,
      final String pOrder);

  int getTotalForPagination(final String pWhereClause);
}
