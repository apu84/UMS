module ums {
  export enum FinancialYearType {
    CURRENT_YEAR = 1,
    PREVIOUS_YEAR = 2
  }
  export class PeriodCloseService {

  }

  UMS.service("PeriodCloseService", PeriodCloseService);
}