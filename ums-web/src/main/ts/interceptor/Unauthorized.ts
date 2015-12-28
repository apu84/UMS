module ums {
  export class Unauthorized {

    constructor(private $q:ng.IQService,
                private baseUri:BaseUri) {

    }

    public responseError = (response:ng.IHttpPromiseCallbackArg<any>) => {
      if (response.status == 401) {
        var base = this.baseUri.getBaseURI();
        var requestURI = new URI(response.config.url);

        if (base.host() == requestURI.host() && requestURI.pathname().indexOf(base.pathname()) == 0) {
          var redirectQuery = '?redirectTo=' + encodeURIComponent(window.location.href);
          window.location.href = UrlUtil.getBaseAppUrl() + 'login/' + redirectQuery;
        }
      }

      return this.$q.reject(response);
    }
  }

  Unauthorized.$inject = ['$q', 'BaseUri'];
  UMS.factory('UnauthorizedInterceptor', Unauthorized);

  UMS.config(['$httpProvider', function ($httpProvider:ng.IHttpProvider) {
    $httpProvider.interceptors.push('UnauthorizedInterceptor');
  }]);
}