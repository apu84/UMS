///<reference path="../../model/master_data/Student.ts"/>
///<reference path="../../model/PasswordReset.ts"/>
module ums {
  interface UserInfo {
    userId: string;
    userName: string;
    department: string;
  }

  interface IPasswordReset extends ng.IScope {
    submit: Function;
    passwordReset:PasswordReset;
    passwordResetUser: UserInfo;
    passwordResetUserError: string;
    generatePdf: Function;
    viewTypeSingle: boolean;
    toggle: Function;
  }


  export class PasswordReport {
    public static $inject = ['$scope', 'HttpClient', '$window', '$sce'];

    constructor(private $scope: IPasswordReset, private httpClient: HttpClient,
                private $window: ng.IWindowService, private $sce: ng.ISCEService) {
      $scope.submit = this.submit.bind(this);
      $scope.generatePdf = this.generatePdf.bind(this);
      $scope.viewTypeSingle = true;
      $scope.toggle = this.toggle.bind(this);
    }

    private submit(): void {
      //TODO: Now it only checks for students, make it to work for all users
      delete this.$scope.passwordResetUser;
      delete this.$scope.passwordResetUserError;

      this.httpClient.get('academic/student/' + this.$scope.passwordReset.userId, 'application/json',
          (data: Student, etag: string) => {
            var user: UserInfo = {
              userId: data.id,
              userName: data.fullName,
              department: ''
            };

            if (data.department) {
              //TODO: This conversion of http to https to be done in backend
              var uri = data.department.replace("http", "https");
              console.debug(uri);
              this.httpClient.get(uri, 'application/json',
                  (data: any, etag: string)=> {
                    user.department = data.longName;
                    this.$scope.passwordResetUser = user;
                  });
            }
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            delete this.$scope.passwordResetUser;
            this.$scope.passwordResetUserError = 'User not found';
            console.error(response);
          });
    }

    private generatePdf(user: UserInfo): void {
      this.httpClient.get('credentialReport/' + user.userId, 'application/pdf',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

    private toggle(): void {
      this.$scope.viewTypeSingle = !this.$scope.viewTypeSingle;
    }
  }
  UMS.controller("PasswordReport", PasswordReport);
}