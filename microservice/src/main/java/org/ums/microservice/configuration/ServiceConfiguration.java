package org.ums.microservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfiguration {
  @Value("${cachewarmer.app.id}")
  private String mCacheWarmerAppId;

  @Value("${cachewarmer.app.token}")
  private String mCacheWarmerAppToken;

  @Value("${indexer.app.id}")
  private String mIndexerAppId;

  @Value("${indexer.app.token}")
  private String mIndexerAppToken;

  @Value("${payment.validator.app.id}")
  private String mPaymentValidatorAppId;

  @Value("${payment.validator.app.token}")
  private String mPaymentValidatorAppToken;

  public String getCacheWarmerAppId() {
    return mCacheWarmerAppId;
  }

  public String getCacheWarmerAppToken() {
    return mCacheWarmerAppToken;
  }

  public String getIndexerAppId() {
    return mIndexerAppId;
  }

  public String getIndexerAppToken() {
    return mIndexerAppToken;
  }

  public String getPaymentValidatorAppId() {
    return mPaymentValidatorAppId;
  }

  public String getPaymentValidatorAppToken() {
    return mPaymentValidatorAppToken;
  }
}
