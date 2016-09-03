module ums {
  export class CookieService {
    private static CREDENTIAL_KEY = 'ums.credentials';
    private static USER_KEY = 'ums.user';
    public static $inject = ['$log', '$cookies', '$cookieStore'];

    constructor(private $log:ng.ILogService, private $cookies:ng.cookies.ICookiesService, private $cookieStore:ng.cookies.ICookieStoreService) {
    }

    public getUserCredential():string {
      return this.$cookies[CookieService.CREDENTIAL_KEY];
    }
    public getCookieByKey(key):string { console.debug("%o", this.$cookieStore.get(key));
      return this.$cookieStore.get(key);
    }

    public getCookieAsJson(key):string {
      return this.$cookieStore.get(key);
    }

    public removeAllCookies(){
      var date = new Date();
      date.setTime(date.getTime()+(-1*24*60*60*1000));
      var expires = "; expires="+date.toUTCString();
      angular.forEach(this.$cookies, function (cookie, key) {
        document.cookie = key+"="+""+expires+"; path=/";
      });
    }

    public removeCookie(pKey:string) {
      var date = new Date();
      date.setTime(date.getTime() + (-1 * 24 * 60 * 60 * 1000));
      var expires = "; expires=" + date.toUTCString();
      angular.forEach(this.$cookies, function (cookie, key) {
        if (pKey == key) {
          document.cookie = key + "=" + "" + expires + "; path=/";
        }
      });
    }
  }

  UMS.service('CookieService', CookieService);

}