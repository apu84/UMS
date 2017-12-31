package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.library.CirculationManager;

import java.util.List;

public class CirculationDaoDecorator extends
    ContentDaoDecorator<Circulation, MutableCirculation, Long, CirculationManager> implements CirculationManager {
  @Override
  public int saveCheckout(MutableCirculation pMutableCirculation) {
    return getManager().saveCheckout(pMutableCirculation);
  }

  @Override
  public List<Circulation> getCirculation(String pUserId) {
    return getManager().getCirculation(pUserId);
  }

  @Override
  public int updateCirculation(MutableCirculation pMutableCirculation) {
    return getManager().updateCirculation(pMutableCirculation);
  }

  @Override
  public int batchUpdateCirculation(List<MutableCirculation> pMutable) {
    return getManager().batchUpdateCirculation(pMutable);
  }

  @Override
  public List<Circulation> getAllCirculation(String pPatronId) {
    return getManager().getAllCirculation(pPatronId);
  }

  @Override
  public List<Circulation> getCirculationCheckedInItems(String pPatronId) {
    return getManager().getCirculationCheckedInItems(pPatronId);
  }
}
