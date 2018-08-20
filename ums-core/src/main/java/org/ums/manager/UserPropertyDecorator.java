package org.ums.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.immutable.Student;
import org.ums.ems.profilemanagement.personal.PersonalInformation;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.usermanagement.transformer.UserPropertyResolver;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserDaoDecorator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserPropertyDecorator extends UserDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(UserPropertyDecorator.class);

  private PersonalInformationManager mPersonalInformationManager;
  private StudentManager mStudentManager;
  private List<UserPropertyResolver> mUserPropertyTransformers;

  public UserPropertyDecorator(PersonalInformationManager pPersonalInformationManager, StudentManager pStudentManager,
      List<UserPropertyResolver> pUserPropertyTransformers) {
    mPersonalInformationManager = pPersonalInformationManager;
    mStudentManager = pStudentManager;
    mUserPropertyTransformers = pUserPropertyTransformers;
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
    return super.getUsers().stream().map(this::transform).collect(Collectors.toList());
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
    for(UserPropertyResolver userPropertyTransformer : mUserPropertyTransformers) {
      if(userPropertyTransformer.supports(user.getPrimaryRole())) {
        return userPropertyTransformer.resolve(user);
      }
    }
    return user;
  }
}
