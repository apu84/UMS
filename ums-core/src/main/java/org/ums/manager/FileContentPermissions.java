package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.message.MessageResource;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class FileContentPermissions extends BinaryContentDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(FileContentPermissions.class);

  private final static String TOKEN = "token";
  private static final String OWNER = "owner";

  private BearerAccessTokenManager mBearerAccessTokenManager;
  private UserManager mUserManager;
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;

  public FileContentPermissions(final UserManager pUserManager,
                                final BearerAccessTokenManager pBearerAccessTokenManager,
                                final UMSConfiguration pUMSConfiguration,
                                final MessageResource pMessageResource) {
    mBearerAccessTokenManager = pBearerAccessTokenManager;
    mUserManager = pUserManager;
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
  }


  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain) {
    if (isValidToken(pToken)) {
      return super.downloadAsZip(pItems, pNewFileName, pToken, pDomain);
    } else {
      return null;
    }
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain) {
    if (isValidToken(pToken)) {
      return super.download(pPath, pToken, pDomain);
    } else {
      return null;
    }
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain) {
    return super.upload(pFileContent, pPath, pDomain);
  }

  @Override
  public Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain) {
    return super.extract(pZippedItem, pDestination, pDomain);
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    return super.compress(pItems, pNewPath, pNewFileName, pDomain);
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain) {
    // Check if there is permission to create folder
    Path parentPath = Paths.get(getQualifiedPath(pNewPath, pDomain).toString()).getParent();
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }
    return super.createFolder(pNewPath, pDomain);
  }

  @Override
  public Map<String, byte[]> content(String pPath, Domain pDomain) {
    return super.content(pPath, pDomain);
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain) {
    return super.remove(pItems, pDomain);
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    Path parentPath = Paths.get(getQualifiedPath(pNewPath, pDomain).toString());
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }

    for (String copiedFile : pItems) {
      Path copiedFilePath = Paths.get(getQualifiedPath(copiedFile, pDomain).toString());

      if (!checkIfAllowed(copiedFilePath)) {
        return error(mMessageResource.getMessage("folder.copy.not.allowed"));

      }
    }
    return super.copy(pItems, pNewPath, pNewFileName, pDomain);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain) {
    Path parentPath = Paths.get(getQualifiedPath(pNewPath, pDomain).toString());
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.move.not.allowed"));
    }

    for (String movedFile : pItems) {
      Path movedFilePath = Paths.get(getQualifiedPath(movedFile, pDomain).toString());

      if (!checkIfAllowed(movedFilePath)) {
        return error(mMessageResource.getMessage("folder.move.not.allowed"));

      }
    }
    return super.move(pItems, pNewPath, pDomain);
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain) {
    Path parentPath = Paths.get(getQualifiedPath(pOldPath, pDomain).toString());
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }
    return super.rename(pOldPath, pNewPath, pDomain);
  }

  @Override
  public Object list(String pPath, Domain pDomain) {

    Object folderList = super.list(pPath, pDomain);

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    String accessToken = mBearerAccessTokenManager.getByUser(userId).getId();
    try {
      String encryptedToken = encrypt(userId + ":" + accessToken);


      if (folderList instanceof List) {
        List list = (List) folderList;
        for (Object folder : list) {
          if (folder instanceof Map) {
            ((Map) folder).put(TOKEN, encryptedToken);
            String owner = ((Map<String, String>) folder).get(OWNER);
            if (!StringUtils.isEmpty(owner)) {
              owner = mUserManager.get(owner).getName();
            }
            ((Map<String, String>) folder).put(OWNER, owner);
          }
        }
      }
    } catch (Exception e) {
      return error(mMessageResource.getMessage("folder.listing.failed"));
    }
    return folderList;
  }


  private String encrypt(String plainText) throws Exception {
    return Base64.getEncoder().encodeToString(plainText.getBytes());
  }

  private static String decrypt(String plainText) throws Exception {
    return new String(Base64.getDecoder().decode(plainText));
  }

  private boolean isValidToken(final String pToken) {
    try {
      String decodedToken = decrypt(pToken);
      String[] splitToken = decodedToken.split(":");

      if (splitToken.length == 2) {
        BearerAccessToken userToken = mBearerAccessTokenManager.getByUser(splitToken[0]);
        if (userToken != null) {
          if (userToken.getId().equals(splitToken[1])) {
            return true;
          }
        }

      }
    } catch (Exception e) {
      mLogger.info("Token is not valid", e);
    }
    return false;
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }

  private boolean checkIfAllowed(final Path pPath) {
    if (mUMSConfiguration.isOwnerOnlyModification()) {
      try {
        String userId = getUserDefinedProperty(OWNER, pPath);
        if (!StringUtils.isEmpty(userId)) {
          String currentUserId = SecurityUtils.getSubject().getPrincipal().toString();
          if (!currentUserId.equalsIgnoreCase(userId)) {
            return false;
          }
        }
      } catch (Exception e) {
        mLogger.error("Can not find user", e);
        return false;
      }
    }
    return true;
  }
}
