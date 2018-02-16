package org.ums.resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.solr.indexer.reindex.DocumentsTobeReIndexed;
import org.ums.solr.indexer.reindex.ReIndexer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
    for(ReIndexer indexer : mDocumentsTobeReIndexed.getReIndexers()) {
      indexer.reindex();
    }
    return Response.ok().build();
  }
}
