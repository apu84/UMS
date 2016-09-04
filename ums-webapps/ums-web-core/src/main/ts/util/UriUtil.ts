module ums {
  export class UrlUtil {
    public static getBaseAppUrl() {
      return window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1) + 1);
    }
  }
}