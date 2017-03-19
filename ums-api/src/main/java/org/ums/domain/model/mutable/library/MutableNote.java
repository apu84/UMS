package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface MutableNote extends Note, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setMfn(String pMfn);

  void setViewOrder(Integer pViewOrder);

  void setNote(String pNote);
}
