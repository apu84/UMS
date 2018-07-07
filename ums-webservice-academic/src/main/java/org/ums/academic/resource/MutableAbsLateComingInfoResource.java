package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AbsLateComingInfoResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class MutableAbsLateComingInfoResource extends Resource {
  @Autowired
  AbsLateComingInfoResourceHelper mHelper;
}
