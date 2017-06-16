package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;
import org.ums.resource.helper.DesignationResourceHelper;

public class MutableDesignationResource extends Resource {

  @Autowired
  ResourceHelper<Designation, MutableDesignation, Integer> mHelper;

  @Autowired
  DesignationResourceHelper mResourceHelper;
}
