package org.ums.solr.indexer.resolver;

import java.util.List;

import org.ums.solr.indexer.model.Index;

public class EntityResolverFactoryImpl implements EntityResolverFactory {
  private List<EntityResolver> mEntityResolvers;

  public EntityResolverFactoryImpl(List<EntityResolver> pEntityResolvers) {
    mEntityResolvers = pEntityResolvers;
  }

  @Override
  public void resolve(Index pIndex) {
    for(EntityResolver entityResolver : mEntityResolvers) {
      if(entityResolver.getEntityType().equalsIgnoreCase(pIndex.getEntityType())) {
        entityResolver.resolve(pIndex);
      }
    }
  }
}
