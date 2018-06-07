module ums {
  export class ExpireToken {
    public static $inject = ['$window','$interval', 'HttpClient'];
    public static PERIOD: number = 2;
    constructor(private $window: ng.IWindowService,
                $interval: ng.IIntervalService,
                private httpClient: HttpClient) {
      $interval(() => {
        const token: Token = $window.sessionStorage.getItem(TOKEN_KEY) ?
            JSON.parse($window.sessionStorage.getItem(TOKEN_KEY)) : null;
        token.expires_in = token.expires_in - ExpireToken.PERIOD;
        if (token.expires_in <= 0) {
          this.refreshToken();
        }
        else {
          $window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(token));
        }
      }, ExpireToken.PERIOD * 1000, 0, false);
    }

    private refreshToken(){
      this.httpClient.get('refreshToken', HttpClient.MIME_TYPE_JSON, (newToken: Token) => {
        this.$window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(newToken));
        this.httpClient.resetAuthenticationHeader();
      });
    }
  }
  UMS.service('ExpireToken', ExpireToken);
}
