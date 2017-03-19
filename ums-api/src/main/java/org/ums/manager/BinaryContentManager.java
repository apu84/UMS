package org.ums.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public interface BinaryContentManager<T> {

  T get(final String pId, Domain pDomain) throws IOException;

  void put(T pData, String pId, Domain pDomain) throws IOException;

  void delete(String pId, Domain pDomain) throws IOException;

  String create(T pData, String pIdentifier, Domain pDomain) throws IOException;

  enum Domain {
    PICTURE(1),
    COURSE_MATERIAL(2);

    private static final Map<Integer, Domain> lookup = new HashMap<>();

    static {
      for(Domain c : EnumSet.allOf(Domain.class))
        lookup.put(c.getValue(), c);
    }

    private int typeCode;

    private Domain(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Domain get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }

  Object list(String pPath, Map<String, String> pAdditionalParams, Domain pDomain, String... pRootPath);

  Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain, String... pRootPath);

  Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain, String... pRootPath);

  Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain,
      String... pRootPath);

  Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath);

  Map<String, byte[]> content(String pPath, Domain pDomain, String... pRootPath);

  Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams, Domain pDomain,
      String... pRootPath);

  Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate, Date pEndDate,
      Map<String, String> pAdditionalParams, Domain pDomain, String... pRootPath);

  Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain,
      String... pRootPath);

  Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain, String... pRootPath);

  Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain, String... pRootPath);

  Map<String, Object> download(String pPath, String pToken, Domain pDomain, String... pRootPath);

  Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain,
      String... pRootPath);
}
