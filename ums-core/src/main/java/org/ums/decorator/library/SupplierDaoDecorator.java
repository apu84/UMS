package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.library.AuthorManager;
import org.ums.manager.library.SupplierManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class SupplierDaoDecorator extends ContentDaoDecorator<Supplier, MutableSupplier, Long, SupplierManager>
    implements SupplierManager {

  @Override
  public List<Supplier> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pWhereClause,
      final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    return getManager().getTotalForPagination(pWhereClause);
  }

}
