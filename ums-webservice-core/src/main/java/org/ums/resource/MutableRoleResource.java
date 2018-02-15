package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.RoleResourceHelper;

public class MutableRoleResource extends Resource {

  @Autowired
  RoleResourceHelper mHelper;
}
