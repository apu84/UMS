package org.ums.twofa;

import java.io.Serializable;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.usermanagement.user.User;

public interface TwoFAToken extends Serializable, EditType<MutableTwoFAToken>, LastModifier, Identifier<Long> {

  User getUser();

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
