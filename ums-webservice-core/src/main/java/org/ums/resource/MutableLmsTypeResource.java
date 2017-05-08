package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.LmsTypeResourceHelper;

/**
 * Created by Monjur-E-Morshed on 08-May-17.
 */
public class MutableLmsTypeResource extends Resource {

  @Autowired
  protected LmsTypeResourceHelper mHelper;

}
