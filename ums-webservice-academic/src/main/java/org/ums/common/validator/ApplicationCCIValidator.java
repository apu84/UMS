package org.ums.common.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.validator.AbstractValidator;

import javax.json.JsonObject;

/**
 * Created by My Pc on 7/17/2016.
 */
public class ApplicationCCIValidator extends AbstractValidator {

  @Autowired
  UGRegistrationResultManager mResultManager;

  @Autowired
  ApplicationCCIManager mAppManager;

  @Autowired
  ParameterSettingManager mParameterSettingManager;

  @Override
  protected void validateFields(JsonObject pJsonObject) {
    if(pJsonObject.size() == 0) {
      addFieldValidationException("message", "Can not save empty data");
    }
  }

}
