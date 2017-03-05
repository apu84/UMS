package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Ifti on 04-Mar-17.
 */
public interface ItemManager extends ContentManager<Item, MutableItem, Long> {
  List<Item> getByMfn(final Long pMfn);
}
