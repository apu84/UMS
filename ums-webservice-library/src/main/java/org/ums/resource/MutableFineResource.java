package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.FineResourceHelper;

public class MutableFineResource extends Resource {

  @Autowired
  FineResourceHelper mHelper;
}
