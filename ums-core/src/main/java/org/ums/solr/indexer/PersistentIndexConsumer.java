package org.ums.solr.indexer;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.model.MutableIndexConsumer;

class PersistentIndexConsumer implements MutableIndexConsumer {

  private static IndexConsumerManager sIndexConsumerManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sIndexConsumerManager =
        applicationContext.getBean("indexConsumerManager", IndexConsumerManager.class);
  }

  private Long mId;
  private String mHost;
  private String mInstance;
  private Date mHead;
  private Date mLastChecked;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getHost() {
    return mHost;
  }

  @Override
  public void setHost(String pHost) {
    this.mHost = pHost;
  }

  @Override
  public String getInstance() {
    return mInstance;
  }

  @Override
  public void setInstance(String pInstance) {
    this.mInstance = pInstance;
  }

  @Override
  public Date getHead() {
    return mHead;
  }

  @Override
  public void setHead(Date pHead) {
    this.mHead = pHead;
  }

  @Override
  public Date getLastChecked() {
    return mLastChecked;
  }

  @Override
  public void setLastChecked(Date pLastChecked) {
    this.mLastChecked = pLastChecked;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sIndexConsumerManager.create(this);
  }

  @Override
  public void update() {
    sIndexConsumerManager.update(this);
  }

  @Override
  public MutableIndexConsumer edit() {
    return new PersistentIndexConsumer(this);
  }

  @Override
  public void delete() {
    sIndexConsumerManager.delete(this);
  }

  PersistentIndexConsumer() {}

  PersistentIndexConsumer(MutableIndexConsumer pIndexConsumer) {
    setId(pIndexConsumer.getId());
    setHost(pIndexConsumer.getHost());
    setInstance(pIndexConsumer.getInstance());
    setHead(pIndexConsumer.getHead());
    setLastChecked(pIndexConsumer.getLastChecked());
    setLastModified(pIndexConsumer.getLastModified());
  }
}
