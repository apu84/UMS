package org.ums.mapper;

import org.apache.commons.lang.SerializationUtils;
import org.mapdb.DB;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentMap;
import java.nio.file.Path;

public class MapperDao<K, V extends Serializable> implements Mapper<K, V> {
  private DB mDB;
  private String mMapName;

  public MapperDao(final UMSConfiguration pProviderConf, final String pFileName, final String pMapName) {
    String dbFile;
    if(!StringUtils.isEmpty(pProviderConf.getDataDir())) {
      dbFile = pProviderConf.getDataDir() + File.separator + pFileName;
    }
    else {
      dbFile = System.getProperty("java.io.tempDir") + File.separator + pFileName;
    }
    // mDB = DBMaker.fileDB(dbFile).fileLockDisable().make();
    mMapName = pMapName;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void save(final K pKey, final V pValue) {
    ConcurrentMap map = mDB.hashMap(mMapName).createOrOpen();
    map.put(pKey, SerializationUtils.serialize(pValue));
    mDB.commit();
  }

  @Override
  @SuppressWarnings("unchecked")
  public V lookup(final K pKey) {
    ConcurrentMap map = mDB.hashMap(mMapName).createOrOpen();
    V value = null;
    if(map.containsKey(pKey)) {
      value = (V) SerializationUtils.deserialize((byte[]) map.get(pKey));
    }
    mDB.commit();
    return value;
  }

  @Override
  public boolean contains(final K pState) {
    ConcurrentMap map = mDB.hashMap(mMapName).createOrOpen();
    return map.containsKey(pState);
  }
}
