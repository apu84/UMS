package org.ums.manager;


import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContentManager implements BinaryContentManager<byte[]> {
  private String mStorageRoot;

  @Override
  public byte[] get(String pId, Domain pDomain) {
    return new byte[0];
  }

  @Override
  public void put(byte[] pData, String pId, Domain pDomain) throws Exception {

  }

  @Override
  public void delete(String pId, Domain pDomain) throws Exception {

  }

  @Override
  public String create(byte[] pData, String pIdentifier, Domain pDomain) throws Exception {
   /* Path path = Paths.get(mStorageRoot + "/" + Domain.get(pDomain.getValue()).toString());
    if(path)
    FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_PATH + pJsonObject.getString("id"));
    fileOutputStream.write(imageData);
    fileOutputStream.flush();
    fileOutputStream.close();*/
    return null;
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public void setStorageRoot(String pStorageRoot) {
    mStorageRoot = pStorageRoot;
  }
}
