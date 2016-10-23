package org.ums.builder;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Navigation;
import org.ums.domain.model.mutable.MutableNavigation;

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
  public void build(JsonObjectBuilder pBuilder, Navigation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("title", pReadOnly.getTitle());
    pBuilder.add("parentMenu",
        pReadOnly.getParent() == null ? "" : String.valueOf(pReadOnly.getParent().getId()));
    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    String location = pReadOnly.getLocation().replaceAll("#", "");
    if(location.length() > 0 && location.charAt(0) == '/') {
      location = location.substring(1);
    }

    if(location.contains("/")) {
      String[] urlParams = location.split("/");
      StringBuilder locationBuilder = new StringBuilder(urlParams[0]);
      locationBuilder.append("({");

      for(int i = 1; i < urlParams.length; i++) {
        if(i > 1) {
          locationBuilder.append(",");
        }
        locationBuilder.append("'").append(i).append("'").append(":").append("'")
            .append(urlParams[i]).append("'");
      }
      locationBuilder.append("})");
      location = locationBuilder.toString();
    }
    pBuilder.add("location", location); // commented out by Ifti( due to url parameter issue)

    Map menu = new HashMap();
    menu.put("index", pReadOnly.getViewOrder());
    menu.put("title", pReadOnly.getTitle());
    menu.put("icon_image_class", pReadOnly.getIconImgClass());
    menu.put("icon_color_class", pReadOnly.getIconColorClass());
    String a = pReadOnly.getLocation().replaceAll("/", "");
    a = a.replaceAll("#", "");
    menu.put("sref", a);

    String iconContentParent =
        VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine,
            "html-templates/parent-menu.vm", "UTF-8", menu);
    String iconContentChild =
        VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine,
            "html-templates/child-menu.vm", "UTF-8", menu);
    String singleContent =
        VelocityEngineUtils.mergeTemplateIntoString(mVelocityEngine,
            "html-templates/single-menu.vm", "UTF-8", menu);

    pBuilder
        .add("iconContent",
            pReadOnly.getParent() == null ? (a.equalsIgnoreCase("") ? iconContentParent
                : singleContent) : iconContentChild);

    pBuilder.add("status", pReadOnly.isActive());
  }

  @Override
  public void build(MutableNavigation pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {
    // Do nothing for now
  }
}
