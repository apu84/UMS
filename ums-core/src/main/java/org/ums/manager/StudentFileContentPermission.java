package org.ums.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.message.MessageResource;

public class StudentFileContentPermission extends BinaryContentDecorator {
  private static Logger mLogger = LoggerFactory.getLogger(StudentFileContentPermission.class);
  private BearerAccessTokenManager mBearerAccessTokenManager;
  private UserManager mUserManager;
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;

  public StudentFileContentPermission(final UserManager pUserManager,
                                      final BearerAccessTokenManager pBearerAccessTokenManager,
                                      final UMSConfiguration pUMSConfiguration,
                                      final MessageResource pMessageResource) {
    mBearerAccessTokenManager = pBearerAccessTokenManager;
    mUserManager = pUserManager;
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }
}
