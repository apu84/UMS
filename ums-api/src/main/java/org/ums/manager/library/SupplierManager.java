package org.ums.manager.library;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public interface SupplierManager extends ContentManager<Supplier, MutableSupplier, Long> {
  List<Supplier> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pOrder);

  int getTotalForPagination();
}
