package org.ums.configuration;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.ums.cache.CacheFactory;
import org.ums.generator.IdGenerator;
import org.ums.lock.LockDao;
import org.ums.lock.LockManager;
import org.ums.solr.indexer.ConsumeIndex;
import org.ums.solr.indexer.ConsumeIndexJobImpl;
import org.ums.solr.indexer.IndexConsumerDao;
import org.ums.solr.indexer.IndexDao;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.resolver.EmployeeResolver;
import org.ums.solr.indexer.resolver.EntityResolverFactory;
import org.ums.solr.indexer.resolver.EntityResolverFactoryImpl;
import org.ums.solr.indexer.resolver.lms.RecordResolver;
import org.ums.solr.repository.CustomSolrJConverter;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.EmployeeRepositoryImpl;
import org.ums.solr.repository.lms.RecordRepository;
import org.ums.solr.repository.lms.RecordRepositoryImpl;
import org.ums.statistics.JdbcTemplateFactory;

import com.google.common.collect.Lists;

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
  @Qualifier("backendSecurityManager")
  SecurityManager mSecurityManager;

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
    return new EntityResolverFactoryImpl(Lists.newArrayList(employeeResolver(), recordResolver()));
  }

  @Bean
  ConsumeIndex consumeIndex() throws Exception {
    return new ConsumeIndexJobImpl(indexManager(), indexConsumerManager(), entityResolverFactory(), lockManager(),
        mSecurityManager, mUMSConfiguration);
  }

  @Bean
  EmployeeRepository employeeRepository() throws Exception {
    return new EmployeeRepositoryImpl(solrTemplate());
  }
}
