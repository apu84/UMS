package org.ums.twofa;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.UMSConfiguration;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class HttpClient1   {

  private TrustManager[] getTrustManager() {
    return new TrustManager[] { new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType)
          throws CertificateException {
        // Trust all servers
      }

      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType)
          throws CertificateException {
        // Trust all clients
      }
    } };
  }

  /**
   * Build a new Rest client with SSL security that trusts all certificates in
   * SSL/TLS.
   *
   * @return : new REST client
   * @throws Exception
   *           : generic exception in the rest client
   */

  public Client createClient() throws Exception {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, getTrustManager(), new SecureRandom());

      HostnameVerifier verifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostName, SSLSession sslSession) {
          return true;
        }
      };

      return ClientBuilder.newBuilder().sslContext(ctx).hostnameVerifier(verifier)
          .build();

    } catch (GeneralSecurityException exception) {
      // Log (or/and) throw exception
    }
    return null;
  }
}