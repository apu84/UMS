package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.CirculationResourceHelper;

public class MutableCirculationResource extends Resource {

  @Autowired
  CirculationResourceHelper mHelper;
}
