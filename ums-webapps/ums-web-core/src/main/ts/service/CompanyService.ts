module ums {
  export class CompanyService {
    public static $inject = ['$q', 'HttpClient'];

    private url = "company"

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {


    }

    public getCompanyById(id: string): ng.IPromise<ICompany> {
      let defer: ng.IDeferred<ICompany> = this.$q.defer();
      this.httpClient.get(this.url + "/id/" + id, HttpClient.MIME_TYPE_JSON,
          (response: ICompany) => {
            defer.resolve(response);
          })

      return defer.promise;
    }

    public getAllCompany(): ng.IPromise<ICompany[]> {
      let defer: ng.IDeferred<ICompany[]> = this.$q.defer();
      this.httpClient.get(this.url + "/all", HttpClient.MIME_TYPE_JSON,
          (response: ICompany[]) => {
            defer.resolve(response);
          })
      return defer.promise;
    }

    public saveCompany(company: ICompany): ng.IPromise<ICompany> {
      let defer: ng.IDeferred<ICompany> = this.$q.defer();
      this.httpClient.post(this.url, company, HttpClient.MIME_TYPE_JSON)
          .success((response: ICompany) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            defer.resolve(undefined);
          })
      return defer.promise;
    }

    public updateCompany(company: ICompany): ng.IPromise<ICompany> {
      let defer: ng.IDeferred<ICompany> = this.$q.defer();
      this.httpClient.put(this.url, company, HttpClient.MIME_TYPE_JSON)
          .success((response: ICompany) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            defer.resolve(undefined);
          })
      return defer.promise;
    }

  }

  UMS.service("companyService", CompanyService);
}