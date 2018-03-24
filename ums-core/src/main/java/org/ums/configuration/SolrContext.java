package org.ums.configuration;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.ums.cache.CacheFactory;
import org.ums.domain.model.immutable.library.Record;
import org.ums.generator.IdGenerator;
import org.ums.lock.LockDao;
import org.ums.lock.LockManager;
import org.ums.solr.indexer.IndexConsumerDao;
import org.ums.solr.indexer.IndexDao;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.reindex.DocumentsTobeReIndexed;
import org.ums.solr.indexer.reindex.EmployeeReIndexer;
import org.ums.solr.indexer.reindex.RecordReIndexer;
import org.ums.solr.indexer.resolver.EmployeeResolver;
import org.ums.solr.indexer.resolver.EntityResolverFactory;
import org.ums.solr.indexer.resolver.EntityResolverFactoryImpl;
import org.ums.solr.indexer.resolver.lms.RecordResolver;
import org.ums.solr.indexer.resolver.meeting.AgendaResolutionResolver;
import org.ums.solr.repository.CustomSolrJConverter;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.EmployeeRepositoryImpl;
import org.ums.solr.repository.converter.EmployeeConverter;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.lms.RecordDocument;
import org.ums.solr.repository.lms.RecordRepository;
import org.ums.solr.repository.lms.RecordRepositoryImpl;
import org.ums.solr.repository.meeting.AgendaResolutionRepository;
import org.ums.solr.repository.meeting.AgendaResolutionRepositoryImpl;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class SolrContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  CoreContext mCoreContext;

  @Autowired
  LibraryContext mLibraryContext;

  @Autowired
  RegistrarContext mRegistrarContext;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Bean
  public SolrClient solrClient() {
    return new HttpSolrClient("http://localhost:8983/solr");
  }

  @Bean
  public SolrTemplate solrTemplate() throws Exception {
    // without core name mentioned in here, custom solr repository doesn't get the core name by
    // default
    SolrTemplate template = new SolrTemplate(solrClient(), "ums");
    template.setSolrConverter(new CustomSolrJConverter());
    return template;
  }

  @Bean
  IndexManager indexManager() {
    return new IndexDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  IndexConsumerManager indexConsumerManager() {
    return new IndexConsumerDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  LockManager lockManager() {
    return new LockDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  EmployeeResolver employeeResolver() throws Exception {
    return new EmployeeResolver(mCoreContext.employeeManager(), employeeRepository());
  }

  @Bean
  RecordRepository recordRepository() throws Exception {
    return new RecordRepositoryImpl(solrTemplate());
  }

  @Bean
  RecordResolver recordResolver() throws Exception {
    return new RecordResolver(mLibraryContext.recordManager(), recordRepository());
  }

  @Bean
  EntityResolverFactory entityResolverFactory() throws Exception {
    return new EntityResolverFactoryImpl(Lists.newArrayList(employeeResolver(), recordResolver(),
        agendaResolutionResolver()));
  }

  @Bean
  EmployeeRepository employeeRepository() throws Exception {
    return new EmployeeRepositoryImpl(solrTemplate());
  }

  @Bean
  AgendaResolutionRepository agendaResolutionRepository() throws Exception {
    return new AgendaResolutionRepositoryImpl(solrTemplate());
  }

  @Bean
  AgendaResolutionResolver agendaResolutionResolver() throws Exception {
    return new AgendaResolutionResolver(mRegistrarContext.agendaResolutionManager(), agendaResolutionRepository());
  }

  @Bean
  DocumentsTobeReIndexed documentsTobeReIndexed() throws Exception {
    return new DocumentsTobeReIndexed(new EmployeeReIndexer(employeeRepository(), new EmployeeConverter(),
        mCoreContext.employeeManager()), new RecordReIndexer(recordRepository(), new SimpleConverter<>(Record.class,
        RecordDocument.class), mLibraryContext.recordManager()));
  }
}
