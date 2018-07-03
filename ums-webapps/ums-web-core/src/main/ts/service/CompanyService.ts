module ums{
  export class CompanyService{
    public static $inject = ['$q', 'HttpClient'];

    private url = "company"

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {


    }

    public getCompanyById(id: string){

    }


  }

  UMS.controller("companyService", CompanyService);
}