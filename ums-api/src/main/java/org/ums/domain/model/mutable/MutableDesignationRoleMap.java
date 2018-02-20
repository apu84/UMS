package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.domain.model.immutable.DesignationRoleMap;
import org.ums.domain.model.immutable.Designation;
import org.ums.usermanagement.role.Role;

public interface MutableDesignationRoleMap extends DesignationRoleMap, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setDesignation(Designation pDesignation);

  void setDesignationId(Integer pDesignationId);

  void setRole(Role pRole);

  void setRoleId(Integer pRoleId);
}
