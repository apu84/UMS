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

  private String mDefaultAppName = "";

  @PostConstruct
  private void init() {
    config();
  }

  private void setDefaultAppName(final String pDefaultAppName) {
    mDefaultAppName = pDefaultAppName;
  }

  private String getDefaultAppName() {
    return mDefaultAppName;
  }

  private void config() {
    try {

      mLogger.info("--------------------- Getting before setting default app-----------------------------");
      mLogger.info(getDefaultAppName().isEmpty() ? "Yes, Its Empty" : "No it is not empty");
      mLogger.info(getDefaultAppName());

      FileInputStream serviceAccount = new FileInputStream(config);

      FirebaseOptions options =
          new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

      FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
      mLogger.info("------------------------- Printing Default App -------------------------------");
      mLogger.info(defaultApp.getName());

      this.setDefaultAppName(defaultApp.getName());

      mLogger.info("--------------------- Getting after setting default app-----------------------------");
      mLogger.info(getDefaultAppName());

    } catch(Exception e) {
      mLogger.error("Error in initializing firebase configuration: " + e.getMessage());
      mLogger.error("" + e);
    }
  }
}
