package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ApplicationCCIResourceHelper;
import org.ums.academic.resource.helper.ApplicationTESResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class MutableApplicationTESResource extends Resource {

  @Autowired
  ApplicationTESResourceHelper mHelper;
}
