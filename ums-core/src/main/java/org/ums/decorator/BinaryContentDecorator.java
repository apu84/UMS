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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BinaryContentDecorator implements BinaryContentManager<byte[]> {
  private static Logger mLogger = LoggerFactory.getLogger(BinaryContentDecorator.class);
  protected static final String SUCCESS = "success";
  protected static final String ERROR = "error";
  protected static final String OWNER = "owner";
  protected static final String START_DATE = "startDate";
  protected static final String END_DATE = "endDate";
  protected static final String FOLDER_TYPE = "type";
  protected static final String FOLDER_TYPE_ASSIGNMENT = "assignment";
  protected static final String FOLDER_TYPE_STUDENT_ASSIGNMENT = "studentAssignment";
  protected static final String SEMESTER_ID = "semesterId";
  protected static final String COURSE_ID = "courseId";
  protected final static String TOKEN = "token";
  protected String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss Z";
  protected DateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);

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
  public Object list(String pPath, Map<String, String> pAdditionalParams, Domain pDomain,
      String... pRootPath) {
    return getManager().list(pPath, pAdditionalParams, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain,
      String... pRootPath) {
    return getManager().rename(pOldPath, pNewPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain,
      String... pRootPath) {
    return getManager().move(pItems, pNewPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName,
      Domain pDomain, String... pRootPath) {
    return getManager().copy(pItems, pNewPath, pNewFileName, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath) {
    return getManager().remove(pItems, pDomain, pRootPath);
  }

  @Override
  public Map<String, byte[]> content(String pPath, Domain pDomain, String... pRootPath) {
    return getManager().content(pPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams,
      Domain pDomain, String... pRootPath) {
    return getManager().createFolder(pNewPath, pAdditionalParams, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName,
      Domain pDomain, String... pRootPath) {
    return getManager().compress(pItems, pNewPath, pNewFileName, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain,
      String... pRootPath) {
    return getManager().extract(pZippedItem, pDestination, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath,
      Domain pDomain, String... pRootPath) {
    return getManager().upload(pFileContent, pPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain,
      String... pRootPath) {
    return getManager().download(pPath, pToken, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken,
      Domain pDomain, String... pRootPath) {
    return getManager().downloadAsZip(pItems, pNewFileName, pToken, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate,
      Date pEndDate, Map<String, String> pAdditionalParams, Domain pDomain, String... pRootPath) {
    return getManager().createAssignmentFolder(pNewPath, pStartDate, pEndDate, pAdditionalParams,
        pDomain, pRootPath);
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

  protected void addUserDefinedProperty(final String pPropertyName, final String pPropertyValue,
      final Path pTargetPath) throws Exception {
    if(isUserDefinedAttributeSupported(pTargetPath)) {
      UserDefinedFileAttributeView view =
          Files.getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);
      view.write(pPropertyName, Charset.defaultCharset().encode(pPropertyValue));
    }
  }

  protected String getUserDefinedProperty(final String pPropertyName, final Path pTargetPath) {
    try {
      if(isUserDefinedAttributeSupported(pTargetPath)) {
        UserDefinedFileAttributeView view =
            Files.getFileAttributeView(pTargetPath, UserDefinedFileAttributeView.class);

        int size = view.size(pPropertyName);
        ByteBuffer buf = ByteBuffer.allocateDirect(size);
        view.read(pPropertyName, buf);
        buf.flip();
        return Charset.defaultCharset().decode(buf).toString();
      }
    } catch(Exception e) {
      mLogger.error("Can not find user defined property named " + pPropertyName, e);
    }
    return null;
  }

  protected String getUserDefinedProperty(final String pPropertyName, final Path pTargetPath,
      final Path pParentPath) {
    try {
      String propertyValue = getUserDefinedProperty(pPropertyName, pTargetPath);
      return propertyValue == null ? (Files.isSameFile(pParentPath, pTargetPath) ? null
          : getUserDefinedProperty(pPropertyName, pTargetPath.getParent())) : propertyValue;
    } catch(Exception e) {
      mLogger.error("Can not find user defined property named " + pPropertyName, e);
    }
    return null;
  }

  private boolean isUserDefinedAttributeSupported(final Path pPath) {
    try {
      FileStore store = Files.getFileStore(pPath);
      if(!store.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
        throw new Exception(String.format("UserDefinedFileAttributeView not supported on %s\n",
            store));
      }
    } catch(Exception e) {
      mLogger.error("UserDefinedFileAttributeView not supported", e);
      return false;
    }

    return true;
  }

  protected Path getQualifiedPath(Domain pDomain, String... pPaths) {
    Path root = Paths.get(getStorageRoot(), Domain.get(pDomain.getValue()).toString());
    return Paths.get(root.toString(), pPaths);
  }

  protected Path getQualifiedPath(Domain pDomain) {
    return Paths.get(getStorageRoot(), Domain.get(pDomain.getValue()).toString());
  }

  protected String buildPath(String pPath, String... pRootPath) {
    Path rootPath = Paths.get("", pRootPath);
    return Paths.get(rootPath.toString(), pPath).toString();
  }

  protected abstract String getStorageRoot();
}
