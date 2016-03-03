package org.ums.manager;

import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.readOnly.Navigation;

import java.util.List;
import java.util.Set;

public interface NavigationManager extends ContentManager<Navigation, MutableNavigation, Integer> {
  List<Navigation> getByPermissions(final Set<String> pPermissions) throws Exception;

  List<Navigation> getByPermissionsId(final Set<Integer> pPermissionIds);
}
