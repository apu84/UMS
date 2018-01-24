package org.ums.integration;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Monjur-E-Morshed on 16-Jul-17.
 */
@Component
public class FtpIntegration {

  @Autowired
  Environment mEnvironment;

  public SessionFactory<FTPFile> ftpSessionFactory() {
    DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
    sf.setHost("localhost");
    sf.setPort(21);
    sf.setUsername("iums");
    sf.setPassword("austig100");
    return new CachingSessionFactory<FTPFile>(sf);
  }

}
