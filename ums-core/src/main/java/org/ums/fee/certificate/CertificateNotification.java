package org.ums.fee.certificate;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.ums.domain.model.immutable.Notification;
import org.ums.message.MessageResource;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class CertificateNotification extends CertificateStatusDaoDecorator {
  private NotificationGenerator mNotificationGenerator;
  private MessageResource mMessageResource;
  private UserManager mUserManager;
  private RoleManager mRoleManager;

  public CertificateNotification(RoleManager pRoleManager, UserManager pUserManager,
      NotificationGenerator pNotificationGenerator, MessageResource pMessageResource) {
    mNotificationGenerator = pNotificationGenerator;
    mMessageResource = pMessageResource;
    mUserManager = pUserManager;
    mRoleManager = pRoleManager;
  }

  @Override
  public int update(List<MutableCertificateStatus> pMutableList) {
    pMutableList.forEach((certificateStatus) -> {
      Notifier notifier = new Notifier() {
        @Override
        public List<String> consumers() {
          return Lists.newArrayList(certificateStatus.getStudentId());
        }

        @Override
        public String producer() {
          return SecurityUtils.getSubject().getPrincipal().toString();
        }

        @Override
        public String notificationType() {
          return new StringBuilder(Notification.Type.CERTIFICATE.getValue()).append("_")
              .append(certificateStatus.getFeeCategory().getId()).append("_")
              .append(certificateStatus.getSemester().getId()).toString();
        }

        @Override
        public String payload() {
          return mMessageResource.getMessage("certificate.processed",
              certificateStatus.getFeeCategory().getDescription());
        }
      };
      mNotificationGenerator.notify(notifier);
    });
    return pMutableList.size();
  }

  @Override
  public List<Long> create(List<MutableCertificateStatus> pMutableList) {
    pMutableList.forEach((certificateStatus) -> {
      Notifier notifier = new Notifier() {
        @Override
        public List<String> consumers() {
          List<User> users =
              mUserManager.getUsers(mRoleManager.getRolesByPermission(Sets.newHashSet("certificate:process")));
          return users.stream().map(User::getId).collect(Collectors.toList());
        }

        @Override
        public String producer() {
          return SecurityUtils.getSubject().getPrincipal().toString();
        }

        @Override
        public String notificationType() {
          return new StringBuilder(Notification.Type.CERTIFICATE.getValue()).append("_")
              .append(certificateStatus.getFeeCategory().getId()).append("_")
              .append(certificateStatus.getSemester().getId()).toString();
        }

        @Override
        public String payload() {
          return mMessageResource.getMessage("certificate.applied", certificateStatus.getStudentId(),
              certificateStatus.getFeeCategory().getDescription());
        }
      };
      mNotificationGenerator.notify(notifier);
    });
    
    return pMutableList.stream().map(CertificateStatus::getId).collect(Collectors.toList());
  }
}
