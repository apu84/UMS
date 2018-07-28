package org.ums.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

public class UMSFtpSessionFactory extends DefaultFtpSessionFactory {
  private int mMinActivePort = 3000;
  private int mMaxActivePort = 4000;

  protected void postProcessClientBeforeConnect(FTPClient ftpClient) throws IOException {
    ftpClient.setActivePortRange(mMinActivePort, mMaxActivePort);
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
