package org.ums.fee.semesterfee;

import org.ums.decorator.ContentDaoDecorator;

public class InstallmentSettingsDaoDecorator extends
    ContentDaoDecorator<InstallmentSettings, MutableInstallmentSettings, Long, InstallmentSettingsManager> implements
    InstallmentSettingsManager {
}
