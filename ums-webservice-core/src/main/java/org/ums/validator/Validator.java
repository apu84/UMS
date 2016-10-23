package org.ums.validator;

import org.ums.exceptions.ValidationException;

import javax.json.JsonObject;

public interface Validator {
  void validate(final JsonObject pJsonObject) throws ValidationException;
}
