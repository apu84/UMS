package org.ums.decorator;

import org.ums.domain.model.immutable.UGTheoryMarks;
import org.ums.domain.model.mutable.MutableUGTheoryMarks;
import org.ums.manager.UGTheoryMarksManager;

public class UGTheoryMarksDaoDecorator extends
    ContentDaoDecorator<UGTheoryMarks, MutableUGTheoryMarks, Integer, UGTheoryMarksManager>
    implements UGTheoryMarksManager {
}
