package org.ums.manager;

import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Syllabus;

import java.util.List;

/**
 * Created by Ifti on 28-Dec-15.
 */
public interface SyllabusManager extends ContentManager<Syllabus, MutableSemester, Integer> {
  public List<Syllabus> getSyllabusList(final Integer pProgramId) throws Exception;
}
