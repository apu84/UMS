package org.ums.common.validator;


import org.springframework.util.StringUtils;
import org.ums.validator.AbstractValidator;

import javax.json.JsonObject;

public class StudentValidator extends AbstractValidator {
  @Override
  public void validateFields(JsonObject pJsonObject) {
    if (!pJsonObject.containsKey("fatherName") || StringUtils.isEmpty(pJsonObject.getString("fatherName"))) {
      addFieldValidationException("fatherName", "Father name is missing");
    }
  }
}
