package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutableAwardInformation;

import java.io.Serializable;
import java.util.Date;

public interface AwardInformation extends Serializable, EditType<MutableAwardInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getAwardName();

  String getAwardInstitute();

  String getAwardedYear();

  String getAwardShortDescription();
}
