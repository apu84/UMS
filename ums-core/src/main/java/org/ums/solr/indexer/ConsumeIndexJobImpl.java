package org.ums.solr.indexer;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.ums.lock.LockManager;
import org.ums.lock.MutableLock;
import org.ums.lock.PersistentLock;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.model.IndexConsumer;
import org.ums.solr.indexer.model.MutableIndexConsumer;
import org.ums.solr.indexer.resolver.EntityResolverFactory;

public class ConsumeIndexJobImpl implements ConsumeIndex {
  private static final Logger mLogger = LoggerFactory.getLogger(ConsumeIndexJobImpl.class);

  private IndexManager mIndexManager;
  private IndexConsumerManager mIndexConsumerManager;
  private Environment environment;
  private LockManager mLockManager;
  private EntityResolverFactory mEntityResolverFactory;

  public ConsumeIndexJobImpl(IndexManager pIndexManager, IndexConsumerManager pIndexConsumerManager,
      Environment pEnvironment, EntityResolverFactory pEntityResolverFactory, LockManager pLockManager) {
    mIndexManager = pIndexManager;
    mIndexConsumerManager = pIndexConsumerManager;
    environment = pEnvironment;
    mEntityResolverFactory = pEntityResolverFactory;
    mLockManager = pLockManager;
  }

  @Override
  @Scheduled(fixedDelay = 30000, initialDelay = 60000)
  @Transactional
  public void consume() {
    // acquire lock
    MutableLock lock = new PersistentLock();
    lock.setId("indexLock");
    mLockManager.create(lock);

    List<String> endpoints = getEndPoints();
    if(endpoints.size() > 0) {
      String host = endpoints.get(0).split(":")[0];
      String port = endpoints.get(0).split(":")[1];
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
          mEntityResolverFactory.resolve(index);
        }
        consumer.setHead(indexList.get(indexList.size() - 1).getModified());
      }
      consumer.update();
    }
    mLockManager.delete(lock);
  }

  private void createNewIndexConsumer(String pHost, String pPort) {
    MutableIndexConsumer consumer = new PersistentIndexConsumer();
    consumer.setHost(pHost);
    consumer.setInstance(pPort);
    Date initialDate = new Date();
    initialDate.setTime(0);
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
}
