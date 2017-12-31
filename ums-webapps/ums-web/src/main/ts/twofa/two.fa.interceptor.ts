module ums {
  function TwoFAInterceptor($q: ng.IQService, $log: ng.ILogService, $injector: any) {
    return {
      response: function (response: ng.IHttpPromiseCallbackArg<any>) {
        if (response.status == 200) {
          if (response.headers('state')) {
            $log.info("Got 2FA request with state: ", response.headers('state'));
            let state: string = response.headers('state');
            let twoFaService: TwoFAService = $injector.get('TwoFAService');
            return twoFaService.showTwoFAForm(state).then((success) => {
                  response.data = success;
                  return $q.resolve(response);
                },
                (error) => {
                  $log.info("Got 2FA error: ", error);
                  response.data = error;
                  return $q.reject(response);
                });
          }
          else {
            return $q.resolve(response);
          }
        }

      }
    };
  }

  TwoFAInterceptor.$inject = ['$q', '$log', '$injector'];

  UMS.factory('TwoFAInterceptor', TwoFAInterceptor);

  UMS.config(['$httpProvider', function ($httpProvider: ng.IHttpProvider) {
    $httpProvider.interceptors.push('TwoFAInterceptor');
  }]);
}
