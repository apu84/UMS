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
            if (response.data) {
              var parser = new DOMParser();
              var doc = parser.parseFromString(response.data, 'text/html');
              var statusHtml = <HTMLElement> doc.body.querySelector('u');
              if(statusHtml && statusHtml.innerHTML) {
                notify.error(statusHtml.innerHTML);
              }
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
