package org.ums.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

  @Value("${fcm.config}")
  private String config;

  public FirebaseConfig() throws IOException {
    FileInputStream serviceAccount = new FileInputStream(config);

    FirebaseOptions options =
        new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

    FirebaseApp.initializeApp(options);
  }
}
