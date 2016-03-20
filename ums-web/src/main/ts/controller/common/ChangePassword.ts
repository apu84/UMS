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
      console.debug('%o',this.$scope.user);
    }

    private submit():void {
      this.httpClient.put('changePassword', this.$scope.password, 'application/json')
          .success((response) => {
            this.$scope.response = {
              status: true,
              text: "Password changed successfully"
            };
            this.resetAuthentication(this.$scope.user, response.token);
          }).error((data) => {
            this.$scope.response = {
              status: false,
              text: "Failed to change password"
            };
          });
    }

    private resetAuthentication(user: User, token: string): void {
      this.cookieService.removeCookie(CookieService.CREDENTIAL_KEY);
      var encoded = btoa(user.userName + ":" + token);
      var encodedToken = {
        credential:  "Basic " + encoded
      };
      document.cookie = CookieService.CREDENTIAL_KEY + "=" + JSON.stringify(encodedToken) + "; path=/";
      this.httpClient.resetAuthenticationHeader();
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

