package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.UserViewResourceHelper;

public class MutableUserViewResource extends Resource {

  @Autowired
  UserViewResourceHelper mHelper;
}
