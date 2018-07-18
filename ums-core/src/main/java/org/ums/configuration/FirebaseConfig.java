package org.ums.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

  private static final Logger mLogger = LoggerFactory.getLogger(FirebaseConfig.class);

  @Value("${fcm.config}")
  private String config;

  @PostConstruct
  private void init() {
    if(FirebaseApp.getApps().size() < 1) {
      mLogger.info("Initializing FirebaseApp instance ...");
      config();
    }
    else{
      mLogger.info("Trying to re-initialize FirebaseApp instance");
      mLogger.info("FirebaseApp already exists with app name: " + FirebaseApp.getInstance().getName());
    }
  }

  private void config() {
    try {
      FileInputStream serviceAccount = new FileInputStream(config);

      FirebaseOptions options =
          new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

      FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
    } catch(Exception e) {
      mLogger.error("Error occurred while initializing FirebaseApp : " + e.getMessage());
      mLogger.error("" + e);
    }
  }
}
