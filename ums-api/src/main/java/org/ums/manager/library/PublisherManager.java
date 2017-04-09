package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public interface PublisherManager extends ContentManager<Publisher, MutablePublisher, Integer> {
  List<Publisher> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pWhereClause,
      final String pOrder);

  int getTotalForPagination(final String pWhereClause);
}
