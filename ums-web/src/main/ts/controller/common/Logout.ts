///<reference path="../../service/CookieService.ts"/>
///<reference path="../../util/UriUtil.ts"/>
module ums {
  export class Logout {
    public static $inject = ['CookieService'];

    constructor(private cookieService:CookieService) {
      this.cookieService.removeAllCookies();
      window.location.href = UrlUtil.getBaseAppUrl() + 'login/';
    }
  }

  UMS.controller('Logout', Logout)
}