package org.ums.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FcmSettings {

    @Value("${fcm.service-account-file}")
    private String serviceAccountFile;

    public String getServiceAccountFile() {
        return this.serviceAccountFile;
    }

    public void setServiceAccountFile(String serviceAccountFile) {
        this.serviceAccountFile = serviceAccountFile;
    }

}
