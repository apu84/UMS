package org.ums.manager;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BinaryContentManager<T> {

  T get(final String pId, Domain pDomain) throws Exception;

  void put(T pData, String pId, Domain pDomain) throws Exception;

  void delete(String pId, Domain pDomain) throws Exception;

  String create(T pData, String pIdentifier, Domain pDomain) throws Exception;

  enum Domain {
    PICTURE(1),
    COURSE_MATERIAL(2)
    ;

    private static final Map<Integer, Domain> lookup
        = new HashMap<>();

    static {
      for (Domain c : EnumSet.allOf(Domain.class))
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


  List<Map<String, Object>> list(String pPath, Domain pDomain);

  Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain);

  Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain);

  Map<String, Object> copy(List<String> pItems, String pNewPath, Domain pDomain);

  Map<String, Object> remove(List<String> pItems, Domain pDomain);

  Map<String, byte[]> content(String pPath, Domain pDomain);

  Map<String, Object> createFolder(String pNewPath, Domain pDomain);

  Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain);

  Map<String, Object> extract(String pZippedItem, String pDestination, Domain pDomain);

  Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain);

  Map<String, Object> download(String pPath, String pToken, Domain pDomain);

  Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain);
}
