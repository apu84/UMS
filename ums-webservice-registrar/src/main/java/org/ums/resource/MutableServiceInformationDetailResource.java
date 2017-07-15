package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.ServiceInformationDetailResourceHelper;

public class MutableServiceInformationDetailResource extends Resource {

  @Autowired
  ServiceInformationDetailResourceHelper mHelper;

}
