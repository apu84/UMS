package org.ums.manager;


import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.readOnly.StudentRecord;

public interface StudentRecordManager extends ContentManager<StudentRecord, MutableStudentRecord, Integer> {
}
