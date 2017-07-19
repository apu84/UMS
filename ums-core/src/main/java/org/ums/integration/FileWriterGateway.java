package org.ums.integration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Monjur-E-Morshed on 08-Jul-17.
 */
public interface FileWriterGateway {

  public InputStream read(String fileName);

  public void write(@Header("fileName") String fileName, @Payload File pFile);

}
