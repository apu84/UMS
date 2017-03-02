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
import org.ums.indexer.manager.IndexConsumerManager;
import org.ums.indexer.manager.IndexManager;
import org.ums.indexer.model.Index;
import org.ums.indexer.model.IndexConsumer;

@Service
public class ConsumeIndexJobImpl implements ConsumeIndex {
  private static final Logger mLogger = LoggerFactory.getLogger(ConsumeIndexJobImpl.class);

  @Autowired
  IndexManager mIndexManager;

  @Autowired
  IndexConsumerManager mIndexConsumerManager;

  @Autowired
  Environment environment;

  @Override
  @Scheduled(fixedDelay = 30000, initialDelay = 60000)
  public void consume() {
    String port = environment.getProperty("local.server.port");
    Optional<String> host = getHost();
    host.ifPresent(pHost -> {
      Long head = findHead(mIndexConsumerManager.get(pHost));
      List<Index> indexList = mIndexManager.after(head);
    });
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
