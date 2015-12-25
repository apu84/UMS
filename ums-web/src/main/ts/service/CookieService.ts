module ums {
  export class CookieService {
    static CREDENTIAL_KEY = 'UMS.credentials';
    public static $inject = ['$log', '$cookies', '$cookieStore'];

    constructor(private $log:ng.ILogService, private $cookies:ng.cookies.ICookiesService, private $cookieStore:ng.cookies.ICookieStoreService) {
    }

    public getUserCredential():string {
      return this.$cookies[CookieService.CREDENTIAL_KEY];
    }
    public getCookieByKey(key):string {
      console.log("Cookie for credential in cookie service: " + this.$cookies[key]);
      return this.$cookies[key];
    }

    public getCookieAsJson(key):string {
      console.log("Cookie for credential in cookie service: " + this.$cookieStore.get(key));
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
  }

  UMS.service('CookieService', CookieService);

}