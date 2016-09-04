package org.ums.decorator;

import org.ums.domain.model.immutable.AppSetting;
import org.ums.domain.model.mutable.MutableAppSetting;
import org.ums.manager.AppSettingManager;

/**
 * Created by My Pc on 30-Aug-16.
 */
public class AppSettingDaoDecorator extends ContentDaoDecorator<AppSetting,MutableAppSetting,Integer,AppSettingManager> implements AppSettingManager{

}
