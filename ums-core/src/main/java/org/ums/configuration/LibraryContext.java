package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.ums.cache.CacheFactory;
import org.ums.cache.LibraryCache;
import org.ums.cache.library.*;
import org.ums.generator.IdGenerator;
import org.ums.manager.LibraryManager;
import org.ums.manager.library.*;
import org.ums.persistent.dao.PersistentLibraryDao;
import org.ums.persistent.dao.library.*;
import org.ums.solr.repository.transaction.lms.RecordTransaction;
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
  SupplierManager supplierManager() {
    SupplierCache supplierCache = new SupplierCache(mCacheFactory.getCacheManager());
    supplierCache.setManager(new PersistentSupplierDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return supplierCache;
  }

  @Bean
  PublisherManager publisherManager() {
    PublisherCache publisherCache = new PublisherCache(mCacheFactory.getCacheManager());
    publisherCache.setManager(new PersistentPublisherDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return publisherCache;
  }

  @Bean
  @Lazy
  RecordManager recordManager() {
    RecordTransaction recordTransaction = new RecordTransaction();
    PersistentRecordDao persistentRecordDao =
        new PersistentRecordDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator);
    recordTransaction.setManager(persistentRecordDao);
    RecordCache recordCache = new RecordCache(mCacheFactory.getCacheManager());
    recordCache.setManager(recordTransaction);
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
    contributorCache.setManager(new PersistentContributorDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return contributorCache;
  }

  @Bean
  CirculationManager checkoutManager() {
    CirculationCache checkoutCache = new CirculationCache(mCacheFactory.getCacheManager());
    checkoutCache.setManager(new PersistentCirculationDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return checkoutCache;
  }

  @Bean
  CheckInManager checkInManager() {
    CheckInCache checkInCache = new CheckInCache(mCacheFactory.getCacheManager());
    checkInCache.setManager(new PersistentCheckInDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return checkInCache;
  }

  @Bean
  FineManager fineManager() {
    FineCache fineCache = new FineCache(mCacheFactory.getCacheManager());
    fineCache.setManager(new PersistentFineDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator));
    return fineCache;
  }

  @Bean
  RecordLogManager recordLogManager() {
    return new PersistentRecordLogDao(mTemplateFactory.getLmsJdbcTemplate(), mIdGenerator);
  }
}
