package org.ums.resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.ums.solr.indexer.reindex.DocumentsTobeReIndexed;
import org.ums.solr.indexer.reindex.ReIndexStatus;
import org.ums.solr.indexer.reindex.ReIndexer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/indexer")
@Produces(Resource.MIME_TYPE_JSON)
public class SolrIndexer extends Resource {
  @Autowired
  DocumentsTobeReIndexed mDocumentsTobeReIndexed;

  @GET
  @Path("/reindex")
  @RequiresPermissions("search:index")
  public Response reindex() throws Exception {
    asyncReindex();
    return Response.ok().build();
  }

  @GET
  @Path("/reindex/status")
  @RequiresPermissions("search:index")
  public List<ReIndexStatus> getStatus() {
    return Arrays.stream(mDocumentsTobeReIndexed.getReIndexers())
        .map(ReIndexer::status)
        .collect(Collectors.toList());
  }

  @Async
  private void asyncReindex() {
    Arrays.stream(mDocumentsTobeReIndexed.getReIndexers()).forEach(ReIndexer::reindex);
  }
}
