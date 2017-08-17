package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

public class MutableAgendaResolutionResource extends Resource {

  @Autowired
  AgendaResolutionResourceHelper mHelper;

}
