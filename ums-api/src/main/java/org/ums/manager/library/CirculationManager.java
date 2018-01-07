package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface CirculationManager extends ContentManager<Circulation, MutableCirculation, Long> {

  int saveCheckout(final MutableCirculation pMutableCirculation);

  List<Circulation> getCirculation(final String pPatronId);

  int updateCirculation(final MutableCirculation pMutableCirculation);

  int batchUpdateCirculation(final List<MutableCirculation> pMutable);

  List<Circulation> getAllCirculation(final String pPatronId);

  List<Circulation> getCirculationCheckedInItems(final String pPatronId);

  Circulation getSingleCirculation(final String pAccessionNumber);
}
