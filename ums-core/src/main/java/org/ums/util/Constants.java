package org.ums.util;


public interface Constants {
  public static final String DATE_FORMAT = "dd/MM/yyyy";
  public static final String SERVICE_CONTEXT = "services-context.xml";

  public static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
  public static final long PASSWORD_RESET_TOKEN_LIFE=60*24*30;//Minutes*Hours*Days
  public static final long PASSWORD_RESET_TOKEN_EMAIL_LIFE=5;//minutes

  public static final String[] validRolesForGradeAccess={"preparer","scrutinizer","courseteacher","head","coe","vc"};
  //Grade Roles
  public static final String GRADE_PREPARER="preparer";
  public static final String GRADE_SCRUTINIZER="scrutinizer";
  public static final String COURSE_TEACHER="courseteacher";
  public static final String HEAD="head";
  public static final String COE="coe";
  public static final String VC="vc";

}

