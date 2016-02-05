package org.ums.manager;

import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.domain.model.readOnly.Syllabus;

import java.util.List;

/**
 * Created by Ifti on 28-Dec-15.
 */
public interface SyllabusManager extends ContentManager<Syllabus, MutableSyllabus, String> {
  public List<Syllabus> getSyllabusList(final Integer pProgramId) throws Exception;
}
