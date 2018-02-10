module ums {

  export enum CurrencyFlag {
    BASE = 'B',
    OTHER = 'O'
  }

  export interface ICurrency {
    id: string;
    company: ICompany;
    currencyCode: string;
    currencyDescription:string;
    currencyFlag: CurrencyFlag;
    notation: string;
  }


  export class CurrencyService {

    private url: string;


    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService, private httpClient: HttpClient, private notify: Notify) {
      this.url = "account/definition/currency";
    }

    public getAllCurrencies():ng.IPromise<ICurrency[]>{
      let defer:ng.IDeferred<ICurrency[]> = this.$q.defer();
      this.httpClient.get(this.url+"/all", HttpClient.MIME_TYPE_JSON,
          (response:ICurrency[])=>defer.resolve(response));
      return defer.promise;
    }

  }

  UMS.service("CurrencyService", CurrencyService);
}