package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableBearerAccessToken;

import java.io.Serializable;
import java.util.Date;

public interface BearerAccessToken extends Serializable, Identifier<String>, EditType<MutableBearerAccessToken>,
    LastModifier {
  String getUserId();

  Date getLastAccessTime();
}
