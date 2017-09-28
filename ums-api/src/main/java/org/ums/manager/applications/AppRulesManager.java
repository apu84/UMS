package org.ums.manager.applications;

import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public interface AppRulesManager extends ContentManager<AppRules, MutableAppRules, Long> {
  List<AppRules> getDependencies(String pFeeCategoryId);
}
