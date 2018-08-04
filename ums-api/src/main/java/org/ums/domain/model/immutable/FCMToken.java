package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableFCMToken;

import java.io.Serializable;
import java.util.Date;

public interface FCMToken extends Serializable, LastModifier, EditType<MutableFCMToken>, Identifier<String> {

  String getToken();

  Date getCreatedOn();
}
