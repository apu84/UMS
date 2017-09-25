package org.ums.decorator.applications;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.manager.applications.AppRulesManager;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public class AppRulesDaoDecorator extends ContentDaoDecorator<AppRules, MutableAppRules, Long, AppRulesManager> implements AppRulesManager {

}
