///<reference path="../../service/CookieService.ts"/>
///<reference path="../../util/UriUtil.ts"/>
module ums {
  export class Logout {
    public static $inject = ['CookieService', 'HttpClient'];

    constructor(private cookieService: CookieService, private httpClient: HttpClient) {
      this.httpClient.get('logout', HttpClient.MIME_TYPE_JSON, (response) => {
        this.cookieService.removeAllCookies();
        window.location.href = UrlUtil.getBaseAppUrl() + 'login/';
      });
    }
  }

  UMS.controller('Logout', Logout)
}