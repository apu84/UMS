package org.ums.manager.common;

import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsApplicationManager extends ContentManager<LmsApplication, MutableLmsApplication, Integer> {
  List<LmsApplication> getLmsApplication(String pEmployeeId, int pYear);

  List<LmsApplication> getPendingLmsApplication(String pEmployeeId);
}
