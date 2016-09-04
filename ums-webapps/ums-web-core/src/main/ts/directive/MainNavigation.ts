module ums {
  interface MainNavigationScope extends ng.IScope {
    menuItems : any;
  }
  export class MainNavigation implements ng.IDirective {
    static TEMPLATE_URL = "./views/common/navigation.html";

    constructor(private httpClient: HttpClient,
                private $templateCache: any) {

    }

    public restrict: string = "A";

    public scope = true;

    public link = (scope: MainNavigationScope, element: JQuery, attr: any) => {
      //Load main navigation (left side menu)
      this.httpClient.get("mainNavigation", HttpClient.MIME_TYPE_JSON,
          (data: any, etag: string) => {
            console.debug("data: %o", data.entries);
            scope.menuItems = data;
          });
    };

    public template = this.$templateCache.get(MainNavigation.TEMPLATE_URL);
  }

  UMS.directive("mainNavigation", ['HttpClient', '$templateCache', (httpClient, $templateCache) => {
    return new MainNavigation(httpClient, $templateCache);
  }])
}