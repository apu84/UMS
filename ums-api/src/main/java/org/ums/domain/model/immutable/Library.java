package org.ums.domain.model.immutable;

/**
 * Created by kawsu on 12/3/2016.
 */

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableLibrary;

import java.io.Serializable;

public interface Library extends Serializable, EditType<MutableLibrary>, Identifier<Integer>, LastModifier {

  String getBookName();

  String getAuthorName();
}
