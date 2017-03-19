package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;

import java.io.Serializable;

/**
 * Created by Ifti on 30-Jan-17.
 */
public interface Country extends Serializable, LastModifier, EditType<MutableCountry>, Identifier<Integer> {

  String getCode();

  String getName();

}
