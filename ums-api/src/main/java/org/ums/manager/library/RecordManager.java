package org.ums.manager.library;

import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 18-Feb-17.
 */
public interface RecordManager extends ContentManager<Record, MutableRecord, Long> {
}
