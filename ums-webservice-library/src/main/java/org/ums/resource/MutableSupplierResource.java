package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutableSupplier;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutableSupplierResource extends Resource {
  @Autowired
  ResourceHelper<Supplier, MutableSupplier, Integer> mResourceHelper;

}
