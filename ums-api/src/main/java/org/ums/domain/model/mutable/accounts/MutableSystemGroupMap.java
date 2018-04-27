package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.group.GroupType;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public interface MutableSystemGroupMap extends SystemGroupMap, Editable<String>, MutableIdentifier<String>,
    MutableLastModifier {

  void setGroupType(GroupType pGroupType);

  void setGroup(Group pGroup);

  void setGroupId(Long pGroupId);
}