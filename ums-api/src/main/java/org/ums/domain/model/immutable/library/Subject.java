package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableSubject;

import java.io.Serializable;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface Subject extends Serializable, LastModifier, EditType<MutableSubject>,
    Identifier<Integer> {

  String getMfn();

  Integer getViewOrder();

  String getSubject();

}
