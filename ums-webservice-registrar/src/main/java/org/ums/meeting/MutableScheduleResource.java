package org.ums.meeting;

import com.ctc.wstx.io.ReaderSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

public class MutableScheduleResource extends Resource {

  @Autowired
  ScheduleResourceHelper mHelper;

}
