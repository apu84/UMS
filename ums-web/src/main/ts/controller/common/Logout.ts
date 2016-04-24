///<reference path="../../util/UriUtil.ts"/>
module ums {
  export class Logout {
    public static $inject = ['$window', 'HttpClient'];

    constructor(private $window: ng.IWindowService, private httpClient: HttpClient) {
      this.httpClient.get('logout', HttpClient.MIME_TYPE_JSON, (response) => {
        this.$window.sessionStorage.clear();
        window.location.href = UrlUtil.getBaseAppUrl() + 'login/';
      });
    }
  }

  UMS.controller('Logout', Logout)
}