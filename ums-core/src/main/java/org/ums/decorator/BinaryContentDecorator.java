package org.ums.decorator;

import org.ums.manager.BinaryContentManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryContentDecorator implements BinaryContentManager<byte[]> {
  protected static final String SUCCESS = "success";
  protected static final String ERROR = "error";

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
}
