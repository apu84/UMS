package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.message.MessageResource;
import org.ums.usermanagement.user.UserManager;

import java.util.Base64;
import java.util.List;
import java.util.Map;

public abstract class BaseFileContentPermission extends BinaryContentDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(BaseFileContentPermission.class);

  private BearerAccessTokenManager mBearerAccessTokenManager;
  protected UserManager mUserManager;
  protected MessageResource mMessageResource;

  public BaseFileContentPermission(final BearerAccessTokenManager pBearerAccessTokenManager,
      final UserManager pUserManager, final MessageResource pMessageResource) {
    mBearerAccessTokenManager = pBearerAccessTokenManager;
    mUserManager = pUserManager;
    mMessageResource = pMessageResource;
  }

  protected BearerAccessTokenManager getBearerAccessTokenManager() {
    return mBearerAccessTokenManager;
  }

  protected UserManager getUserManager() {
    return mUserManager;
  }

  protected String encrypt(String plainText) {
    return Base64.getEncoder().encodeToString(plainText.getBytes());
  }

  protected static String decrypt(String plainText) {
    return new String(Base64.getDecoder().decode(plainText));
  }

  protected boolean isValidToken(final String pToken) {
    try {
      String decodedToken = decrypt(pToken);
      String[] splitToken = decodedToken.split(":");

      if(splitToken.length == 2) {
        BearerAccessToken userToken = mBearerAccessTokenManager.getByUser(splitToken[0]);
        if(userToken != null) {
          if(userToken.getId().equals(splitToken[1])) {
            return true;
          }
        }

      }
    } catch(Exception e) {
      mLogger.info("Token is not valid", e);
    }
    return false;
  }

  protected Object addOwnerToken(final Object folderList) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    String accessToken = getBearerAccessTokenManager().getByUser(userId).getId();
    try {
      String encryptedToken = encrypt(userId + ":" + accessToken);

      if(folderList instanceof List) {
        List list = (List) folderList;
        for(Object folder : list) {
          if(folder instanceof Map) {
            ((Map) folder).put(TOKEN, encryptedToken);
            String owner = ((Map<String, String>) folder).get(OWNER);
            if(!StringUtils.isEmpty(owner)) {
              owner = mUserManager.get(owner).getName();
            }
            ((Map<String, String>) folder).put(OWNER, owner);
          }
        }
      }
    } catch(Exception e) {
      return error(mMessageResource.getMessage("folder.listing.failed"));
    }
    return folderList;
  }
}
