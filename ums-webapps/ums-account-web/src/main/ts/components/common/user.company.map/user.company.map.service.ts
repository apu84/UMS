module ums {
  export class UserCompanyMapService {
    public companyName: string;

    public static $inject = ['$q', 'HttpClient'];

    private url = "company"

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {

    }
  }

  UMS.service("UserCompanyMapService", UserCompanyMapService);
}