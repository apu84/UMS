package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.builder.StudentBuilder;
import org.ums.common.builder.StudentProfileBuilder;

@Component
public class StudentProfileHelper extends StudentResourceHelper {
  @Autowired
  private StudentProfileBuilder mBuilder;

  @Override
  protected StudentBuilder getBuilder() {
    return mBuilder;
  }
}
