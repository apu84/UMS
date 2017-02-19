package org.ums.decorator;

import org.ums.domain.model.immutable.UGSessionalMarks;
import org.ums.domain.model.mutable.MutableUGSessionalMarks;
import org.ums.manager.UGSessionalMarksManager;

public class UGSessionalMarksDaoDecorator extends
    ContentDaoDecorator<UGSessionalMarks, MutableUGSessionalMarks, Long, UGSessionalMarksManager>
    implements UGSessionalMarksManager {
}
