package org.ums.common.validator;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.json.JsonObject;

@Component
@Scope("prototype")
public class StudentValidator extends AbstractValidator {
  @Override
  public void validateFields(JsonObject pJsonObject) {
    if (!pJsonObject.containsKey("fatherName") || StringUtils.isEmpty(pJsonObject.getString("fatherName"))) {
      addFieldValidationException("fatherName", "Father name is missing");
    }
  }
}
