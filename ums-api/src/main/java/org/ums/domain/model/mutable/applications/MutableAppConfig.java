package org.ums.domain.model.mutable.applications;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 18-Sep-17.
 */
public interface MutableAppConfig extends AppConfig, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setFeeCategoryId(String pFeeCategoryId);

  void setValidityPeriod(int pValidityPeriod);

  void setDepartmentId(String pDepartmentId);

  void setHeadsForwarding(boolean pHeadsForwarding);

}
