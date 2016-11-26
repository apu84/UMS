module ums {

  interface ExceptionMessageModel {
    cause?: string;
    message?: string;
  }
  function ServerExceptionInterceptor($q: ng.IQService, $log: ng.ILogService, baseURI: BaseUri, notify: Notify) {

    return {
      responseError: function (response: ng.IHttpPromiseCallbackArg<any>) {
        if (response.status == 500) {
          if (response.config.responseType == 'arraybuffer') {
            notify.error("Failed to generate pdf/xls.");
          } else {
            var responseJson: ExceptionMessageModel = response.data;
            $log.debug("Internal Server Exception Occured.");
            if (responseJson.message) {
              notify.error(responseJson.message);
            }
          }
        }
        return $q.reject(response);
      }
    };
  }

  ServerExceptionInterceptor.$inject = ['$q', '$log', 'BaseUri', 'notify'];

  UMS.factory('ServerExceptionInterceptor', ServerExceptionInterceptor);

  UMS.config(['$httpProvider', function ($httpProvider:ng.IHttpProvider) {
    $httpProvider.interceptors.push('ServerExceptionInterceptor');
  }]);
}
