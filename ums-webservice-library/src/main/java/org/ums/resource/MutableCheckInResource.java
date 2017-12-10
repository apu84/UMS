package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.CheckInResourceHelper;

public class MutableCheckInResource extends Resource {

  @Autowired
  CheckInResourceHelper mHelper;
}
