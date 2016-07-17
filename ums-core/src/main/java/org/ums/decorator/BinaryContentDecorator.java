package org.ums.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.manager.BinaryContentManager;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BinaryContentDecorator implements BinaryContentManager<byte[]> {
  private static Logger mLogger = LoggerFactory.getLogger(BinaryContentDecorator.class);
  protected static final String SUCCESS = "success";
  protected static final String ERROR = "error";
  protected static final String OWNER = "owner";

  private BinaryContentManager<byte[]> mManager;

  public BinaryContentManager<byte[]> getManager() {
    return mManager;
  }

  public void setManager(BinaryContentManager<byte[]> pManager) {
    mManager = pManager;
  }

  @Override
  public byte[] get(String pId, Domain pDomain) throws Exception {
    return getManager().get(pId, pDomain);
  }

  @Override
  public void put(byte[] pData, String pId, Domain pDomain) throws Exception {
    getManager().put(pData, pId, pDomain);
  }

  @Override
  public void delete(String pId, Domain pDomain) throws Exception {
    getManager().delete(pId, pDomain);
  }

  @Override
  public String create(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
    return getManager().create(pData, pIdentifier, pDomain);
  }

  @Override
  public Object list(String pPath, Domain pDomain) {
    return getManager().list(pPath, pDomain);
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain) {
    return getManager().rename(pOldPath, pNewPath, pDomain);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain) {
    return getManager().move(pItems, pNewPath, pDomain);
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    return getManager().copy(pItems, pNewPath, pNewFileName, pDomain);
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain) {
    return getManager().remove(pItems, pDomain);
  }

  @Override
  public Map<String, byte[]> content(String pPath, Domain pDomain) {
    return getManager().content(pPath, pDomain);
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain) {
    return getManager().createFolder(pNewPath, pDomain);
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain) {
    return getManager().compress(pItems, pNewPath, pNewFileName, pDomain);
  }

  @Override
  public Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain) {
    return getManager().extract(pZippedItem, pDestination, pDomain);
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain) {
    return getManager().upload(pFileContent, pPath, pDomain);
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain) {
    return getManager().download(pPath, pToken, pDomain);
  }

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain) {
    return getManager().downloadAsZip(pItems, pNewFileName, pToken, pDomain);
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


  protected void addUserDefinedProperty(final String pPropertyName,
                                        final String pPropertyValue,
                                        final Path pTargetPath) throws Exception {
    if (isUserDefinedAttributeSupported(pTargetPath)) {
      UserDefinedFileAttributeView view = Files.
          getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);
      view.write(pPropertyName, Charset.defaultCharset().encode(pPropertyValue));
    }
  }

  protected String getUserDefinedProperty(final String pPropertyName, final Path pTargetPath) throws Exception {
    if (isUserDefinedAttributeSupported(pTargetPath)) {
      UserDefinedFileAttributeView view = Files.
          getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);

      int size = view.size(pPropertyName);
      ByteBuffer buf = ByteBuffer.allocateDirect(size);
      view.read(pPropertyName, buf);
      buf.flip();
      return Charset.defaultCharset().decode(buf).toString();
    }
    return null;
  }

  private boolean isUserDefinedAttributeSupported(final Path pPath) {
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

  protected Path getQualifiedPath(String pIdentifier, Domain pDomain) {
    return Paths.get(getStorageRoot(), Domain.get(pDomain.getValue()).toString(), pIdentifier);
  }

  protected Path getQualifiedPath(Domain pDomain) {
    return Paths.get(getStorageRoot(), Domain.get(pDomain.getValue()).toString());
  }

  protected abstract String getStorageRoot();
}
