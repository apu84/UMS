module ums {
  function TwoFAInterceptor($q: ng.IQService, $log: ng.ILogService, $injector: any) {
    return {
      response: function (response: ng.IHttpPromiseCallbackArg<any>) {
        return $q.resolve(response);
      },
      responseError: function (response: ng.IHttpPromiseCallbackArg<any>) {
        if (response.status == 428) {
          if (response.headers('state')) {
            $log.info("Got 2FA request with state: ", response.headers('state'));
            let state: string = response.headers('state');
            let lifeTime: number = Number(response.headers('lifeTime'));
            let remainingTime: number = Number(response.headers('remainingTime'));
            let emailAddress: string = response.headers('emailAddress');
            let twoFaService: TwoFAService = $injector.get('TwoFAService');
            return twoFaService.showTwoFAForm(state, lifeTime, remainingTime, emailAddress).then((success) => {
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
        } else {
          return $q.reject(response);
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
