package org.ums.decorator.applications;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.manager.applications.AppConfigManager;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
public class AppConfigDaoDecorator extends ContentDaoDecorator<AppConfig, MutableAppConfig, Long, AppConfigManager>
    implements AppConfigManager {

}
