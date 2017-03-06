package org.ums.indexer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.ums.indexer.manager.IndexConsumerManager;
import org.ums.indexer.manager.IndexManager;
import org.ums.indexer.model.Index;
import org.ums.indexer.model.IndexConsumer;
import org.ums.indexer.model.MutableIndexConsumer;
import org.ums.lock.LockManager;
import org.ums.lock.MutableLock;
import org.ums.lock.PersistentLock;

@Service
public class ConsumeIndexJobImpl implements ConsumeIndex {
  private static final Logger mLogger = LoggerFactory.getLogger(ConsumeIndexJobImpl.class);

  @Autowired
  IndexManager mIndexManager;

  @Autowired
  IndexConsumerManager mIndexConsumerManager;

  @Autowired
  Environment environment;

  @Autowired
  LockManager mLockManager;

  @Override
  @Scheduled(fixedDelay = 30000, initialDelay = 60000)
  @Transactional
  public void consume() {
    // acquire lock
    MutableLock lock = new PersistentLock();
    lock.setId("indexLock");
    mLockManager.create(lock);

    String port = environment.getProperty("local.server.port");
    Optional<String> host = getHost();
    host.ifPresent(pHost -> {
      MutableIndexConsumer consumer;
      if(mIndexConsumerManager.exists(pHost, port)) {
        IndexConsumer indexConsumer = mIndexConsumerManager.get(pHost, port);
        consumer = indexConsumer.edit();
      }
      else {
        consumer = new PersistentIndexConsumer();
        consumer.setHost(pHost);
        consumer.setInstance(port);
        consumer.setHead(0L);
      }

      List<Index> indexList = mIndexManager.after(consumer.getHead());
      if(indexList.size() > 0) {
        // SOLR index

        consumer.setHead(indexList.get(indexList.size() - 1).getId());
        if(consumer.getId() != null) {
          consumer.update();
        }
        else {
          consumer.create();
        }
      }
    });

    mLockManager.delete(lock);
  }

  private Long findHead(List<IndexConsumer> pIndexConsumerList) {
    if(pIndexConsumerList.size() == 0) {
      return 0L;
    }
    else if(pIndexConsumerList.size() == 1) {
      return pIndexConsumerList.get(0).getHead();
    }
    return 0L;
  }

  private Optional<String> getHost() {
    InetAddress ip;
    try {
      ip = InetAddress.getLocalHost();
      return Optional.of(ip.getHostName());
    } catch(UnknownHostException e) {
      mLogger.error("Can't resolve server host name for ConsumeIndexJob", e);
    }
    return Optional.empty();
  }
}
