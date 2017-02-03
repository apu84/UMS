package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.library.AuthorManager;
import org.ums.manager.library.SupplierManager;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class SupplierDaoDecorator extends
    ContentDaoDecorator<Supplier, MutableSupplier, Integer, SupplierManager> implements
    SupplierManager {

}
