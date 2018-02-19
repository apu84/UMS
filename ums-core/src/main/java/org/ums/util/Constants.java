package org.ums.util;

import java.text.SimpleDateFormat;

public interface Constants {
  String DATE_FORMAT = "dd/MM/yyyy";
  SimpleDateFormat DATE_TIME_24H_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  SimpleDateFormat DATE_TIME_12H_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa");
  SimpleDateFormat DF_dd_Mon_YY_Time = new SimpleDateFormat("dd MMM, yy HH:mm:ss");
  String SERVICE_CONTEXT = "services-context.xml";

  long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
  long PASSWORD_RESET_TOKEN_LIFE = 60 * 24 * 1;// Minutes*Hours*Days
  long PASSWORD_RESET_TOKEN_EMAIL_LIFE = 5;// minutes

  String[] validRolesForGradeAccess = {"preparer", "scrutinizer", "courseteacher", "head", "coe", "vc"};
  // Grade Roles
  String GRADE_PREPARER = "preparer";
  String GRADE_SCRUTINIZER = "scrutinizer";
  String COURSE_TEACHER = "courseteacher";
  String HEAD = "head";
  String COE = "coe";
  String VC = "vc";

  // Application Labels
  String University_AllCap = "AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY";
  String University_InitCap = "Ahsanullah University of Science and Technology";
  String University_Address = "141-142 Love Road, Tejgaon Industrial Area, Dhaka-1208";
  String ONE_BANK = "One Bank Ltd.";

}
