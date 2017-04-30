package org.ums.fee.semesterfee;

import java.util.Optional;

import org.ums.manager.ContentManager;

public interface InstallmentSettingsManager extends
    ContentManager<InstallmentSettings, MutableInstallmentSettings, Long> {
  Optional<InstallmentSettings> getInstallmentSettings(Integer pSemesterId);
}
