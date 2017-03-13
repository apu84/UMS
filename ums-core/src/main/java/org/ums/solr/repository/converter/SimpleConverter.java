package org.ums.solr.repository.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.solr.repository.document.SearchDocument;

public class SimpleConverter<S, T extends SearchDocument> implements Converter<S, T> {
  private static final Logger mLogger = LoggerFactory.getLogger(SimpleConverter.class);

  private Class<T> targetClass;
  private Class<S> sourceClass;

  public SimpleConverter(Class<S> s, Class<T> t) {
    targetClass = t;
    sourceClass = s;
  }

  @Override
  public T convert(S pSource) {
    T instance = null;
    try {
      instance = targetClass.getConstructor(sourceClass).newInstance(pSource);
    } catch(Exception e) {
      mLogger.error("Exception while instantiating converter ", e);
    }
    return instance;
  }

  @Override
  public List<T> convert(List<S> pSourceList) {
    return pSourceList.stream().map(obj -> {
      T instance = null;
      try {
        instance = convert(obj);
      } catch(Exception e) {
        mLogger.error("Exception while instantiating converter ", e);
      }
      return instance;
    }).collect(Collectors.toList());
  }
}
