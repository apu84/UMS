package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.DeptDesignationMap;

public interface MutableDeptDesignationMap extends DeptDesignationMap, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {
}
