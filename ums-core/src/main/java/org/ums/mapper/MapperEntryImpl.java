package org.ums.mapper;

import java.net.URI;

public class MapperEntryImpl implements MapperEntry {
  private String mEntity;
  private URI mUri;
  private String mMediaType;
  private String mMethod;

  public MapperEntryImpl(String pEntity, URI pUri, String pMediaType, String pMethod) {
    mEntity = pEntity;
    mUri = pUri;
    mMediaType = pMediaType;
    mMethod = pMethod;
  }

  @Override
  public String getEntity() {
    return mEntity;
  }

  @Override
  public URI getUri() {
    return mUri;
  }

  @Override
  public String getMediaType() {
    return mMediaType;
  }

  @Override
  public String getMethod() {
    return mMethod;
  }
}
