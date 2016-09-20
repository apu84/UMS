package org.ums.common.validator;


import org.ums.exceptions.ValidationException;

import javax.json.JsonObject;

public interface Validator {
  void validate(final JsonObject pJsonObject) throws ValidationException, ValidationException;
}
