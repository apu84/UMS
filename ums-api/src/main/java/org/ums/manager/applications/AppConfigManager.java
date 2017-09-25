package org.ums.manager.applications;

import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
public interface AppConfigManager extends ContentManager<AppConfig, MutableAppConfig, Long> {

}
