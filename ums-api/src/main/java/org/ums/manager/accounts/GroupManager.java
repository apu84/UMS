package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public interface GroupManager extends ContentManager<Group, MutableGroup, Long> {
  List<Group> getGroups(Group pGroup);
}
