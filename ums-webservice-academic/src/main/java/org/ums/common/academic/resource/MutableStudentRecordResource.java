package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.StudentRecordResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by My Pc on 08-Aug-16.
 */
public class MutableStudentRecordResource extends Resource{


  @Autowired
  StudentRecordResourceHelper mHelper;

}
