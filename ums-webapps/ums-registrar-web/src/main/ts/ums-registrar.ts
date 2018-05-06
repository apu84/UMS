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
    ]).run(function(ExpireToken){

    });

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
            .state('employeeProfile', {
                url: "/employeeProfile",
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
            .state('employeeProfile.personal', {
                url: "/personal",
                params : {
                    'id1': null
                },
                templateUrl: 'views/employee/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id2': null
                },
                templateUrl: 'views/employee/academic-information.html',
                controller: 'EducationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id3': null
                },
                templateUrl: 'views/employee/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.training', {
                url: "/training",
                params : {
                    'id4': null
                },
                templateUrl: 'views/employee/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.award', {
                url: "/award",
                params : {
                    'id5': null
                },
                templateUrl: 'views/employee/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id6': null
                },
                templateUrl: 'views/employee/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id7': null
                },
                templateUrl: 'views/employee/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.service', {
                url: "/service",
                params : {
                    'id8': null
                },
                templateUrl: 'views/employee/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
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
            .state('employeeInformation.employeeProfile', {
                url: "/employeeProfile",
                params: {
                  'id' : null
                },
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
            .state('employeeInformation.employeeProfile.personal', {
                url: "/personal",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/academic-information.html',
                controller: 'EducationInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.training', {
                url: "/training",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.award', {
                url: "/award",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeInformation.employeeProfile.service', {
                url: "/service",
                params : {
                    'id' : null
                },
                templateUrl: 'views/employee/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
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
                    }],
                    roles: ['roleService', function (roleService) {
                        return roleService.getAll().then((data: any) => {
                            return data.entries;
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
