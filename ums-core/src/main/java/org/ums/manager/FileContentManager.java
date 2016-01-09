package org.ums.manager;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileContentManager implements BinaryContentManager<byte[]> {
  private String mStorageRoot;

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
    Path path = Paths.get(mStorageRoot + "/" + Domain.get(pDomain.getValue()).toString());
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  protected Path getQualifiedPath(String pIdentifier, Domain pDomain) {
    return Paths.get(mStorageRoot + "/" + Domain.get(pDomain.getValue()).toString() + "/" + pIdentifier);
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public void setStorageRoot(String pStorageRoot) {
    mStorageRoot = pStorageRoot;
  }
}
