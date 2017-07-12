package org.ums.integration;

import java.io.InputStream;

/**
 * Created by Monjur-E-Morshed on 08-Jul-17.
 */
public interface FileWriterGateway {
  // public void writeToFtpServer(@Header("fileName") String fileName, File file) throws
  // IOException;

  public InputStream read(String fileName);
}
