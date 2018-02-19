module ums {
  export class TwoFATestController {
    public static $inject = ['HttpClient', '$window', '$q'];

    constructor(private httpClient: HttpClient, private $window: ng.IWindowService,
                private $q: ng.IQService) {
    }

    private generateRequest(json: string): void {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.post(`two-fa-test`, json ? json : {"one": "1", "two": "2", "three": "3"},
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => console.log("Success response in controller: ", response))
          .error((errorResponse) => console.log("Error response in controller: ", errorResponse));
    }
  }

  UMS.controller("TwoFATestController", TwoFATestController);
}
