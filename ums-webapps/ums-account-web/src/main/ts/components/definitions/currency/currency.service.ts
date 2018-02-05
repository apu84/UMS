module ums {

  export enum CurrencyFlag {
    BASE = 'B',
    OTHER = 'O'
  }

  export interface ICurrency {
    id: string;
    company: ICompany;
    currencyCode: string;
    currencyFlag: CurrencyFlag;
  }


  export class CurrencyService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/definition/currency";
    }


  }

  UMS.service("CurrencyService", CurrencyService);
}