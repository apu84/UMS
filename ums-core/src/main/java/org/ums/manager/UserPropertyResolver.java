package org.ums.manager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.usermanagement.user.UserDaoDecorator;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.MutableUser;

public class UserPropertyResolver extends UserDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(UserPropertyResolver.class);

  private EmployeeManager mEmployeeManager;
  private PersonalInformationManager mPersonalInformationManager;
  private StudentManager mStudentManager;

  public UserPropertyResolver(EmployeeManager pEmployeeManager, PersonalInformationManager pPersonalInformationManager,
      StudentManager pStudentManager) {
    mEmployeeManager = pEmployeeManager;
    mPersonalInformationManager = pPersonalInformationManager;
    mStudentManager = pStudentManager;
  }

  @Override
  public List<User> getAll() {
    return super.getUsers().stream().map(user -> transform(user)).collect(Collectors.toList());
  }

  @Override
  public User get(String pId) {
    User user = getManager().get(pId);
    return transform(user);
  }

  @Override
  public List<User> getUsers() {
    return super.getUsers().stream().map(user -> transform(user)).collect(Collectors.toList());
  }

  @Override
  public Optional<User> getByEmail(String pEmail) {
    Optional<Student> student = mStudentManager.getByEmail(pEmail);
    if(student.isPresent()) {
      return Optional.of(transform(getManager().get(student.get().getId())));
    }
    Optional<PersonalInformation> personalInformation = mPersonalInformationManager.getByEmail(pEmail);
    if(personalInformation.isPresent()) {
      return Optional.of(transform(getManager().getByEmployeeId(personalInformation.get().getId())));
    }
    return Optional.empty();
  }

  private User transform(User user) {
    MutableUser mutableUser = user.edit();
    if(user.getPrimaryRole().getName().equalsIgnoreCase("sadmin")) {
      mutableUser.setDepartment(null);
      mutableUser.setName("Admin User");
      mutableUser.setEmployeeId("-1");
      return mutableUser;
    }
    if(user.getPrimaryRole().getName().equalsIgnoreCase("student")) {
      Student student = mStudentManager.get(user.getId());
      mutableUser.setDepartment(student.getDepartment());
      mutableUser.setName(student.getFullName());
      mutableUser.setEmail(student.getEmail());
    }
    else {
      Employee employee = null;
      try {
        employee = mEmployeeManager.get(user.getEmployeeId());
      } catch(Exception e) {
        mLogger.error("User not found as employee, " + user.getId(), e);
      }
      if(employee != null) {
        mutableUser.setDepartment(employee.getDepartment());
        mutableUser.setName(employee.getPersonalInformation().getFullName());
        mutableUser.setEmployeeId(employee.getId());
        mutableUser.setEmail(employee.getPersonalInformation().getPersonalEmail());
      }
    }
    return mutableUser;
  }
}
