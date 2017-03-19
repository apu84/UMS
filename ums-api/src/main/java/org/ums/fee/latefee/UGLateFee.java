package org.ums.fee.latefee;

import java.io.Serializable;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;

public interface UGLateFee extends Serializable, EditType<MutableUGLateFee>, LastModifier, Identifier<Long> {

  Date getFrom();

  Date getTo();

  Integer getFee();

  Semester getSemester();

  Integer getSemesterId();
}
