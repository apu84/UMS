package org.ums.punishment.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

public class MutableAuthorityResource extends Resource {

  @Autowired
  ResourceHelper<Authority, MutableAuthority, Long> mHelper;
}
