package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.builder.StudentBuilder;
import org.ums.common.builder.StudentProfileBuilder;

@Component
@Qualifier("StudentProfileHelper")
public class StudentProfileHelper extends StudentResourceHelper {
  @Autowired
  @Qualifier("StudentProfileBuilder")
  private StudentProfileBuilder mBuilder;

  @Override
  protected StudentBuilder getBuilder() {
    return mBuilder;
  }
}
