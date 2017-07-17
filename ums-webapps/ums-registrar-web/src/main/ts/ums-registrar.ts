module ums {
  export var UMS = angular.module('UMS', [
    'ngRoute',
    'ui.bootstrap',
    'ui.router',
    'oc.lazyLoad',
    'LocalStorageModule',
    'ngCookies',
    'ngSanitize',
    'scrollable-table',
    'amChartsDirective',
    'ui.sortable',
      "angularUtils.directives.dirPagination"
  ]);

  UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
    baseUriProvider.setServicePath('/ums-webservice-registrar/');
  }]);

  UMS.constant("registrarConstants", Constants.RegistrarConstant());

  UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
    $urlRouterProvider.otherwise("/userHome");
    $stateProvider
        .state('userHome', {
          url: "/userHome",
          templateUrl: 'views/user-home.html',
          controller: 'MainController',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: ['vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js']
              });
            }]
          }
        })
        .state('empInfo', {
          url: "/empInfo",
          templateUrl: 'views/registrar-office/employee-information-management/employee-information.html',
          controller: 'EmployeeInformation',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/select2/select2-madmin.css',
                  'vendors/bootstrap-select/bootstrap-select.min.css',
                  'vendors/multi-select/css/multi-select-madmin.css',
                  'vendors/select2/select2.min.js',
                  'vendors/bootstrap-select/bootstrap-select.min.js',
                  'vendors/multi-select/js/jquery.multi-select.js',
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('empServiceInfo', {
          url: "/empServiceInfo",
          templateUrl: 'views/registrar-office/employee-information-management/service-information.html',
          controller: 'EmployeeServiceInformation',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/select2/select2-madmin.css',
                  'vendors/bootstrap-select/bootstrap-select.min.css',
                  'vendors/multi-select/css/multi-select-madmin.css',
                  'vendors/select2/select2.min.js',
                  'vendors/bootstrap-select/bootstrap-select.min.js',
                  'vendors/multi-select/js/jquery.multi-select.js',
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('viewEmpInfo', {
            url: "/viewEmpInfo",
            templateUrl: 'views/registrar-office/employee-information-management/registrar-emp-view.html',
            controller: 'RegistrarEmployeeView',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/select2/select2-madmin.css',
                            'vendors/bootstrap-select/bootstrap-select.min.css',
                            'vendors/multi-select/css/multi-select-madmin.css',
                            'vendors/select2/select2.min.js',
                            'vendors/bootstrap-select/bootstrap-select.min.js',
                            'vendors/multi-select/js/jquery.multi-select.js',
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                        ]
                    });
                }]
            }
        })
        .state('meetingMS', {
          url: "/meetingMS",
          templateUrl: 'views/registrar-office/meeting-management/meeting.html',
          controller: 'Meeting',
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
              return $ocLazyLoad.load({
                files: [
                  'vendors/ckeditor/ckeditor.js',
                  'vendors/select2/select2-madmin.css',
                  'vendors/bootstrap-select/bootstrap-select.min.css',
                  'vendors/multi-select/css/multi-select-madmin.css',
                  'vendors/select2/select2.min.js',
                  'vendors/bootstrap-select/bootstrap-select.min.js',
                  'vendors/multi-select/js/jquery.multi-select.js',
                  'vendors/bootstrap-switch/css/bootstrap-switch.css',
                  'vendors/bootstrap-datepicker/css/datepicker.css',
                  'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                  'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                ]
              });
            }]
          }
        })
        .state('approvePublication', {
            url: "/approvePublication",
            templateUrl: 'views/registrar-office/approve-publication.html',
            controller: 'ApprovePublication',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/ckeditor/ckeditor.js',
                            'vendors/select2/select2-madmin.css',
                            'vendors/bootstrap-select/bootstrap-select.min.css',
                            'vendors/multi-select/css/multi-select-madmin.css',
                            'vendors/select2/select2.min.js',
                            'vendors/bootstrap-select/bootstrap-select.min.js',
                            'vendors/multi-select/js/jquery.multi-select.js',
                            'vendors/bootstrap-switch/css/bootstrap-switch.css',
                            'vendors/bootstrap-datepicker/css/datepicker.css',
                            'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'vendors/bootstrap-switch/js/bootstrap-switch.min.js'
                        ]
                    });
                }]
            }
        })
        .state('logout', {
          url: "/logout",
          controller: 'Logout'
        })
        .state('admissionFee', {
          url: "/admissionFee",
          templateUrl: 'views/admission/admission-fee.html',
          controller: 'AdmissionFee'
        })
  });
}

