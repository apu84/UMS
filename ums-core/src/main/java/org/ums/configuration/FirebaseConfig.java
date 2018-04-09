package org.ums.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

  public FirebaseConfig() throws IOException {

    FileInputStream serviceAccount =
        new FileInputStream("D:\\IUMS-Source/push-notification-demo-6b41d-firebase-adminsdk-ca6ng-bdfc7855a5.json");

    FirebaseOptions options =
        new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://push-notification-demo-6b41d.firebaseio.com/").build();

    FirebaseApp.initializeApp(options);

  }
}
