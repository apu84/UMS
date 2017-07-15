package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.enums.common.EmploymentPeriod;

import java.io.Serializable;
import java.util.Date;

public interface ServiceInformationDetail extends Serializable, EditType<MutableServiceInformationDetail>,
    Identifier<Integer>, LastModifier {

  EmploymentPeriod getEmploymentPeriod();

  int getEmploymentPeriodId();

  Date getStartDate();

  Date getEndDate();

  int getServiceId();
}
