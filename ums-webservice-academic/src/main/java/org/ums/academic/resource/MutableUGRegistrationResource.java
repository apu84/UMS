package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.UGRegistrationResultResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by My Pc on 7/13/2016.
 */

public class MutableUGRegistrationResource extends Resource {

  @Autowired
  UGRegistrationResultResourceHelper mHelper;
}
