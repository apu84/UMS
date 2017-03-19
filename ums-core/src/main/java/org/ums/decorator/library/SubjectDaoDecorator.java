package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.immutable.library.Subject;
import org.ums.domain.model.mutable.library.MutableNote;
import org.ums.domain.model.mutable.library.MutableSubject;
import org.ums.manager.library.NoteManager;
import org.ums.manager.library.SubjectManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class SubjectDaoDecorator extends ContentDaoDecorator<Subject, MutableSubject, Integer, SubjectManager>
    implements SubjectManager {

}
