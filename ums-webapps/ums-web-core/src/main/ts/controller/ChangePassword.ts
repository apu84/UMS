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

  interface TokenResponse {
    accessToken: string;
    refreshToken: string;
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
          .success((response: TokenResponse) => {
            this.$scope.response = {
              status: true,
              text: "Password changed successfully"
            };
            this.resetAuthentication(this.$scope.user, response);
          }).error((data) => {
            this.$scope.response = {
              status: false,
              text: "Failed to change password"
            };
          });
    }

    private resetAuthentication(user: User, token: TokenResponse): void {
      this.$window.sessionStorage.removeItem(HttpClient.CREDENTIAL_KEY);
      this.$window.sessionStorage.setItem(HttpClient.CREDENTIAL_KEY, token.accessToken);
      this.httpClient.resetAuthenticationHeader();
    }
  }

  UMS.controller("ChangePassword", ChangePassword);
}

