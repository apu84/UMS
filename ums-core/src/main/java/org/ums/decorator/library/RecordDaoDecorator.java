package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.library.RecordManager;

/**
 * Created by Ifti on 18-Feb-17.
 */
public class RecordDaoDecorator extends
    ContentDaoDecorator<Record, MutableRecord, Long, RecordManager> implements RecordManager {

}
