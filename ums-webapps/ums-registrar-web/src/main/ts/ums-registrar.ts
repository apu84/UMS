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
    UMS.constant("libConstants", Constants.LibConstant());

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
                templateUrl: 'views/ems/profile-management/non-admin/employee-profile.html',
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
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.training', {
                url: "/training",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.award', {
                url: "/award",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.service', {
                url: "/service",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/non-admin/service/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch', {
                url: "/employeeSearch",
                templateUrl: 'views/ems/search/employee-search.html',
                controller: 'EmployeeSearch',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile', {
                url: "/employeeProfile",
                params: {
                  'id' : null
                },
                templateUrl: 'views/ems/profile-management/admin/employee-profile.html',
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
            .state('employeeSearch.employeeProfile.personal', {
                url: "/personal",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/personal/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/academic/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/publication/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.training', {
                url: "/training",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/training/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.award', {
                url: "/award",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/award/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/experience/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/additional/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeSearch.employeeProfile.service', {
                url: "/service",
                params : {
                    'id' : null,
                    'edit': null
                },
                templateUrl: 'views/ems/profile-management/admin/service/service-information.html',
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
                templateUrl: 'views/ems/create-new/new-employee.html',
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
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js',
                                'vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                                'vendors/bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js',
                                'vendors/bootstrap-timepicker/css/bootstrap-timepicker.css',
                                'vendors/bootstrap-timepicker/js/bootstrap-timepicker.js'
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
