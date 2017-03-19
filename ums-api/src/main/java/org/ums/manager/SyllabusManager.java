package org.ums.manager;

import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.domain.model.immutable.Syllabus;

import java.util.List;

/**
 * Created by Ifti on 28-Dec-15.
 */
public interface SyllabusManager extends ContentManager<Syllabus, MutableSyllabus, String> {
  List<Syllabus> getSyllabusList(final Integer pProgramId);
}
