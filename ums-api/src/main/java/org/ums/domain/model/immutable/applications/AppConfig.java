package org.ums.domain.model.immutable.applications;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.fee.FeeCategory;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 18-Sep-17.
 */
public interface AppConfig extends Serializable, EditType<MutableAppConfig>, LastModifier, Identifier<Long> {

  FeeCategory getFeeCategory();

  int getValidityPeriod();

  Department getDepartment();

  Boolean getHeadsForwarding();
}
