package org.ums.util;

import java.text.SimpleDateFormat;

public interface Constants {
  public static final String DATE_FORMAT = "dd/MM/yyyy";
  public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  public static final SimpleDateFormat DF_dd_Mon_YY_Time = new SimpleDateFormat("dd MMM, yy HH:mm:ss");
  public static final String SERVICE_CONTEXT = "services-context.xml";

  public static final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
  public static final long PASSWORD_RESET_TOKEN_LIFE = 60 * 24 * 1;// Minutes*Hours*Days
  public static final long PASSWORD_RESET_TOKEN_EMAIL_LIFE = 5;// minutes

  public static final String[] validRolesForGradeAccess = {"preparer", "scrutinizer", "courseteacher", "head", "coe",
      "vc"};
  // Grade Roles
  public static final String GRADE_PREPARER = "preparer";
  public static final String GRADE_SCRUTINIZER = "scrutinizer";
  public static final String COURSE_TEACHER = "courseteacher";
  public static final String HEAD = "head";
  public static final String COE = "coe";
  public static final String VC = "vc";

  // Application Labels
  public static final String University_AllCap = "AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY";
  public static final String University_InitCap = "Ahsanullah University of Science and Technology";

}
