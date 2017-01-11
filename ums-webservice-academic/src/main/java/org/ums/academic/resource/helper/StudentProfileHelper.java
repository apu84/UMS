package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.StudentProfileBuilder;
import org.ums.builder.StudentBuilder;

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
