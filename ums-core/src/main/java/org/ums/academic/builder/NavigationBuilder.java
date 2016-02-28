package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.readOnly.Navigation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;


public class NavigationBuilder implements Builder<Navigation, MutableNavigation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Navigation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("title", pReadOnly.getTitle());
    pBuilder.add("parentMenu", pReadOnly.getParent() == null ? "" : String.valueOf(pReadOnly.getParent().getId()));
    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    pBuilder.add("location", pReadOnly.getLocation());
    pBuilder.add("iconContent", pReadOnly.getIconContent());
    pBuilder.add("status", pReadOnly.isActive());
  }

  @Override
  public void build(MutableNavigation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    //Do nothing for now
  }
}
