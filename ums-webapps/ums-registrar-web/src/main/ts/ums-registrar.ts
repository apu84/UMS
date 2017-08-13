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
        .state('profile', {
            url: "/profile",
            templateUrl: 'views/eim/employee-profile.html',
            controller: 'EmployeeProfile',
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
        .state('employeeInformation', {
            url: "/employeeInformation",
            templateUrl: 'views/eim/employee-information.html',
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
        .state('employeePublication', {
            url: "/employeePublication",
            templateUrl: 'views/employee-publication/employee-publication.html',
            controller: 'EmployeePublication',
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
        .state('meetingSchedule', {
          url: "/meetingSchedule",
          templateUrl: 'views/meeting-management/meeting-schedule.html',
          controller: 'MeetingSchedule',
            controllerAs: 'vm',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                            'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js'
                        ]
                    });
                }]
            }
        })
        .state('agendaAndResolution', {
            url: "/agendaAndResolution",
            templateUrl: 'views/meeting-management/agenda-resolution.html',
            controller: 'AgendaAndResolution',
            controllerAs: 'vm',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        files: [
                            'vendors/ckeditor/ckeditor.js',
                            'vendors/ckeditor/styles.js'
                        ]
                    });
                }]
            }
        })
        .state('meetingSearch', {
            url: "/meetingSearch",
            templateUrl: 'views/meeting-management/meeting-search.html',
            controller: 'MeetingSearch',
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
        .state('logout', {
          url: "/logout",
          controller: 'Logout'
        })
  });
}

