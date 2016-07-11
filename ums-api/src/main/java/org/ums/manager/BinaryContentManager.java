package org.ums.manager;

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


  List<Map<String, String>> list(String pPath, Domain pDomain);

  Map<String, String> rename(String pOldPath, String pNewPath, Domain pDomain);

  Map<String, String> move(List<String> pItems, String pNewPath, Domain pDomain);

  Map<String, String> copy(List<String> pItems, String pNewPath, Domain pDomain);

  Map<String, String> remove(List<String> pItems, Domain pDomain);

  Map<String, byte[]> content(String pPath, Domain pDomain);

  Map<String, String> createFolder(String pNewPath, Domain pDomain);

  Map<String, String> compress(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain);

  Map<String, String> extract(String pZippedItem, String pDestination, Domain pDomain);

  Map<String, String> upload(byte[] pFileContent, String pPath, Domain pDomain);

  byte[] download(String pPath, Domain pDomain);

  byte[] downloadAsZip(List<String> pItems, String pNewFileName, Domain pDomain);
}
