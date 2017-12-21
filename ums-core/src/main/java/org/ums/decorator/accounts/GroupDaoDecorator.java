package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.accounts.GroupManager;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class GroupDaoDecorator extends ContentDaoDecorator<Group, MutableGroup, Long, GroupManager> {

}
