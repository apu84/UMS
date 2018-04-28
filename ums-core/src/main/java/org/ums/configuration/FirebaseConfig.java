package org.ums.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Configuration
public class FirebaseConfig {

  public FirebaseConfig() throws IOException {
    initializeFirebaseApp();
  }

  private void initializeFirebaseApp() throws IOException {
    FileInputStream serviceAccount = new FileInputStream("D:\\IUMS-Source/service-account.json");

    FirebaseOptions options =
        new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

    FirebaseApp.initializeApp(options);
  }
}
