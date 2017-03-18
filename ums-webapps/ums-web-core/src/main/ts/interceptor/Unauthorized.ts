module ums {
  function UnauthorizedInterceptor($q: ng.IQService, $log: ng.ILogService, baseURI: BaseUri, notify: Notify) {
    const EXPIRED_TOKEN: string = 'expired_token';
    let expiredSession: boolean = false;
    let backendUnavailable: boolean = false;
    return {
      responseError: function (response: ng.IHttpPromiseCallbackArg<any>) {
        switch (response.status) {
          case 401:
            var base = baseURI.getBaseURI();
            var requestURI = new URI(response.config.url);

            if (base.host() == requestURI.host() && requestURI.pathname().indexOf(base.pathname()) == 0) {
              if ($.trim(response.data) === EXPIRED_TOKEN) {
                if (!expiredSession) {
                  notify.error("Your session has expired. Please Logout and then Login again", false);
                  expiredSession = !expiredSession;
                }
              } else {
                // var redirectQuery = '?redirectTo=' + encodeURIComponent(window.location.href);
                window.location.href = UrlUtil.getBaseAppUrl() + 'login/session-expired.html';
              }
            }
            break;
          case 503:
            if (!backendUnavailable) {
              $log.debug("Service temporarily unavailable");
              notify.error("Service is temporarily unavailable. Please try again later.");
              backendUnavailable = !backendUnavailable;
            }
            break;
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
