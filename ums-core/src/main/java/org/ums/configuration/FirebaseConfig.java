package org.ums.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
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

  public void send() throws InterruptedException, ExecutionException {

    String registrationToken =
        "f0_9crYJEzs:APA91bHaAXBw5mnIFOvAf0ywYMWdKzxhbajqOUUacdPyDanG2kbhn8bkuBhaYIRkXUunm38AkS3iTmFsrkCe4MxPg9uL8tv38ctt3V3ZTv4-_cZ3qfwrG5EWvpKah1gljFZmHestmSIw";

    Message message =
        Message.builder().putData("message", "hello firebase, this is from IUMS")
            .setToken(registrationToken).build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    System.out.println("Sent message: " + response);
  }
}
