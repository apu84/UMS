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
    public static $inject = ['$scope', 'HttpClient', '$window'];

    constructor(private $scope: IChangePasswordScope, private httpClient: HttpClient,
                private $window: ng.IWindowService) {
      $scope.submit = this.submit.bind(this);
      console.debug('%o', this.$scope.user);
    }

    private submit(): void {
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
      this.$window.sessionStorage.removeItem(HttpClient.CREDENTIAL_KEY);
      var encoded = btoa(user.userName + ":" + token);
      this.$window.sessionStorage.setItem(HttpClient.CREDENTIAL_KEY, "Basic " + encoded);
      this.httpClient.resetAuthenticationHeader();
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

