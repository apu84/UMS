package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ApplicationTesSetQuestionManager;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */

public class MutableApplicationTesSetQuestionResource extends Resource {
  @Autowired
  ApplicationTesSetQuestionManager mApplicationTesSetQuestionManager;
}
