package org.ums.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Monjur-E-Morshed on 24-Apr-18.
 */
@Component
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    packages("...");
    property(ServletProperties.FILTER_FORWARD_ON_404, true);
  }
}
