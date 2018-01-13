package org.ums.builder;

import org.eclipse.collections.api.partition.bag.PartitionMutableBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.print.attribute.PrintJobAttributeSet;
import javax.ws.rs.core.UriInfo;

@Component
public class FineBuilder implements Builder<Fine, MutableFine> {
  @Autowired
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, Fine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("circulationId", pReadOnly.getCirculationId().toString());
    pBuilder.add("amount", pReadOnly.getAmount());
    pBuilder.add("fineCategory", pReadOnly.getFineCategory());
    pBuilder.add("description", pReadOnly.getDescription());
    pBuilder.add("fineAppliedDate", mDateFormat.format(pReadOnly.getFineAppliedDate()));
    pBuilder.add("fineAppliedBy", pReadOnly.getFineAppliedBy());
    pBuilder.add("fineForgivenBy", pReadOnly.getFineForgivenBy());
    pBuilder.add("finePaymentDate", mDateFormat.format(pReadOnly.getFinePaymentDate()));
  }

  @Override
  public void build(MutableFine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setPatronId(pJsonObject.getString("patronId"));
    pMutable.setCirculationId(Long.parseLong(pJsonObject.getString("circulationId")));
    pMutable.setAmount(pJsonObject.getInt("amount"));
    pMutable.setFineCategory(pJsonObject.getInt("fineCategory"));
    pMutable.setDescription(pJsonObject.getString("description"));
    pMutable.setFineAppliedBy(pJsonObject.getString("fineAppliedBy"));
    pMutable.setFineAppliedDate(mDateFormat.parse(pJsonObject.getString("fineAppliedDate")));
    pMutable.setFineForgivenBy(pJsonObject.getString("fineForgivenBy"));
    pMutable.setFinePaymentDate(mDateFormat.parse(pJsonObject.getString("finePaymentDate")));

  }
}
