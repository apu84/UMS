module ums {
  interface ValidationExceptionModel {
    cause: string;
    message: string;
  }

  interface FieldValidationModel {
    rootCause?: string;
    fieldValidation: {
      [key: string]: string;
    }
  }

  function ValidationExceptions($q: ng.IQService, $log: ng.ILogService, baseURI: BaseUri, notify: Notify) {
    return {
      responseError: function (response: ng.IHttpPromiseCallbackArg<any>) {
        if (response.status == 400) {
          var responseJson: ValidationExceptionModel = response.data;

          if (responseJson.cause && responseJson.cause == 'ValidationException') {

            var fieldValidationModel: FieldValidationModel = null;

            if (responseJson.message) {
              fieldValidationModel = JSON.parse(responseJson.message);
            }

            var rootCause = fieldValidationModel.rootCause ? fieldValidationModel.rootCause : "";
            if (rootCause != "") {
              notify.error(rootCause);
            }

            if (fieldValidationModel.fieldValidation) {
              for (var key in fieldValidationModel.fieldValidation) {
                if (fieldValidationModel.fieldValidation.hasOwnProperty(key)) {
                  notify.warn(fieldValidationModel.fieldValidation[key]);
                }
              }
            }
          }
        }

        return $q.reject(response);
      }
    };
  }

  ValidationExceptions.$inject = ['$q', '$log', 'BaseUri', 'notify'];

  UMS.factory('ValidationExceptions', ValidationExceptions);

  UMS.config(['$httpProvider', function ($httpProvider: ng.IHttpProvider) {
    $httpProvider.interceptors.push('ValidationExceptions');
    console.debug('%o',$httpProvider);
  }]);
}