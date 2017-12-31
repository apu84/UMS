module ums {
  export class ModalController {
    public static $inject = ['$scope', '$modalInstance','HttpClient', 'state', 'currentDefer'];

    constructor(private $scope: any,
                private $modalInstance: any,
                private httpClient: HttpClient,
                private state: string,
                private currentDefer: ng.IDeferred<any>) {
      this.$scope.ok = this.ok.bind(this);
      this.$scope.testTwoFa = this.testTwoFa.bind(this);
      this.$scope.tokenRef = {
        message: '',
        twofatoken: ''
      };
    }

    private ok(): void {
      this.$modalInstance.dismiss('cancel');
    }

    private testTwoFa(): ng.IPromise<any> {
      console.log('validating token...', this.$scope.tokenRef.twofatoken);
      this.$scope.tokenRef.message = '';
      return this.httpClient.post(`match-two-fa`, {"state": this.state, "token": this.$scope.tokenRef.twofatoken},
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => {
              this.currentDefer.resolve(response);
              this.ok();
            })
          .error((errorResponse) => {
            this.$scope.tokenRef.message = 'Failed to validate token';
            // this.currentDefer.resolve(errorResponse);
          });
    }
  }
  UMS.controller('ModalController', ModalController);
}
