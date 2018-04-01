package org.ums.bank;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface Bank
    extends
    Serializable,
    EditType<MutableBank>,
    LastModifier,
    Identifier<String> {

  String getName();
}
