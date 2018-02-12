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

    UMS.constant("appConstants", Constants.Default());
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
                templateUrl: 'views/employee/employee-profile.html',
                controller: 'EmployeeProfile',
                controllerAs: 'vm',
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
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
                            ]
                        });
                    }]
                }
            })
            .state('employeeInformation', {
                url: "/employeeInformation",
                templateUrl: 'views/employee/employee-information.html',
                controller: 'EmployeeInformation',
                controllerAs: 'vm',
                resolve: {
                    allUsers: ['employeeService', function (employeeService) {
                        return employeeService.getAll().then((data) => {
                            return data;
                        });
                    }]
                }
            })
            .state('employeeInformation.profile', {
                url: "/profile/:id",
                templateUrl: 'views/employee/employee-profile.html',
                controller: 'EmployeeProfile',
                controllerAs: 'vm',
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
                                'vendors/bootstrap-switch/js/bootstrap-switch.min.js',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
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
            .state('newEmployee', {
                url: "/newEmployee",
                templateUrl: 'views/new-employee.html',
                controller: 'NewEmployee',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
                            ]
                        });
                    }],
                    departments: ['departmentService', function (departmentService) {
                        return departmentService.getAll().then((data) => {
                            return data;
                        });
                    }],
                    designations: ['designationService', function (designationService) {
                        return designationService.getAll().then((data) => {
                            return data;
                        });
                    }],
                    employmentTypes: ['employmentTypeService', function (employmentTypeService) {
                        return employmentTypeService.getAll().then((data) => {
                            return data;
                        });
                    }]
                }
            })
            .state('schedule', {
                url: "/schedule",
                templateUrl: 'views/meeting/schedule.html',
                controller: 'Schedule',
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
            .state('agendaResolution', {
                url: "/agendaResolution",
                templateUrl: 'views/meeting/agenda-resolution.html',
                controller: 'AgendaResolution',
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
            .state('search', {
                url: "/search",
                templateUrl: 'views/meeting/search.html',
                controller: 'Search',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                              'vendors/ckeditor/ckeditor.js'
                            ]
                        });
                    }]
                }
            })
            .state('changePassword', {
                url: "/changePassword",
                controller: 'ChangePassword',
                templateUrl: 'views/common/change-password.html'
            })
            .state('logout', {
                url: "/logout",
                controller: 'Logout'
            })
    });
}