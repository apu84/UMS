package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.mutable.library.MutableNote;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface NoteManager extends ContentManager<Note, MutableNote, Integer> {

}
