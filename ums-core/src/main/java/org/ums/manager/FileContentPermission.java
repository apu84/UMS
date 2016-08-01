package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.message.MessageResource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FileContentPermission extends BaseFileContentPermission {
  private static final Logger mLogger = LoggerFactory.getLogger(FileContentPermission.class);
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;

  public FileContentPermission(final UserManager pUserManager,
                               final BearerAccessTokenManager pBearerAccessTokenManager,
                               final UMSConfiguration pUMSConfiguration,
                               final MessageResource pMessageResource) {
    super(pBearerAccessTokenManager, pUserManager, pMessageResource);
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
  }


  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName,
                                           String pToken, Domain pDomain, String... pRootPath) {
    if (isValidToken(pToken)) {
      return super.downloadAsZip(pItems, pNewFileName, pToken, pDomain, pRootPath);
    } else {
      return null;
    }
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain, String... pRootPath) {
    if (isValidToken(pToken)) {
      return super.download(pPath, pToken, pDomain, pRootPath);
    } else {
      return null;
    }
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath,
                                    Domain pDomain, String... pRootPath) {
    Path uploadPath = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
    if (!checkIfAllowed(uploadPath)) {
      return error(mMessageResource.getMessage("file.upload.not.allowed", pPath));
    }
    return super.upload(pFileContent, pPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain, String... pRootPath) {
    // Check if there is permission to create folder
    Path parentPath = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath)).getParent();
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }
    return super.createFolder(pNewPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath) {
    for (String removedFile : pItems) {
      Path removedFilePath = getQualifiedPath(pDomain, buildPath(removedFile, pRootPath));

      if (!checkIfAllowed(removedFilePath)) {
        return error(mMessageResource.getMessage("folder.remove.not.allowed"));

      }
    }
    return super.remove(pItems, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName,
                                  Domain pDomain, String... pRootPath) {
    Path parentPath = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }

    for (String copiedFile : pItems) {
      Path copiedFilePath = getQualifiedPath(pDomain, buildPath(copiedFile, pRootPath));
      if (!checkIfAllowed(copiedFilePath)) {
        return error(mMessageResource.getMessage("folder.copy.not.allowed"));
      }
    }
    return super.copy(pItems, pNewPath, pNewFileName, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain, String... pRootPath) {
    Path parentPath = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.move.not.allowed"));
    }

    for (String movedFile : pItems) {
      Path movedFilePath = getQualifiedPath(pDomain, buildPath(movedFile, pRootPath));

      if (!checkIfAllowed(movedFilePath)) {
        return error(mMessageResource.getMessage("folder.move.not.allowed"));

      }
    }
    return super.move(pItems, pNewPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain, String... pRootPath) {
    Path parentPath = getQualifiedPath(pDomain, buildPath(pOldPath, pRootPath));
    if (!checkIfAllowed(parentPath)) {
      return error(mMessageResource.getMessage("folder.creation.not.allowed", pNewPath));
    }
    return super.rename(pOldPath, pNewPath, pDomain, pRootPath);
  }

  @Override
  public Object list(String pPath, Domain pDomain, String... pRootPath) {
    Object folderList = super.list(pPath, pDomain, pRootPath);
    return addOwnerToken(folderList);
  }

  @Override
  public Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate,
                                                    Date pEndDate, Domain pDomain, String... pRootPath) {
    return super.createAssignmentFolder(pNewPath, pStartDate, pEndDate, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain, String... pRootPath) {
    return super.compress(pItems, pNewPath, pNewFileName, pDomain, pRootPath);
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
        } else {
          //if there is no user info found on file/folder we take it as modifiable
          return true;
        }
      } catch (Exception e) {
        mLogger.error("Can not find user", e);
        return true;
      }
    }
    return true;
  }
}
