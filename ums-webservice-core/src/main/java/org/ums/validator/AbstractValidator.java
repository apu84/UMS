package org.ums.validator;

import org.ums.exceptions.ValidationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public abstract class AbstractValidator implements Validator {
  JsonObjectBuilder mFieldValidationMap = null;
  JsonObjectBuilder mValidationRoot = null;

  public AbstractValidator() {
    mFieldValidationMap = Json.createObjectBuilder();
    mValidationRoot = Json.createObjectBuilder();

  }

  protected void addRootCause(final String pRootCause) {
    mValidationRoot.add("rootCause", pRootCause);
  }

  protected void addFieldValidationException(final String pField, final String pValidationMessage) {
    mFieldValidationMap.add(pField, pValidationMessage);
  }

  @Override
  public void validate(JsonObject pJsonObject) throws ValidationException {
    validateFields(pJsonObject);
    aggregateValidations();
  }

  private void aggregateValidations() throws ValidationException {
    mValidationRoot.add("fieldValidation", mFieldValidationMap.build());
    JsonObject validationRoot = mValidationRoot.build();
    if (validationRoot.containsKey("rootCause")
      || validationRoot.getJsonObject("fieldValidation").size() > 0){
      throw new ValidationException(validationRoot.toString());
    }
  }

  protected abstract void validateFields(JsonObject pJsonObject);
}
