package org.ums.manager;


import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.message.MessageResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileContentManager implements BinaryContentManager<byte[]> {
  private String mStorageRoot;
  private static final String SUCCESS = "success";
  private static final String ERROR = "error";
  private static final String NAME = "name";
  private static final String RIGHTS = "rights";
  private static final String SIZE = "size";
  private static final String DATE = "date";
  private static final String TYPE = "type";
  private String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z"; // (Wed, 4 Jul 2001 12:08:56)
  private DateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);

  @Autowired
  MessageResource mMessageResource;

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
//    details.put(RIGHTS, getPermissions(pTargetPath));
    details.put(DATE, mDateFormat.format(new Date(attrs.lastModifiedTime().toMillis())));
    details.put(SIZE, attrs.size() + "");
    details.put(TYPE, attrs.isDirectory() ? "dir" : "file");
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
  public Map<String, Object> copy(List<String> pItems, String pNewPath, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);

    for (String oldPathString : pItems) {
      Path oldPath = root.resolve(oldPathString);
      Path newPath = root.resolve(pNewPath);
      File srcFile = oldPath.toFile();
      File destinationFile = newPath.toFile();

      try {
        if (srcFile.isFile()) {
          FileUtils.copyFile(srcFile, destinationFile);
        } else {
          FileUtils.copyDirectory(srcFile, destinationFile);
        }
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
      Path deletedPath = root.resolve(deletedItem);
      File deletedFile = deletedPath.toFile();

      try {
        if (!FileUtils.deleteQuietly(deletedFile)) {
          throw new Exception("Can't delete: " + deletedFile.getAbsolutePath());
        }
        return success();
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
  public Map<String, Object> upload(byte[] pFileContent, String pPath, Domain pDomain) {
    return null;
  }

  @Override
  public byte[] download(String pPath, Domain pDomain) {
    return new byte[0];
  }

  @Override
  public byte[] downloadAsZip(List<String> pItems, String pNewFileName, Domain pDomain) {
    return new byte[0];
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
}
