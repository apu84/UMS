package org.ums.academic.resource.fee.semesterfee;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.stereotype.Component;
import org.ums.fee.UGFee;
import org.ums.fee.latefee.UGLateFee;

@Component
class FeeConverter {
  JsonObject convert(UGFee pUGFee) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    jsonObject.add("name", pUGFee.getFeeCategory().getDescription());
    jsonObject.add("amount", pUGFee.getAmount());
    return jsonObject.build();
  }

  JsonObject convert(UGLateFee pUGLateFee) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    jsonObject.add("name", "Late fee");
    jsonObject.add("amount", pUGLateFee.getFee());
    return jsonObject.build();
  }
}
