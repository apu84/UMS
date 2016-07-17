package org.ums.manager;


import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.ums.message.MessageResource;

import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileContentManager implements BinaryContentManager<byte[]> {
  private static final Logger mLogger = LoggerFactory.getLogger(FileContentManager.class);
  private String mStorageRoot;
  private static final String SUCCESS = "success";
  private static final String ERROR = "error";
  private static final String NAME = "name";
  private static final String RIGHTS = "rights";
  private static final String SIZE = "size";
  private static final String DATE = "date";
  private static final String TYPE = "type";
  private static final String OWNER = "owner";
  private String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";
  private DateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);

  @Autowired
  MessageResource mMessageResource;

  @Autowired
  @Lazy
  BearerAccessTokenManager mBearerAccessTokenManager;

  @Autowired
  @Lazy
  UserManager mUserManager;

  @Override
  public byte[] get(String pIdentifier, Domain pDomain) throws Exception {
    Path path = getQualifiedPath(pIdentifier, pDomain);
    if (!Files.exists(path)) {
      throw new FileNotFoundException();
    }
    return Files.readAllBytes(path);
  }

  @Override
  public void put(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
    Path filePath = getQualifiedPath(pIdentifier, pDomain);
    Files.createFile(filePath);
    Files.copy(new ByteArrayInputStream(pData), filePath, StandardCopyOption.REPLACE_EXISTING);
  }

  @Override
  public void delete(String pIdentifier, Domain pDomain) throws Exception {
    Path filePath = getQualifiedPath(pIdentifier, pDomain);
    Files.delete(filePath);
  }

  @Override
  public String create(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
    createIfNotExist(pDomain);
    Path newFilePath = getQualifiedPath(pIdentifier, pDomain);
    Files.createFile(newFilePath);
    Files.copy(new ByteArrayInputStream(pData), newFilePath, StandardCopyOption.REPLACE_EXISTING);
    return newFilePath.toString();
  }

  protected void createIfNotExist(final Domain pDomain) throws Exception {
    Path path = Paths.get(mStorageRoot, Domain.get(pDomain.getValue()).toString());
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  protected void createIfNotExist(final Domain pDomain, final String pPath) throws Exception {
    Path path = Paths.get(mStorageRoot, Domain.get(pDomain.getValue()).toString(), pPath);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
      addUser(SecurityUtils.getSubject().getPrincipal().toString(), path);
    }
  }

  protected Path getQualifiedPath(String pIdentifier, Domain pDomain) {
    return Paths.get(mStorageRoot, Domain.get(pDomain.getValue()).toString(), pIdentifier);
  }

  protected Path getQualifiedPath(Domain pDomain) {
    return Paths.get(mStorageRoot, Domain.get(pDomain.getValue()).toString());
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public void setStorageRoot(String pStorageRoot) {
    mStorageRoot = pStorageRoot;
  }


  @Override
  public List<Map<String, Object>> list(String pPath, Domain pDomain) {
    try {
      /*
      This is to make sure course material gets into folder structure like {/coursematerial/semestername/coursename/}.
      Initial request would be made with this.
       */
      createIfNotExist(pDomain, pPath);
    } catch (Exception e) {
      return Lists.newArrayList(error(mMessageResource.getMessage("folder.creation.failed", pPath)));
    }

    Path targetDirectory = Paths.get(mStorageRoot, Domain.get(pDomain.getValue()).toString(), pPath);

    List<Map<String, Object>> list = new ArrayList<>();

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(targetDirectory)) {
      for (Path path : directoryStream) {
        list.add(getPathDetails(path));
      }
    } catch (Exception ex) {
      return Lists.newArrayList(error(mMessageResource.getMessage("folder.listing.failed")));
    }

    return list;
  }

  protected Map<String, Object> getPathDetails(Path pTargetPath) throws Exception {
    BasicFileAttributes attrs = Files.readAttributes(pTargetPath, BasicFileAttributes.class);
    Map<String, Object> details = new HashMap<>();
    details.put(NAME, pTargetPath.getFileName().toString());
//    details.put(RIGHTS, "some valid hash");
    details.put(DATE, mDateFormat.format(new Date(attrs.lastModifiedTime().toMillis())));
    details.put(SIZE, attrs.size() + "");
    details.put(TYPE, attrs.isDirectory() ? "dir" : "file");
    details.put("token", mBearerAccessTokenManager.getByUser(SecurityUtils.getSubject().getPrincipal().toString()).getId());

    String userId = getUser(pTargetPath);
    if (!StringUtils.isEmpty(userId)) {
      userId = mUserManager.get(userId).getName();
    }
    details.put(OWNER, userId);

    return details;
  }

  private String getPermissions(Path path) throws IOException {
    // http://www.programcreek.com/java-api-examples/index.php?api=java.nio.file.attribute.PosixFileAttributes
    PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class);
    PosixFileAttributes readAttributes = fileAttributeView.readAttributes();
    Set<PosixFilePermission> permissions = readAttributes.permissions();
    return PosixFilePermissions.toString(permissions);
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);
    Path oldPath = Paths.get(root.toString(), pOldPath);

    Path newPath = Paths.get(root.toString(), pNewPath);
    try {
      Files.move(oldPath, oldPath.resolveSibling(newPath.toString()));
    } catch (Exception e) {
      return error(mMessageResource.getMessage("rename.failed"));
    }
    return success();
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);

    for (String oldPathString : pItems) {
      Path oldPath = Paths.get(root.toString(), oldPathString);
      Path newPath = Paths.get(root.toString(), pNewPath);
      File srcFile = oldPath.toFile();
      File destinationFile = newPath.toFile();

      try {
        if (srcFile.isFile()) {
          FileUtils.moveFileToDirectory(srcFile, destinationFile, true);
        } else {
          FileUtils.moveDirectoryToDirectory(srcFile, destinationFile, true);
        }
      } catch (Exception e) {
        return error(mMessageResource.getMessage("move.failed"));
      }
    }

    return success();
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);

    for (String oldPathString : pItems) {
      Path oldPath = Paths.get(root.toString(), oldPathString);
      Path newPath = Paths.get(root.toString(), pNewPath, pNewFileName);
      File srcFile = oldPath.toFile();
      File destinationFile = newPath.toFile();

      try {
        if (srcFile.isFile() && destinationFile.isDirectory()) {
          FileUtils.copyFileToDirectory(srcFile, destinationFile);
        } else if (srcFile.isDirectory() && destinationFile.isDirectory()) {
          FileUtils.copyDirectoryToDirectory(srcFile, destinationFile);
        } else {
          FileUtils.copyFile(srcFile, destinationFile);
        }

        addUser(SecurityUtils.getSubject().getPrincipal().toString(), Paths.get(newPath.toString(), srcFile.getName()));
      } catch (Exception e) {
        return error(mMessageResource.getMessage("copy.failed"));
      }
    }

    return success();
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);

    for (String deletedItem : pItems) {
      Path deletedPath = Paths.get(root.toString(), deletedItem);
      File deletedFile = deletedPath.toFile();

      try {
        if (!FileUtils.deleteQuietly(deletedFile)) {
          throw new Exception("Can't delete: " + deletedFile.getAbsolutePath());
        }
      } catch (Exception e) {
        return error(e.getMessage());
      }
    }

    return success();
  }

  @Override
  public Map<String, byte[]> content(String pPath, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain) {
    try {
      createIfNotExist(pDomain, pNewPath);
    } catch (Exception e) {
      return error(mMessageResource.getMessage("folder.creation.failed", pNewPath));
    }
    return success();
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain) {
    if (pFileContent.size() == 0) {
      return error("file size  = 0");
    } else {
      Path path = Paths.get(getQualifiedPath(pDomain).toString(), pPath);

      for (Map.Entry<String, InputStream> fileEntry : pFileContent.entrySet()) {
        Path filePath = Paths.get(path.toString(), fileEntry.getKey());
        try {
          Files.copy(fileEntry.getValue(), filePath, StandardCopyOption.REPLACE_EXISTING);
          addUser(SecurityUtils.getSubject().getPrincipal().toString(), filePath);
        } catch (Exception e) {
          error("Failed to upload file");
        }
      }
    }
    return success();
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain) {
    if (isValidToken(pToken)) {

      Map<String, Object> response = new HashMap<>();
      try {
        Path path = Paths.get(getQualifiedPath(pDomain).toString(), pPath);
        File file = path.toFile();
        response.put("Content-Type", Files.probeContentType(path));
        response.put("Content-Length", String.valueOf(file.length()));
        response.put("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        response.put("Content", Files.newInputStream(path));
        return response;
      } catch (Exception e) {
        mLogger.error("Failed to download file " + pPath, e);
        return null;
      }
    }

    return null;
  }

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain) {
    if (isValidToken(pToken)) {
      Map<String, Object> response = new HashMap<>();
      try {

        String tempDirectory = System.getProperty("java.io.tmpdir");
        Path zipfile = Paths.get(tempDirectory, pNewFileName);

        URI fileUri = zipfile.toUri();
        URI zipUri = new URI("jar:" + fileUri.getScheme(), fileUri.getPath(), null);

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
          for (String toAdd : pItems) {
            Path externalFile = Paths.get(getQualifiedPath(pDomain).toString(), toAdd);
            Path pathInZipfile = zipfs.getPath("/" + externalFile.getFileName());
            // copy a file into the zip file
            Files.copy(externalFile, pathInZipfile,
                StandardCopyOption.REPLACE_EXISTING);
          }
        }
        response.put("Content-Type", Files.probeContentType(zipfile));
        response.put("Content-Length", String.valueOf(zipfile.toFile().length()));
        response.put("Content-Disposition", "inline; filename=\"" + zipfile.toFile().getName() + "\"");
        response.put("Content", Files.newInputStream(zipfile));
        return response;
      } catch (Exception e) {
        mLogger.error("Failed to download file as zip " + pNewFileName, e);
        return null;
      }
    }
    return null;
  }

  protected boolean addUser(final String pUser, final Path pTargetPath) {
    try {
      if (!isUserDefinedAttributeSupported(pTargetPath)) {
        return false;
      }

      UserDefinedFileAttributeView view = Files.
          getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);

      view.write(OWNER, Charset.defaultCharset().encode(pUser));
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  protected String getUser(final Path pTargetPath) {
    try {
      if (!isUserDefinedAttributeSupported(pTargetPath)) {
        return "";
      }

      UserDefinedFileAttributeView view = Files.
          getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);

      int size = view.size(OWNER);
      ByteBuffer buf = ByteBuffer.allocateDirect(size);
      view.read(OWNER, buf);
      buf.flip();
      return Charset.defaultCharset().decode(buf).toString();
    } catch (Exception e) {
      return "";
    }
  }

  protected boolean isUserDefinedAttributeSupported(final Path pPath) {
    try {
      FileStore store = Files.getFileStore(pPath);
      if (!store.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
        throw new Exception(String.format("UserDefinedFileAttributeView not supported on %s\n", store));
      }
    } catch (Exception e) {
      mLogger.error("UserDefinedFileAttributeView not supported", e);
      return false;
    }

    return true;
  }

  protected Map<String, Object> success() {
    Map<String, Object> success = new HashMap<>();
    success.put(SUCCESS, true);
    success.put(ERROR, null);
    return success;
  }

  protected Map<String, Object> error(String msg) {
    Map<String, Object> error = new HashMap<>();
    error.put(SUCCESS, false);
    error.put(ERROR, msg);
    return error;
  }

  protected boolean isValidToken(final String pToken) {
    try {
      if (mBearerAccessTokenManager.get(pToken) == null) {
        return false;
      }
    } catch (Exception e) {
      mLogger.info("Token is not valid", e);
      return false;
    }
    return true;
  }
}
