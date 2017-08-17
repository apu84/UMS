package org.ums.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
@Import({CoreContext.class, AcademicContext.class, AdmissionContext.class, FeeContext.class, LibraryContext.class,
    SolrContext.class, RegistrarContext.class})
public class UMSContext {

}
