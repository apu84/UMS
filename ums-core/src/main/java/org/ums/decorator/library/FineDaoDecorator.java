package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.library.FineManager;

import java.util.List;

public class FineDaoDecorator extends ContentDaoDecorator<Fine, MutableFine, Long, FineManager> implements FineManager {
  @Override
  public List<Fine> getFines(String pPatronId) {
    return getManager().getFines(pPatronId);
  }

  @Override
  public int saveFine(MutableFine pMutableFine) {
    return getManager().saveFine(pMutableFine);
  }

  @Override
  public int updateFine(MutableFine pMutableFine) {
    return getManager().updateFine(pMutableFine);
  }

  @Override
  public Fine getFine(Long pCirculationId) {
    return getManager().getFine(pCirculationId);
  }
}
