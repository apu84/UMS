module ums{

  export interface ICurrencyConversion{
    id: string;
    company: ICompany;
    currency: ICurrency;
    conversionFactor: any;
    reverseConversionFactor: any;
    baseConversionFactor:any;
    reverseBaseConversionFactor: any;
    modifiedDate: string;
    modifiedBy: string;
  }

  export class CurrencyConversionService{

    public static $inject = ['$q', 'HttpClient'];

    private url: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.url= "account/definition/currency/conversion";
    }

    public getAll():ng.IPromise<ICurrencyConversion[]>{
      let defer:ng.IDeferred<ICurrencyConversion[]> = this.$q.defer();
      this.httpClient.get(this.url+"/all", HttpClient.MIME_TYPE_JSON,
          (response: any) => {
            console.log("----currency-conversion-----");
            console.log(response);
            defer.resolve(response);
          });
      return defer.promise;
    }

  }


  UMS.service("CurrencyConversionService", CurrencyConversionService);
}