package org.ums.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrJConverter;
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
import org.ums.solr.repository.EmployeeRepository;
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
  @Lazy
  EmployeeRepository mEmployeeRepository;

  @Bean
  public SolrClient solrClient() {
    return new HttpSolrClient("http://localhost:8983/solr/");
  }

  @Bean
  public SolrTemplate solrTemplate(SolrClient client) throws Exception {
    SolrTemplate template = new SolrTemplate(client);
    template.setSolrConverter(new SolrJConverter());
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
  EmployeeResolver employeeResolver() {
    return new EmployeeResolver(mCoreContext.employeeManager(), mEmployeeRepository);
  }

  @Bean
  EntityResolverFactory entityResolverFactory() {
    return new EntityResolverFactoryImpl(Lists.newArrayList(employeeResolver()));
  }

  @Bean
  ConsumeIndex consumeIndex() {
    return new ConsumeIndexJobImpl(indexManager(), indexConsumerManager(), entityResolverFactory(), lockManager());
  }
}
