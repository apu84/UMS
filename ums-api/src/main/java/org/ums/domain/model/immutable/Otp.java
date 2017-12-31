package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableOtp;

import java.io.Serializable;
import java.util.Date;

public interface Otp extends Serializable, EditType<MutableOtp>, Identifier<Long>, LastModifier {

  String getUserId();

  String getType();

  boolean isUsed();

  Date getGeneratedOn();

  Date getExpiredOn();

  Date getUsedOn();

  int getTryCount();

  String getOtp();

  String getLastModified();
}
