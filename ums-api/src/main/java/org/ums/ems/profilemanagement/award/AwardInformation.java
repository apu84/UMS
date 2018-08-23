package org.ums.ems.profilemanagement.award;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface AwardInformation extends Serializable, EditType<MutableAwardInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  String getAwardName();

  String getAwardInstitute();

  int getAwardedYear();

  String getAwardShortDescription();
}
