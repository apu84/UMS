package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableNote;
import org.ums.domain.model.mutable.library.MutableSubject;

import java.io.Serializable;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface Note extends Serializable, LastModifier, EditType<MutableNote>,
    Identifier<Integer> {

  String getMfn();

  Integer getViewOrder();

  String getNote();
}
