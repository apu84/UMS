package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Subject;
import org.ums.domain.model.mutable.library.MutableSubject;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface SubjectManager extends ContentManager<Subject, MutableSubject, Integer> {

}
