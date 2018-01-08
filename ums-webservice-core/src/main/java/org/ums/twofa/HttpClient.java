package org.ums.twofa;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;

import org.glassfish.jersey.client.ClientConfig;
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
      try {
        ClientConfig clientConfig = new ClientConfig();
        mClient = createClient(clientConfig);
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }
    return mClient;
  }

  /*
   * private Client createClient() { if(mUMSConfiguration.isDeveloperMode()) { SSLContext
   * sslcontext; try { sslcontext = SSLContext.getInstance("TLS"); } catch(NoSuchAlgorithmException
   * e) { throw new IllegalArgumentException("Couldn't get TLS instance", e); }
   * 
   * try { sslcontext.init(null, new TrustManager[] {new X509TrustManager() { public void
   * checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException
   * {}
   * 
   * public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws
   * CertificateException {}
   * 
   * public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
   * 
   * }}, new SecureRandom()); } catch(KeyManagementException e) { throw new
   * IllegalArgumentException("Couldn't override SSL context", e); } return
   * ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build(); }
   * else { return ClientBuilder.newClient(); } }
   */

  public Client createClient(Configuration config) throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext ctx = SSLContext.getInstance("SSL");
    ctx.init(null, certs, new SecureRandom());

    return ClientBuilder.newBuilder().withConfig(config).hostnameVerifier(new TrustAllHostNameVerifier())
        .sslContext(ctx).build();
  }

  TrustManager[] certs = new TrustManager[] {new X509TrustManager() {
    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
  }};

  public static class TrustAllHostNameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
      return true;
    }

  }
}
