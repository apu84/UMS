module ums {
  function UnauthorizedInterceptor($q: ng.IQService, $log: ng.ILogService, baseURI: BaseUri, notify: Notify) {
    var expiredSession: boolean = false;
    return {
      responseError: function (response:ng.IHttpPromiseCallbackArg<any>) {
        if (response.status == 401) {
          var base = baseURI.getBaseURI();
          var requestURI = new URI(response.config.url);

          if (base.host() == requestURI.host() && requestURI.pathname().indexOf(base.pathname()) == 0) {
            $log.debug('Redirecting to login page. 401 Unauthorized found when requested: ' + response.config.url);

            //var redirectQuery = '?redirectTo=' + encodeURIComponent(window.location.href);
            //window.location.href = UrlUtil.getBaseAppUrl() + 'login/' + redirectQuery;
            if (!expiredSession) {
              notify.error("Your session has expired. Please Logout and then Login again", false);
              expiredSession = !expiredSession;
            }
          }
        } else if (response.status == 503) {
          $log.debug("Service temporarily unavailable");
          notify.error("Service is temporarily unavailable. Please try again later.");
        }

        return $q.reject(response);
      }
    };
  }

  UnauthorizedInterceptor.$inject = ['$q', '$log', 'BaseUri', 'notify'];

  UMS.factory('UnauthorizedInterceptor', UnauthorizedInterceptor);

  UMS.config(['$httpProvider', function ($httpProvider:ng.IHttpProvider) {
    $httpProvider.interceptors.push('UnauthorizedInterceptor');
  }]);
}