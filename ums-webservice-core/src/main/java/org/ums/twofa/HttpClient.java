package org.ums.twofa;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.UMSConfiguration;

@Component
public class HttpClient {
  @Autowired
  private UMSConfiguration mUMSConfiguration;

  private Client mClient;

  public synchronized Client getClient() {
    if(mClient == null) {
      mClient = createClient();
    }
    return mClient;
  }

  private Client createClient() {
    if(mUMSConfiguration.isDeveloperMode()) {
      SSLContext sslcontext;
      try {
        sslcontext = SSLContext.getInstance("TLS");
      } catch(NoSuchAlgorithmException e) {
        throw new IllegalArgumentException("Couldn't get TLS instance", e);
      }

      try {
        sslcontext.init(null, new TrustManager[] {new X509TrustManager() {
          public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {}

          public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {}

          public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
          }

        }}, new SecureRandom());
      } catch(KeyManagementException e) {
        throw new IllegalArgumentException("Couldn't override SSL context", e);
      }
      return ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
    }
    else {
      return ClientBuilder.newClient();
    }
  }
}
