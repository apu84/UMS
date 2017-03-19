package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.mutable.library.MutableMaterialContributor;
import org.ums.domain.model.mutable.library.MutableNote;
import org.ums.manager.library.MaterialContributorManager;
import org.ums.manager.library.NoteManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class NoteDaoDecorator extends ContentDaoDecorator<Note, MutableNote, Integer, NoteManager> implements
    NoteManager {

}
