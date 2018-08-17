package org.ums.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.integration.ftp.session.AbstractFtpSessionFactory;

public class UMSFtpSessionFactory extends AbstractFtpSessionFactory<FTPClient> {
  private int mMinActivePort = 3000;
  private int mMaxActivePort = 4000;

  @Override
  protected FTPClient createClientInstance() {
    FTPClient ftpClient = new FTPClient();
    ftpClient.setActivePortRange(mMinActivePort, mMaxActivePort);
    ftpClient.setRemoteVerificationEnabled(false);
    ftpClient.setUseEPSVwithIPv4(false);
    return ftpClient;
  }

  public int getMinActivePort() {
    return mMinActivePort;
  }

  public void setMinActivePort(int pMinActivePort) {
    mMinActivePort = pMinActivePort;
  }

  public int getMaxActivePort() {
    return mMaxActivePort;
  }

  public void setMaxActivePort(int pMaxActivePort) {
    mMaxActivePort = pMaxActivePort;
  }
}
