module ums {
  export class ExpireToken {
    public static $inject = ['$window', '$timeout', '$interval', 'HttpClient'];

    constructor($window: ng.IWindowService,
                $timeout: ng.ITimeoutService,
                $interval: ng.IIntervalService,
                httpClient: HttpClient) {
      $timeout(() => {
        const token: Token = $window.sessionStorage.getItem(TOKEN_KEY) ?
            JSON.parse($window.sessionStorage.getItem(TOKEN_KEY)) : null;
        if (token) {
          $interval(() => {
            httpClient.get('refreshToken', HttpClient.MIME_TYPE_JSON, (newToken: Token) => {
              $window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(newToken));
              httpClient.resetAuthenticationHeader();
            });
          }, token.expires_in * 1000, 0, false);
        }
      }, 10000, false);
    }
  }
  UMS.service('ExpireToken', ExpireToken);
}
