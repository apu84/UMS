package org.ums.common.builder;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.readOnly.Navigation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@Component
public class NavigationBuilder implements Builder<Navigation, MutableNavigation> {
  @Autowired
  VelocityEngine mVelocityEngine;

  @Override
  public void build(JsonObjectBuilder pBuilder, Navigation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("title", pReadOnly.getTitle());
    pBuilder.add("parentMenu", pReadOnly.getParent() == null ? "" : String.valueOf(pReadOnly.getParent().getId()));
    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    pBuilder.add("location", pReadOnly.getLocation());


    Map menu = new HashMap();
    menu.put("index", pReadOnly.getViewOrder());
    menu.put("title", pReadOnly.getTitle());
    menu.put("icon_image_class", pReadOnly.getIconImgClass());
    menu.put("icon_color_class", pReadOnly.getIconColorClass());
    String a = pReadOnly.getLocation().replaceAll("/", "");
    a = a.replaceAll("#", "");
    menu.put("sref", a);

    String iconContentParent = VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine, "html-templates/parent-menu.vm", "UTF-8", menu);
    String iconContentChild = VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine, "html-templates/child-menu.vm", "UTF-8", menu);
    String singleContent = VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine, "html-templates/single-menu.vm", "UTF-8", menu);

    pBuilder.add("iconContent", pReadOnly.getParent() == null ? (a.equalsIgnoreCase("") ? iconContentParent : singleContent) : iconContentChild);

    pBuilder.add("status", pReadOnly.isActive());
  }

  @Override
  public void build(MutableNavigation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    //Do nothing for now
  }
}
