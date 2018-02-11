package org.ums.microservice.instance.consumeindex;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.ums.configuration.UMSConfiguration;
import org.ums.lock.LockManager;
import org.ums.lock.MutableLock;
import org.ums.lock.PersistentLock;
import org.ums.microservice.AbstractService;
import org.ums.solr.indexer.PersistentIndexConsumer;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.model.IndexConsumer;
import org.ums.solr.indexer.model.MutableIndexConsumer;
import org.ums.solr.indexer.resolver.EntityResolverFactory;

public class ConsumeIndexJobImpl extends AbstractService implements ConsumeIndex {
  private static final Logger mLogger = LoggerFactory.getLogger(ConsumeIndexJobImpl.class);

  private IndexManager mIndexManager;
  private IndexConsumerManager mIndexConsumerManager;
  private LockManager mLockManager;
  private EntityResolverFactory mEntityResolverFactory;
  private SecurityManager mSecurityManager;
  private UMSConfiguration mUMSConfiguration;

  public ConsumeIndexJobImpl(IndexManager pIndexManager, IndexConsumerManager pIndexConsumerManager,
      EntityResolverFactory pEntityResolverFactory, LockManager pLockManager, SecurityManager pSecurityManager,
      UMSConfiguration pUMSConfiguration) {
    mIndexManager = pIndexManager;
    mIndexConsumerManager = pIndexConsumerManager;
    mEntityResolverFactory = pEntityResolverFactory;
    mLockManager = pLockManager;
    mSecurityManager = pSecurityManager;
    mUMSConfiguration = pUMSConfiguration;
  }

  @Override
  @Scheduled(fixedDelay = 30000, initialDelay = 0)
  public void consume() {
    if(login()) {
      // acquire lock
      // MutableLock lock = new PersistentLock();
      // lock.setId("indexLock");
      // mLockManager.create(lock);
      String host = "microservice";
      String port = "8000";
      MutableIndexConsumer consumer;
      if(!mIndexConsumerManager.exists(host, port)) {
        createNewIndexConsumer(host, port);
      }
      IndexConsumer indexConsumer = mIndexConsumerManager.get(host, port);
      consumer = indexConsumer.edit();
      List<Index> indexList = mIndexManager.after(consumer.getHead());
      if(indexList.size() > 0) {
        // SOLR index
        for(Index index : indexList) {
          if(mLogger.isDebugEnabled()) {
            mLogger.debug("Indexing doc: {}, type {}", index.getEntityId(), index.getEntityType());
          }
          mEntityResolverFactory.resolve(index);
        }
        consumer.setHead(indexList.get(indexList.size() - 1).getModified());
      }
      consumer.update();
      // mLockManager.delete(lock);
    }
  }

  private void createNewIndexConsumer(String pHost, String pPort) {
    MutableIndexConsumer consumer = new PersistentIndexConsumer();
    consumer.setHost(pHost);
    consumer.setInstance(pPort);
    Date initialDate = new Date();
    // initialDate.setTime(0);
    consumer.setHead(initialDate);
    consumer.create();
  }

  private List<String> getEndPoints() {
    ArrayList<String> endPoints = new ArrayList<String>();
    try {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      Set<ObjectName> objs =
          mbs.queryNames(new ObjectName("*:type=Connector,*"),
              Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
      String hostname = InetAddress.getLocalHost().getHostName();
      InetAddress[] addresses = InetAddress.getAllByName(hostname);

      for(Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
        ObjectName obj = i.next();
        // String scheme = mbs.getAttribute(obj, "scheme").toString();
        String port = obj.getKeyProperty("port");
        for(InetAddress addr : addresses) {
          String host = addr.getHostAddress();
          String ep = host + ":" + port;
          endPoints.add(ep);
        }
      }
    } catch(Exception e) {
      mLogger.error("Exception while getting server address", e);
    }
    return endPoints;
  }

  @Override
  public void start() {
    // Do nothing
  }

  @Override
  protected SecurityManager getSecurityManager() {
    return mSecurityManager;
  }

  @Override
  protected UMSConfiguration getUMSConfiguration() {
    return mUMSConfiguration;
  }
}
