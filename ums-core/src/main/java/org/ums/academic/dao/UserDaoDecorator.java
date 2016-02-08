package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.Department;
import org.ums.domain.model.readOnly.Teacher;
import org.ums.domain.model.readOnly.User;
import org.ums.manager.TeacherManager;
import org.ums.manager.UserManager;

import java.util.List;

public class UserDaoDecorator  extends ContentDaoDecorator<User, MutableUser, String, UserManager> implements UserManager {
  @Override
  public void setPasswordResetToken(String pToken, String pUserId)  throws Exception{
     getManager().setPasswordResetToken(pToken, pUserId);
  }
}
