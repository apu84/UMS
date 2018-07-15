package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.QuestionCorrectionResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class MutableQuestionCorrectionResource extends Resource {
  @Autowired
  QuestionCorrectionResourceHelper mQuestionCorrectionResourceHelper;

}
