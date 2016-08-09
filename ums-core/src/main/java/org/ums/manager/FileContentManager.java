package org.ums.manager;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.message.MessageResource;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.*;
import java.util.*;

public class FileContentManager extends BinaryContentDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(FileContentManager.class);
  private String mStorageRoot;
  private static final String NAME = "name";
  private static final String SIZE = "size";
  private static final String DATE = "date";
  private static final String TYPE = "type";

  @Autowired
  MessageResource mMessageResource;

  @Override
  public byte[] get(String pIdentifier, Domain pDomain) throws Exception {
    Path path = getQualifiedPath(pDomain, pIdentifier);
    if (!Files.exists(path)) {
      throw new FileNotFoundException();
    }
    return Files.readAllBytes(path);
  }

  @Override
  public void put(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
    Path filePath = getQualifiedPath(pDomain, pIdentifier);
    Files.createFile(filePath);
    Files.copy(new ByteArrayInputStream(pData), filePath, StandardCopyOption.REPLACE_EXISTING);
  }

  @Override
  public void delete(String pIdentifier, Domain pDomain) throws Exception {
    Path filePath = getQualifiedPath(pDomain, pIdentifier);
    Files.delete(filePath);
  }

  @Override
  public String create(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
    createIfNotExist(pDomain);
    Path newFilePath = getQualifiedPath(pDomain, pIdentifier);
    Files.createFile(newFilePath);
    Files.copy(new ByteArrayInputStream(pData), newFilePath, StandardCopyOption.REPLACE_EXISTING);
    return newFilePath.toString();
  }

  protected void createIfNotExist(final Domain pDomain) throws Exception {
    Path path = getQualifiedPath(pDomain);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  protected void createIfNotExist(final Domain pDomain, final String pPath) throws Exception {
    Path path = getQualifiedPath(pDomain, pPath);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public void setStorageRoot(String pStorageRoot) {
    mStorageRoot = pStorageRoot;
  }


  @Override
  public Object list(String pPath, Domain pDomain, String... pRootPath) {
    try {
      /*
      This is to make sure course material gets into folder structure like {/coursematerial/semestername/coursename/}.
      Initial request would be made with this.
       */
      if (pPath.equalsIgnoreCase("/")) {
        createIfNotExist(pDomain, buildPath(pPath, pRootPath));
      }

    } catch (Exception e) {
      mLogger.error("Failed to create directory: " + pPath, e);
      return error(mMessageResource.getMessage("folder.creation.failed", pPath));
    }

    Path targetDirectory = getQualifiedPath(pDomain, buildPath(pPath,pRootPath));

    List<Map<String, Object>> list = new ArrayList<>();

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(targetDirectory)) {
      for (Path path : directoryStream) {
        list.add(getPathDetails(path));
      }
    } catch (Exception ex) {
      return error(mMessageResource.getMessage("folder.listing.failed"));
    }

    return list;
  }

  protected Map<String, Object> getPathDetails(Path pTargetPath) throws Exception {
    BasicFileAttributes attrs = Files.readAttributes(pTargetPath, BasicFileAttributes.class);
    Map<String, Object> details = new HashMap<>();
    details.put(NAME, pTargetPath.getFileName().toString());
//    details.put(RIGHTS, "some valid hash");
//    mDateFormat.setTimeZone( TimeZone.getTimeZone("UTC"));
    details.put(DATE, mDateFormat.format(new Date(attrs.lastModifiedTime().toMillis())));
    details.put(SIZE, attrs.size() + "");
    details.put(TYPE, attrs.isDirectory() ? "dir" : "file");
    String userId = getUserDefinedProperty(OWNER, pTargetPath);
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
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain, String... pRootPath) {
    Path oldPath = getQualifiedPath(pDomain, buildPath(pOldPath, pRootPath));
    Path newPath = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
    try {
      Files.move(oldPath, oldPath.resolveSibling(newPath.toString()));
    } catch (Exception e) {
      return error(mMessageResource.getMessage("rename.failed"));
    }
    return success();
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain, String... pRootPath) {

    for (String oldPathString : pItems) {
      Path oldPath = getQualifiedPath(pDomain, buildPath(oldPathString, pRootPath));
      Path newPath = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
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
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain,
                                  String... pRootPath) {
    for (String oldPathString : pItems) {
      Path oldPath = getQualifiedPath(pDomain, buildPath(oldPathString, pRootPath));
      Path newPath = getQualifiedPath(pDomain, buildPath(buildPath(pNewFileName, pNewPath), pRootPath));
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

        addUserDefinedProperty(OWNER, SecurityUtils.getSubject().getPrincipal().toString(),
            Paths.get(newPath.toString(), srcFile.getName()));
      } catch (Exception e) {
        return error(mMessageResource.getMessage("copy.failed"));
      }
    }

    return success();
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath) {
    for (String deletedItem : pItems) {
      Path deletedPath = getQualifiedPath(pDomain, buildPath(deletedItem, pRootPath));
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
  public Map<String, byte[]> content(String pPath, Domain pDomain, String... pRootPath) {
    return null;
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams, Domain pDomain, String... pRootPath) {
    try {
      createIfNotExist(pDomain, buildPath(pNewPath, pRootPath));
      addUserDefinedProperty(OWNER, SecurityUtils.getSubject().getPrincipal().toString(),
          getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath)));

      Path targetDirectory = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
      if (pAdditionalParams != null) {
        for (String key : pAdditionalParams.keySet()) {
          addUserDefinedProperty(key, pAdditionalParams.get(key), targetDirectory);
        }
      }
    } catch (Exception e) {
      return error(mMessageResource.getMessage("folder.creation.failed", pNewPath));
    }
    return success();
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName,
                                      Domain pDomain, String... pRootPath) {
    try {
      Path zipFile = getQualifiedPath(pDomain, buildPath(pNewPath +"/"+ pNewFileName, pRootPath));

      URI fileUri = zipFile.toUri();
      URI zipUri = new URI("jar:" + fileUri.getScheme(), fileUri.getPath(), null);

      Map<String, String> env = new HashMap<>();
      env.put("create", "true");

      String rootPath = getQualifiedPath(pDomain, buildPath("", pRootPath)).toString();


      try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
        for (String toAdd : pItems) {
          Path externalFile = getQualifiedPath(pDomain, buildPath(toAdd, pRootPath));
          Path pathInZipfile = zipfs.getPath("/");

          if (Files.isDirectory(externalFile)) {
            //for directories, walk the file tree
            Files.walkFileTree(externalFile, new SimpleFileVisitor<Path>() {
              @Override
              public FileVisitResult visitFile(Path file,
                                               BasicFileAttributes attrs) throws IOException {
                final Path dest = zipfs.getPath(pathInZipfile.toString(), file.toString().replace(externalFile.getParent().toString(), ""));
                Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
              }

              @Override
              public FileVisitResult preVisitDirectory(Path dir,
                                                       BasicFileAttributes attrs) throws IOException {
                String targetDirectory = dir.toString().replace(externalFile.getParent().toString(), "");
                final Path dirToCreate = zipfs.getPath(pathInZipfile.toString(), targetDirectory);
                if (Files.notExists(dirToCreate)) {
                  Files.createDirectories(dirToCreate);
                }
                return FileVisitResult.CONTINUE;
              }
            });
          } else {
            final Path dest = zipfs.getPath(pathInZipfile.toString(), externalFile.toString().replace(externalFile.getParent().toString(), ""));
            Files.copy(externalFile, dest, StandardCopyOption.REPLACE_EXISTING);
          }
        }
      }
      addUserDefinedProperty(OWNER, SecurityUtils.getSubject().getPrincipal().toString(),
          zipFile);
      return success();
    } catch (Exception e) {
      mLogger.error("Failed to download file as zip " + pNewFileName, e);
      return error(mMessageResource.getMessage("compress.failed"));
    }
  }

  @Override
  public Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain, String... pRootPath) {
    return null;
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain, String... pRootPath) {
    if (pFileContent.size() == 0) {
      return error("file size  = 0");
    } else {
      Path path = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));

      for (Map.Entry<String, InputStream> fileEntry : pFileContent.entrySet()) {
        Path filePath = Paths.get(path.toString(), fileEntry.getKey());
        try {
          Files.copy(fileEntry.getValue(), filePath, StandardCopyOption.REPLACE_EXISTING);
          addUserDefinedProperty(OWNER, SecurityUtils.getSubject().getPrincipal().toString(), filePath);
        } catch (Exception e) {
          error("Failed to upload file");
        }
      }
    }
    return success();
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain, String... pRootPath) {
    Map<String, Object> response = new HashMap<>();
    try {
      Path path = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
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

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain, String... pRootPath) {
    Map<String, Object> response = new HashMap<>();
    try {

      String tempDirectory = System.getProperty("java.io.tmpdir");
      Path zipFile = Paths.get(tempDirectory, pNewFileName);

      URI fileUri = zipFile.toUri();
      URI zipUri = new URI("jar:" + fileUri.getScheme(), fileUri.getPath(), null);

      Map<String, String> env = new HashMap<>();
      env.put("create", "true");

      try (FileSystem zipfs = FileSystems.newFileSystem(zipUri, env)) {
        for (String toAdd : pItems) {
          Path externalFile = getQualifiedPath(pDomain, buildPath(toAdd, pRootPath));
          Path pathInZipfile = zipfs.getPath("/" + externalFile.getFileName());
          // copy a file into the zip file
          Files.copy(externalFile, pathInZipfile,
              StandardCopyOption.REPLACE_EXISTING);
        }
        zipfs.close();
      }

      //TODO: Need to investigate more why Files.probeContentType returning null .
     //response.put("Content-Type", Files.probeContentType(zipFile));
      response.put("Content-Type", "application/zip, application/octet-stream");
      response.put("Content-Length", String.valueOf(zipFile.toFile().length()));
      response.put("Content-Disposition", "inline; filename=\"" + zipFile.toFile().getName() + "\"");
      response.put("Content", Files.newInputStream(zipFile));
      return response;
    } catch (Exception e) {
      mLogger.error("Failed to download file as zip " + pNewFileName, e);
      return null;
    }
  }

  @Override
  public Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate,
                                                    Date pEndDate, Domain pDomain,
                                                    String... pRootPath) {
    Map<String, Object> createFolderResponse;

    if (pStartDate != null
        && pEndDate != null
        && pStartDate.before(pEndDate)) {

      createFolderResponse = createFolder(pNewPath, null, pDomain, pRootPath);
      Path assignmentFolder = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
      try {
        addUserDefinedProperty(OWNER, SecurityUtils.getSubject().getPrincipal().toString(), assignmentFolder);
        addUserDefinedProperty(START_DATE, mDateFormat.format(pStartDate), assignmentFolder);
        addUserDefinedProperty(END_DATE, mDateFormat.format(pEndDate), assignmentFolder);
        addUserDefinedProperty(FOLDER_TYPE, "assignment", assignmentFolder);
      } catch (Exception e) {
        return error("Failed to add user");
      }
    } else {
      return error("Assignment submission dates are no valid");
    }
    return createFolderResponse;
  }
}
