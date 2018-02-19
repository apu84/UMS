package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.RecordManager;

import java.util.List;

/**
 * Created by Ifti on 04-Mar-17.
 */
public class ItemDaoDecorator extends ContentDaoDecorator<Item, MutableItem, Long, ItemManager> implements ItemManager {
  public List<Item> getByMfn(final Long pMfn) {
    return getManager().getByMfn(pMfn);
  }

  @Override
  public Item getByAccessionNumber(String pAccessionNumber) {
    return getManager().getByAccessionNumber(pAccessionNumber);
  }
}
