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
          .success((response: Token) => {
            this.$scope.response = {
              status: true,
              text: "Password changed successfully"
            };
            this.resetAuthentication(response);
          }).error((data) => {
            this.$scope.response = {
              status: false,
              text: "Failed to change password"
            };
          });
    }

    private resetAuthentication(token: Token): void {
      this.$window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(token));
      this.httpClient.resetAuthenticationHeader();
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

