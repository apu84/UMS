package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.FCMToken;

import java.util.Date;

public interface MutableFCMToken extends FCMToken, Editable<String>, MutableLastModifier, MutableIdentifier<String> {

  void setToken(final String pToken);

  void setCreatedOn(final Date pSetUpdatedOn);
}
