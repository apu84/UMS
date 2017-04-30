package org.ums.fee.semesterfee;

import java.util.Optional;

import org.ums.decorator.ContentDaoDecorator;

public class InstallmentSettingsDaoDecorator extends
    ContentDaoDecorator<InstallmentSettings, MutableInstallmentSettings, Long, InstallmentSettingsManager> implements
    InstallmentSettingsManager {
  @Override
  public Optional<InstallmentSettings> getInstallmentSettings(Integer pSemesterId) {
    return getManager().getInstallmentSettings(pSemesterId);
  }
}
