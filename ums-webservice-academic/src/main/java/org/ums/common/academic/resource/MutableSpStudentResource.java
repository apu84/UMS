package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.SpStudentResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by My Pc on 4/28/2016.
 */

public class MutableSpStudentResource extends Resource {
  @Autowired
  SpStudentResourceHelper mHelper;

}
