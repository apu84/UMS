module ums {
  export class PasswordReport {
    public static $inject = ['$scope', 'HttpClient', '$window', '$sce'];

    constructor(private $scope:any, private httpClient:HttpClient,
                private $window:ng.IWindowService, private $sce: ng.ISCEService) {
      $scope.submit = this.submit.bind(this);
    }

    private submit():void {
      this.httpClient.get('credentialReport/' + this.$scope.userId, 'application/pdf',
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