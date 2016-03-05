package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.readOnly.StudentRecord;
import org.ums.manager.StudentRecordManager;

public class StudentRecordDaoDecorator
    extends ContentDaoDecorator<StudentRecord, MutableStudentRecord, Integer, StudentRecordManager>
    implements StudentRecordManager {
}
