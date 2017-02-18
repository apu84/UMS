package org.ums.manager;

import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.immutable.Navigation;

import java.util.List;
import java.util.Set;

public interface NavigationManager extends ContentManager<Navigation, MutableNavigation, Long> {
  List<Navigation> getByPermissions(final Set<String> pPermissions);

  List<Navigation> getByPermissionsId(final Set<Integer> pPermissionIds);
}
