///<reference path="../../model/PasswordReset"/>
module ums {

  interface IPasswordReset extends ng.IScope {
    submit: Function;
    passwordReset:PasswordReset;
  }
  export class PasswordReport {
    public static $inject = ['$scope', 'HttpClient', '$window', '$sce'];

    constructor(private $scope:IPasswordReset, private httpClient:HttpClient,
                private $window:ng.IWindowService, private $sce: ng.ISCEService) {
      $scope.submit = this.submit.bind(this);
      //$scope.passwordReset.singleUser=true;
    }

    
    private submit():void {
      this.httpClient.get('credentialReport/' + this.$scope.passwordReset.userId, 'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }
  }

  UMS.controller("PasswordReport", PasswordReport);
}