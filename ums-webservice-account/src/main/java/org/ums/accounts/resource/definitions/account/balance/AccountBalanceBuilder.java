package org.ums.accounts.resource.definitions.account.balance;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
@Component
public class AccountBalanceBuilder implements Builder<AccountBalance, MutableAccountBalance> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AccountBalance pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableAccountBalance pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    try {
      if(pJsonObject.containsKey("finStartDate"))
        pMutable.setFinStartDate(UmsUtils.convertToDate(pJsonObject.getString("finStartDate"), "dd-MM-yyyy"));
      if(pJsonObject.containsKey("finEndDate"))
        pMutable.setFinEndDate(UmsUtils.convertToDate(pJsonObject.getString("finEndDate"), "dd-MM-yyyy"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    if(pJsonObject.containsKey("accountCode"))
      pMutable.setAccountCode(Long.parseLong(pJsonObject.getString("accountCode")));
    if(pJsonObject.containsKey("yearOpenBalance"))
      pMutable.setYearOpenBalance(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getJsonNumber("yearOpenBalance")
          .toString())));
    if(pJsonObject.containsKey("yearOpenBalanceType"))
      pMutable.setYearOpenBalanceType(BalanceType.get(pJsonObject.getString("yearOpenBalanceType")));
    if(pJsonObject.containsKey("totMonthDbBal01"))
      pMutable.setTotMonthDbBal01(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal01"))));
    if(pJsonObject.containsKey("totMonthDbBal02"))
      pMutable.setTotMonthDbBal02(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal02"))));
    if(pJsonObject.containsKey("totMonthDbBal03"))
      pMutable.setTotMonthDbBal03(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal03"))));
    if(pJsonObject.containsKey("totMonthDbBal04"))
      pMutable.setTotMonthDbBal04(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal04"))));
    if(pJsonObject.containsKey("totMonthDbBal05"))
      pMutable.setTotMonthDbBal05(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal05"))));
    if(pJsonObject.containsKey("totMonthDbBal06"))
      pMutable.setTotMonthDbBal06(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal06"))));
    if(pJsonObject.containsKey("totMonthDbBal07"))
      pMutable.setTotMonthDbBal07(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal07"))));
    if(pJsonObject.containsKey("totMonthDbBal08"))
      pMutable.setTotMonthDbBal08(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal08"))));
    if(pJsonObject.containsKey("totMonthDbBal09"))
      pMutable.setTotMonthDbBal09(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal09"))));
    if(pJsonObject.containsKey("totMonthDbBal10"))
      pMutable.setTotMonthDbBal10(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal10"))));
    if(pJsonObject.containsKey("totMonthDbBal11"))
      pMutable.setTotMonthDbBal11(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal11"))));
    if(pJsonObject.containsKey("totMonthDbBal12"))
      pMutable.setTotMonthDbBal12(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getString("totMonthDbBal12"))));

  }
}
