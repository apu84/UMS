package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.ResourceHelper;
import org.ums.common.academic.resource.helper.StudentRecordResourceHelper;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.mutable.MutableStudentRecord;

/**
 * Created by My Pc on 08-Aug-16.
 */
public class MutableStudentRecordResource extends Resource{


  @Autowired
  StudentRecordResourceHelper mHelper;

}
