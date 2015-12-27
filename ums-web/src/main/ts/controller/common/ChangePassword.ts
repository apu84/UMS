///<reference path="../../model/ChangePasswordModel.ts"/>
///<reference path="../../model/User.ts"/>
///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/CookieService.ts"/>

module ums {
  interface IChangePasswordScope extends ng.IScope {
    submit: Function;
    password:ChangePasswordModel;
    user: User;
    response: {
      status : boolean;
      text: string
    }
  }
  export class ChangePassword {
    public static $inject = ['$scope', 'HttpClient', 'CookieService'];

    constructor(private $scope:IChangePasswordScope, private httpClient:HttpClient,
                private cookieService:CookieService) {
      $scope.submit = this.submit.bind(this);
    }

    private submit():void {
      this.httpClient.put('changePassword', this.$scope.password, 'application/json')
          .success(() => {
            this.$scope.response = {
              status: true,
              text: "Password changed successfully"
            };
            this.resetAuthentication(this.$scope.user, this.$scope.password.newPassword);
          }).error((data) => {
            this.$scope.response = {
              status: false,
              text: "Failed to change password"
            };
          });
    }

    private resetAuthentication(user:User, password:string):void {
      this.cookieService.removeCookie(CookieService.CREDENTIAL_KEY);
      var credentials = "Basic " + btoa(user.username + ":" + password);
      document.cookie = CookieService.CREDENTIAL_KEY + "=" + credentials + "; path=/";
      this.httpClient.resetAuthenticationHeader(credentials);
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

