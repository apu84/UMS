package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.LibraryCache;
import org.ums.cache.common.CountryCache;
import org.ums.cache.library.*;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.enums.library.ContributorCategory;
import org.ums.generator.IdGenerator;
import org.ums.manager.LibraryManager;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.*;
import org.ums.persistent.dao.PersistentLibraryDao;
import org.ums.persistent.dao.common.PersistentCountryDao;
import org.ums.persistent.dao.library.*;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class LibraryContext {
  @Autowired
  CacheFactory mCacheFactory;
  @Autowired
  JdbcTemplateFactory mTemplateFactory;
  @Autowired
  IdGenerator mIdGenerator;

  @Bean
  LibraryManager libraryManager() {
    LibraryCache libraryCache = new LibraryCache(mCacheFactory.getCacheManager());
    libraryCache.setManager(new PersistentLibraryDao(mTemplateFactory.getJdbcTemplate()));
    return libraryCache;
  }

  @Bean
  AuthorManager authorManager() {
    AuthorCache authorCache = new AuthorCache(mCacheFactory.getCacheManager());
    authorCache.setManager(new PersistentAuthorDao(mTemplateFactory.getLmsJdbcTemplate()));
    return authorCache;
  }

  @Bean
  CountryManager countryManager() {
    CountryCache countryCache = new CountryCache(mCacheFactory.getCacheManager());
    countryCache.setManager(new PersistentCountryDao(mTemplateFactory.getJdbcTemplate()));
    return countryCache;
  }

  @Bean
  SupplierManager supplierManager() {
    SupplierCache supplierCache = new SupplierCache(mCacheFactory.getCacheManager());
    supplierCache.setManager(new PersistentSupplierDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return supplierCache;
  }

  @Bean
  PublisherManager publisherManager() {
    PublisherCache publisherCache = new PublisherCache(mCacheFactory.getCacheManager());
    publisherCache.setManager(new PersistentPublisherDao(mTemplateFactory.getLmsJdbcTemplate()));
    return publisherCache;
  }

  @Bean
  RecordManager recordManager() {
    RecordCache recordCache = new RecordCache(mCacheFactory.getCacheManager());
    recordCache.setManager(new PersistentRecordDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return recordCache;
  }

  @Bean
  ItemManager itemManager() {
    ItemCache itemCache = new ItemCache(mCacheFactory.getCacheManager());
    itemCache.setManager(new PersistentItemDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return itemCache;
  }

  @Bean
  ContributorManager contributorManager() {
    ContributorCache contributorCache = new ContributorCache(mCacheFactory.getCacheManager());
    contributorCache.setManager(new PersistentContributorDao(mTemplateFactory.getLmsJdbcTemplate()));
    return contributorCache;
  }
}
