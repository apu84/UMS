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
        'ngHandsontable',
        "angularUtils.directives.dirPagination"
    ]).run(function(ExpireToken){

    });
//https://localhost//ums-webservice-academic/academic/course/all/ipp/5/page/1

    UMS.config(['BaseUriProvider', (baseUriProvider: BaseUriProvider) => {
        baseUriProvider.setServicePath('/ums-webservice-library/');
    }]);

    UMS.constant("libConstants", Constants.LibConstant());
    UMS.constant("registrarConstants", Constants.RegistrarConstant());

    UMS.config(($stateProvider, $urlRouterProvider, $locationProvider) => {
        $urlRouterProvider.when('/cataloging', '/cataloging/search/new');
        $urlRouterProvider.otherwise("/userHome");
        $stateProvider
            .state('userHome', {
                url: "/userHome",
                templateUrl: 'views/user-home.html',
                controller: 'MainController',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/bootstrap-select/bootstrap-select.css'
                            ]
                        });
                    }]
                }
            })
            .state('cataloging.search', {
                url: "/search/:1",
                controller: 'RecordSearch',
                templateUrl: 'views/admin/cataloging/catalog-search.html',

                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/bootstrap-select/bootstrap-select.css'
                            ]
                        });
                    }]
                }
            })
            .state('cataloging.record', {
                url: "/:1/record/:2",
                controller: 'Cataloging',
                templateUrl: 'views/admin/cataloging/catalog.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'https://sliptree.github.io/bootstrap-tokenfield/dist/css/bootstrap-tokenfield.css',
                                'https://sliptree.github.io/bootstrap-tokenfield/dist/bootstrap-tokenfield.js',
                                // 'https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.css',
                                // 'https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.full.js',


                                'vendors/select2/select2-madmin.css',
                                'vendors/select2/select2.min.js',


                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'

                            ]
                        });
                    }]
                }
            })
            .state('cataloging', {
                url: "/cataloging",
                templateUrl: 'views/admin/cataloging/catalog-home.html',
                resolve: {
                    contributor: ['contributorService', function(contributorService){
                        return contributorService.fetchAllContributors().then((contributors) =>{
                            return contributors.entries;
                        });
                    }],
                    publisher: ['publisherService', function(publisherService){
                        return publisherService.fetchAllPublishers().then((publishers) =>{
                            return publishers.entries;
                        });
                    }],
                    supplier: ['supplierService', function(supplierService){
                        return supplierService.fetchAllSuppliers().then((suppliers) =>{
                            return suppliers.entries;
                        });
                    }]
                }
            })
            .state('cataloging.thesis', {
                url: "/thesis",
                templateUrl: 'views/admin/cataloging/catalog-thesis.html'
            })
            .state('cataloging.journal', {
                url: "/journal",
                templateUrl: 'views/admin/cataloging/catalog-journal.html'
            })
            .state('cataloging.map', {
                url: "/map",
                templateUrl: 'views/admin/cataloging/catalog-map.html'
            })
            .state('cataloging.cdDvd', {
                url: "/cdDvd",
                templateUrl: 'views/admin/cataloging/catalog-cdDvd.html'
            })
            .state('cataloging.magazine', {
                url: "/magazine",
                templateUrl: 'views/admin/cataloging/catalog-magazine.html'
            })
            .state('circulation', {
                url: "/circulation",
                templateUrl: 'views/admin/circulation/circulation-home.html',
                controller: 'CirculationHome',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-datepicker/css/datepicker.css',
                                'vendors/bootstrap-datepicker/js/bootstrap-datepicker.js'
                            ]
                        });
                    }]
                }
            })
            .state('patrons', {
                url: "/patrons",
                templateUrl: 'views/admin/patron/patron-home.html',
                controller: 'PatronHome',
                controllerAs: 'vm',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.css',
                                'vendors/bootstrap-imageupload/bootstrap-imageupload.js'
                            ]
                        });
                    }]
                }
            })
            .state('circulation.checkOut', {
                url: "/checkOut/:userId",
                templateUrl: 'views/admin/circulation/circulation-checkout.html',
                controller: 'CirculationCheckOut',
                controllerAs: 'vm'
            })
            .state('circulation.checkIn', {
                url: "/checkIn/:itemId/:mfn",
                templateUrl: 'views/admin/circulation/circulation-checkin.html',
                controller: 'CheckIn',
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
            .state('circulation.searchPatron', {
                url: "/searchPatron/:patronId",
                templateUrl: 'views/admin/patron/patron-search.html',
                controller: 'PatronSearch',
                controllerAs: 'vm'
            })
            .state('circulation.checkOut.patronCheckOut', {
                url: "/patronCheckOut/:patronId",
                templateUrl: 'views/admin/patron/patron-checkout.html',
                controller: 'PatronCheckOut',
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
            .state('circulation.checkOut.patronDetail', {
                url: "/patronDetail/:patronId",
                templateUrl: 'views/admin/patron/patron-detail.html',
                controller: 'PatronDetails',
                controllerAs: 'vm'
            })
            .state('circulation.checkOut.patronFines', {
                url: "/patronFine/:patronId",
                templateUrl: 'views/admin/patron/patron-fine.html',
                controller: 'Fine',
                controllerAs: 'vm'
            })
            .state('circulation.checkOut.circulationHistory', {
                url: "/circulationHistory/:patronId",
                templateUrl: 'views/admin/circulation/circulation-history.html',
                controller: 'CirculationHistory',
                controllerAs: 'vm'
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
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/personal-information.html',
                controller: 'PersonalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.academic', {
                url: "/academic",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/academic-information.html',
                controller: 'AcademicInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.publication', {
                url: "/publication",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/publication-information.html',
                controller: 'PublicationInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.training', {
                url: "/training",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/training-information.html',
                controller: 'TrainingInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.award', {
                url: "/award",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/award-information.html',
                controller: 'AwardInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.experience', {
                url: "/experience",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/experience-information.html',
                controller: 'ExperienceInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.additional', {
                url: "/additional",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/additional-information.html',
                controller: 'AdditionalInformation',
                controllerAs: 'vm'
            })
            .state('employeeProfile.service', {
                url: "/service",
                params : {
                    'id': null,
                    'edit': null
                },
                templateUrl: 'views/employee/service-information.html',
                controller: 'ServiceInformation',
                controllerAs: 'vm'
            })
            .state('searchLibrary', {
                url: "/searchLibrary",
                controller: 'SearchLibrary',
                templateUrl: 'views/library-search.html',
                resolve: {
                    loadMyCtrl: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            files: [
                                'vendors/bootstrap-select/bootstrap-select.min.js',
                                'vendors/bootstrap-select/bootstrap-select.css'
                            ]
                        });
                    }]
                }
            })

            .state('borrow', {
                url: "/borrow",
                controller: 'BorrowHistory',
                templateUrl: 'views/borrow-history.html',
                controllerAs: 'vm'
            })
    });
}
