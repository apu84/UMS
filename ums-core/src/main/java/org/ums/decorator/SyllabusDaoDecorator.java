package org.ums.decorator;

import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.manager.SyllabusManager;

import java.util.List;

/**
 * Created by Ifti on 28-Dec-15.
 */
public class SyllabusDaoDecorator extends
    ContentDaoDecorator<Syllabus, MutableSyllabus, String, SyllabusManager> implements
    SyllabusManager {
  @Override
  public List<Syllabus> getSyllabusList(Integer pProgramId) throws Exception {
    return getManager().getSyllabusList(pProgramId);
  }
}
