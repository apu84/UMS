package org.ums.manager;


import com.google.common.collect.Lists;
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
    Path path = Paths.get(mStorageRoot).resolve(Domain.get(pDomain.getValue()).toString());
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  protected void createIfNotExist(final Domain pDomain, final String pPath) throws Exception {
    Path path = Paths.get(mStorageRoot).resolve(Domain.get(pDomain.getValue()).toString()).resolve(pPath);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  protected Path getQualifiedPath(String pIdentifier, Domain pDomain) {
    return Paths.get(mStorageRoot).resolve(Domain.get(pDomain.getValue()).toString()).resolve(pIdentifier);
  }

  protected Path getQualifiedPath(Domain pDomain) {
    return Paths.get(mStorageRoot).resolve(Domain.get(pDomain.getValue()).toString());
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public void setStorageRoot(String pStorageRoot) {
    mStorageRoot = pStorageRoot;
  }

  @Override
  public List<Map<String, String>> list(String pPath, Domain pDomain) {
    Map<String, String> result = new HashMap<>();

    try {
      /*
      This is to make sure course material gets into folder structure like {/coursematerial/semestername/coursename/}.
      Initial request would be made with this.
       */
      createIfNotExist(pDomain, pPath);
    } catch (Exception e) {
      result.put(SUCCESS, null);
      result.put(ERROR, mMessageResource.getMessage("folder.creation.failed", pPath));
      return Lists.newArrayList(result);
    }

    Path targetDirectory = Paths.get(mStorageRoot).resolve(Domain.get(pDomain.getValue()).toString()).resolve(pPath);
    List<Map<String, String>> list = new ArrayList<>();

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(targetDirectory)) {
      for (Path path : directoryStream) {
        list.add(getPathDetails(path));
      }
    } catch (Exception ex) {
      result.put(SUCCESS, null);
      result.put(ERROR, mMessageResource.getMessage("folder.listing.failed"));
      return Lists.newArrayList(result);
    }

    return list;
  }

  protected Map<String, String> getPathDetails(Path pTargetPath) throws Exception {
    BasicFileAttributes attrs = Files.readAttributes(pTargetPath, BasicFileAttributes.class);
    Map<String, String> details = new HashMap<>();
    details.put(NAME, pTargetPath.getFileName().toString());
    details.put(RIGHTS, getPermissions(pTargetPath));
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
  public Map<String, String> rename(String pOldPath, String pNewPath, Domain pDomain) {
    Path root = getQualifiedPath(pDomain);
    Path oldPath = root.resolve(pOldPath);
    return null;
  }

  @Override
  public Map<String, String> move(List<String> pItems, String pNewPath, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> copy(List<String> pItems, String pNewPath, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> remove(List<String> pItems, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, byte[]> content(String pPath, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> createFolder(String pNewPath, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> extract(String pZippedItem, String pDestination, Domain pDomain) {
    return null;
  }

  @Override
  public Map<String, String> upload(byte[] pFileContent, String pPath, Domain pDomain) {
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
}
